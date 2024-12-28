package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.exception.GeneralException;
import com.cuong02n.aimsbackend.model.entity.*;
import com.cuong02n.aimsbackend.repository.InvoiceRepository;
import com.cuong02n.aimsbackend.repository.OrderRepository;
import jakarta.servlet.ServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    private static final int FREE_SHIPPING_THRESHOLD = 100000;
    private static final int MAX_SHIPPING_DISCOUNT = 25000;
    private static final int RUSH_SHIPPING_FEE = 10000;
    private static final double INITIAL_WEIGHT_ROOT = 3.0;
    private static final double INITIAL_WEIGHT_EXTRA = 0.5;
    private static final int INITIAL_FEE_URBAN = 22000;
    private static final int INITIAL_FEE_RURAL = 30000;
    private static final int ADDITIONAL_FEE_PER_WEIGHT = 2500;
    private static final Set<String> URBAN_PROVINCES = Set.of("Hà Nội", "Hồ Chí Minh");
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final InvoiceRepository invoiceRepository;
    private final ServletRequest httpServletRequest;

    public Invoice placeOrder(User user, HashSet<Long> productIds, String address, String phone, String province, String shippingInstruction) {

        checkPlaceOrderRequestInCart(cartService.getUserCart(user), productIds);

        checkOrderNotPaidExist(user);

        return createNewOrder(user, productIds, address, phone, province, shippingInstruction);
    }

    public Invoice placeRushOrder(User user, HashSet<Long> productIds, int minute, String address, String phone, String province, String shippingInstruction) {
        checkPlaceOrderRequestInCart(cartService.getUserCart(user), productIds);
        checkOrderNotPaidExist(user);
        return createNewOrder(user, productIds, address, phone, province, shippingInstruction, minute);
    }

    @Transactional
    public void deleteOrder(long orderId) {
        // check user is owner of this order
        if (orderRepository.findAllByUserAndOrderId((User) httpServletRequest.getAttribute("user"), orderId).isEmpty()) {
            throw new GeneralException("You not have permission of modifying this order or it may not exists");
        }

        // check invoice is paid
        if (isInvoicePaid(orderId)) {
            throw new GeneralException("The invoice is paid, you cannot delete this order");
        }
        // delete invoice
        invoiceRepository.deleteById(orderId);
        orderRepository.deleteById(orderId);
    }

    private boolean isInvoicePaid(long orderId) {
        Invoice i = invoiceRepository.findById(orderId).orElseThrow();
        return i.isPaid();
    }

    private Invoice createNewOrder(User user, HashSet<Long> productIds, String address, String phone, String province, String shippingInstruction) {
        return createNewOrder(user, productIds, address, phone, province, shippingInstruction, 0);
    }

    private Invoice createNewOrder(User user, HashSet<Long> productIds, String address, String phone, String province, String shippingInstruction, int timeInMinute) {
        List<ProductCart> cart = cartService.getUserCart(user);
        Order order = new Order();

        order.setUser(user);
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (ProductCart productCart : cart) {
            if (productIds.contains(productCart.getKey().getProductId())) {
                orderProducts.add(
                        OrderProduct
                                .builder()
                                .product(productCart.getProduct())
                                .order(order)
                                .quantity(productCart.getQuantity())
                                .key(new OrderProduct.OrderProductKey(
                                        productCart.getKey().getProductId(),
                                        order.getOrderId())
                                )
                                .build()
                );
            }
        }
        order.setOrderProducts(orderProducts);
        order.setAddress(address);
        order.setPhone(phone);
        order.setProvince(province);
        order.setShippingInstruction(shippingInstruction);
        order.setRush(timeInMinute != 0);
        order.setTimeInMinute(timeInMinute);

        orderRepository.save(order);
        return invoiceRepository.save(createInvoice(order));
    }

    private Invoice createInvoice(Order order) {

        int shippingFee = calculateShippingFee(order);
        int totalAmountWithoutVAT = order.getOrderProducts().stream().mapToInt(o -> o.getProduct().getPrice() * o.getQuantity()).sum();
        int totalAmountIncludeVAT = (int) (1.1 * totalAmountWithoutVAT);
        int finalTotalAmount = shippingFee + totalAmountIncludeVAT;

        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setPaid(false);
        invoice.setShippingFee(shippingFee);
        invoice.setTotalAmountWithoutVAT(totalAmountWithoutVAT);
        invoice.setTotalAmountIncludeVAT(totalAmountIncludeVAT);
        invoice.setTotalAmountIncludeShippingFee(finalTotalAmount);
        return invoice;
    }

    private int calculateShippingFee(Order order) {
        double maxWeight = 0;
        int totalOrderValue = 0;
        List<OrderProduct> orderProducts = order.getOrderProducts();
        String province = order.getProvince();
        boolean isRush = order.isRush();

        for (OrderProduct op : orderProducts) {
            Product product = op.getProduct();
            int quantity = op.getQuantity();

            maxWeight = Math.max(maxWeight, product.getWeight());
            totalOrderValue += product.getPrice() * quantity;
        }

        int baseShippingFee = calculateBaseShippingFee(maxWeight, province);

        if (isRush) {
            return baseShippingFee + (RUSH_SHIPPING_FEE * orderProducts.size());
        }

        if (totalOrderValue >= FREE_SHIPPING_THRESHOLD) {
            int discount = Math.min(baseShippingFee, MAX_SHIPPING_DISCOUNT);
            return baseShippingFee - discount;
        }

        return baseShippingFee;
    }

    private int calculateBaseShippingFee(double weight, String province) {
        boolean isUrbanArea = URBAN_PROVINCES.contains(province);

        if (isUrbanArea) {
            if (weight <= INITIAL_WEIGHT_ROOT) {
                return INITIAL_FEE_URBAN;
            }
            double extraWeight = weight - INITIAL_WEIGHT_ROOT;
            int additionalFee = (int) Math.ceil(extraWeight / INITIAL_WEIGHT_EXTRA) * ADDITIONAL_FEE_PER_WEIGHT;
            return INITIAL_FEE_URBAN + additionalFee;
        } else {
            if (weight <= INITIAL_WEIGHT_ROOT) {
                return INITIAL_FEE_RURAL;
            }
            double extraWeight = weight - INITIAL_WEIGHT_ROOT;
            int additionalFee = (int) Math.ceil(extraWeight / INITIAL_WEIGHT_EXTRA) * ADDITIONAL_FEE_PER_WEIGHT;
            return INITIAL_FEE_RURAL + additionalFee;
        }
    }

    public List<Order> getOrder(User user) {
        return orderRepository.findAllByUser(user);
    }

    private void checkOrderNotPaidExist(User user) {
//        Order notPaidOrder = orderRepository.findByUser(user);
//        if (notPaidOrder != null) {
//            throw new GeneralException("There is a order you have not paid yet: " + notPaidOrder.getOrderId());
//        }
    }

    private void checkPlaceOrderRequestInCart(List<ProductCart> productCarts, HashSet<Long> productIds) {
        List<Long> productIdInCart = productCarts.stream().map(c -> c.getKey().getProductId()).toList();
        if (!new HashSet<>(productIdInCart).containsAll(productIds)) {
            throw new GeneralException("Place Order request must be exist in cart: [" + productIdInCart + "] not contains [" + productIds + "]");
        }
    }
}
