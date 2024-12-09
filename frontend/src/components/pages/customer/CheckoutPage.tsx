import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../store/store";
import { addOrder } from "../../store/orderSlice.ts";

const CheckoutPage: React.FC = () => {
  const dispatch = useDispatch();
  const cartItems = useSelector((state: RootState) => state.cart.items);
  const totalAmount = cartItems.reduce(
    (sum, item) => sum + item.price * item.quantity,
    0
  );

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
      <h2>Checkout</h2>
      <div className="checkout-summary">
        <p>Total Amount: {totalAmount} USD</p>
        <button onClick={handlePlaceOrder}>Place Order</button>
      </div>
    </div>
  );
};

export default CheckoutPage;
