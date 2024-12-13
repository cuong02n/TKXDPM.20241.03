import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../store/store";
import { addOrder } from "../../store/orderSlice.ts";
import { Invoice } from "../../types/invoice.ts";
import InvoiceProduct from "../../invoice/InvoiceProduct.tsx";
import InvoiceSummary from "../../invoice/InvoiceSummary.tsx";
import InvoiceDelivery from "../../invoice/InvoiceDelivery.tsx";

const CheckoutPage: React.FC = () => {
  const dispatch = useDispatch();
  const cartItems = useSelector((state: RootState) => state.cart.items);
  const order = useSelector((state: RootState) => state.oneOrder);
  const totalAmount = cartItems.reduce(
    (sum, item) => sum + item.price * item.quantity,
    0
  );

  const invoice: Invoice = {
    deliveryInfo: {
      address: "123 ABC",
      province: "HCM",
      name: "Vietnam",
      phone: "123",
      instructions: "Please call me before delivery",
    },
    id: "1",
    order: order,
    shippingFee: 1000,
    VAT: 10000,
    total: 100000,
  };
  const total = invoice.order.totalAmount + invoice.VAT + invoice.shippingFee;
  const subtotal = invoice.order.totalAmount;

  const handlePlaceOrder = () => {
    const userId = "user01"; // Bạn có thể lấy `userId` từ state hoặc context nếu cần
    dispatch(
      addOrder({
        userId,
        items: cartItems,
        totalAmount,
      })
    );
  };

  return (
    <div className="container mx-auto py-10 min-h-screen checkout-page">
      <h2 className="text-2xl font-bold mb-5">Invoice</h2>
      <div></div>
      <div>
        {invoice.order.items.map((item, index) => (
          <InvoiceProduct key={index} product={item} />
        ))}
        <hr className="border-t-2 border-dashed border-zinc-500 my-4" />
      </div>
      <div>
        <InvoiceSummary
          subtotal={subtotal}
          VAT={invoice.VAT}
          shippingFee={invoice.shippingFee}
          total={total}
        />
      </div>
      <hr className="border-t-2 border-dashed border-zinc-500 my-4" />
      <div>
        <InvoiceDelivery delivery={invoice.deliveryInfo} />
      </div>
      <div className="flex justify-end gap-8">
        <button
          onClick={handlePlaceOrder}
          className="w-48 mt-4 bg-blue-600 text-white py-3 rounded-lg hover:bg-gradient-to-r from-blue-900 to-blue-800 transition-colors duration-300 font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 active:scale-[0.99] disabled:opacity-50 disabled:cursor-not-allowed"
        >
          <span className="text-xl drop-shadow-md">Pay Order</span>
        </button>
      </div>
    </div>
  );
};

export default CheckoutPage;
