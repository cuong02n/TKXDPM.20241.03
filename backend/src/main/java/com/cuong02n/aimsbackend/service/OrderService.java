package com.cuong02n.aimsbackend.service;

import com.cuong02n.aimsbackend.exception.GeneralException;
import com.cuong02n.aimsbackend.model.dto.request.PlaceOrderV2Request;
import com.cuong02n.aimsbackend.model.entity.*;
import com.cuong02n.aimsbackend.repository.InvoiceRepository;
import com.cuong02n.aimsbackend.repository.OrderRepository;
import com.cuong02n.aimsbackend.repository.ProductCartRepository;
import jakarta.servlet.ServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final InvoiceRepository invoiceRepository;
    private final ServletRequest httpServletRequest;
    private final ProductCartRepository productCartRepository;

    public Invoice placeOrder(User user, HashSet<Long> productIds, String address, String phone, String province, String shippingInstruction) {

        checkPlaceOrderRequestInCart(cartService.getUserCart(user), productIds);

        checkOrderNotPaidExist(user);

        return createNewOrder(user, productIds, address, phone, province, shippingInstruction);
    }

    @Transactional
    public Invoice placeOrderV2(User user, PlaceOrderV2Request request) {
        checkPlaceOrderRequestInCart(cartService.getUserCart(user), request.getListProductNoRush());
        checkPlaceOrderRequestInCart(cartService.getUserCart(user), request.getListProductRush());
        // check diffirent list btw no rush and rush;

        return createNewOrder(
                user,
                request.getListProductNoRush(),
                request.getListProductRush(),
                request.getAddress(),
                request.getPhone(),
                request.getProvince(),
                request.getShippingInstruction(),
                request.getTimeInMinute()
        );
    }


    public Invoice placeRushOrder(User user, HashSet<Long> productIds, int minute, String address, String phone, String province, String shippingInstruction) {
        checkPlaceOrderRequestInCart(cartService.getUserCart(user), productIds);
        checkOrderNotPaidExist(user);
        return createNewOrder(user, null, productIds, address, phone, province, shippingInstruction, minute);
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
        return createNewOrder(user, productIds, null, address, phone, province, shippingInstruction, 0);
    }

    protected Invoice createNewOrder(User user, HashSet<Long> productIdsNotRush, HashSet<Long> productIdsRush, String address, String phone, String province, String shippingInstruction, int timeInMinute) {
        List<ProductCart> cart = cartService.getUserCart(user);
        OrderService.log.info("{}", cart.stream().map(c -> c.getKey().getProductId()).collect(Collectors.toSet()));
        Order order = new Order();

        order.setUser(user);
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (ProductCart productCart : cart) {
            if (productIdsNotRush.contains(productCart.getKey().getProductId())) {
                orderProducts.add(
                        OrderProduct
                                .builder()
                                .isRush(true)
                                .product(productCart.getProduct())
                                .order(order)
                                .quantity(productCart.getQuantity())
                                .key(new OrderProduct.OrderProductKey(
                                        productCart.getKey().getProductId(),
                                        order.getOrderId())
                                )
                                .build()
                );
            } else if (productIdsRush.contains(productCart.getKey().getProductId())) {
                if(!productCart.getProduct().isSupportedRush()){
                    throw new GeneralException("This product currently not support rush: "+productCart.getProduct().getId());
                }
                orderProducts.add(
                        OrderProduct
                                .builder()
                                .isRush(false)
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
//        order.setRush(timeInMinute != 0);
        order.setTimeInMinute(timeInMinute);

        orderRepository.save(order);

        ArrayList<Long> allProductIdsInOrder = new ArrayList<>();
        allProductIdsInOrder.addAll(productIdsRush);
        allProductIdsInOrder.addAll(productIdsNotRush);
        productCartRepository.deleteAllByKey_UserEmailAndKey_ProductIdIn(user.getEmail(), allProductIdsInOrder);
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

    private int calculateShippingFee(Order order) {
//        double maxWeight = 0;
//        int totalOrderValue = 0;
//        List<OrderProduct> orderProducts = order.getOrderProducts();
//        String province = order.getProvince();
//        boolean isRush = order.isRush();
//
//        for (OrderProduct op : orderProducts) {
//            Product product = op.getProduct();
//            int quantity = op.getQuantity();
//
//            maxWeight = Math.max(maxWeight, product.getWeight());
//            totalOrderValue += product.getPrice() * quantity;
//        }
//
//        int baseShippingFee = calculateBaseShippingFee(maxWeight, province);
//
//        if (isRush) {
//            return baseShippingFee + (RUSH_SHIPPING_FEE * orderProducts.size());
//        }
//
//        if (totalOrderValue >= FREE_SHIPPING_THRESHOLD) {
//            int discount = Math.min(baseShippingFee, MAX_SHIPPING_DISCOUNT);
//            return baseShippingFee - discount;
//        }
//
//        return baseShippingFee;
        return 0;
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
