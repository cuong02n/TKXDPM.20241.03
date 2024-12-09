import React from "react";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../../store/store";
import { addOrder, updateOrderStatus } from "../../store/orderSlice";

const OrderPage = () => {
  const dispatch = useDispatch();
  const orders = useSelector((state: RootState) => state.order.orders);

  const handleAddOrder = () => {
    const newOrder = {
      orderId: "123",
      userId: "user01",
      items: [{ id: "1", name: "Book", price: 20, quantity: 1 }],
      totalAmount: 20,
      status: "pending",
    };
    // dispatch(addOrder(newOrder));
  };

  const handleUpdateStatus = (orderId: string) => {
    dispatch(updateOrderStatus({ orderId, status: "completed" }));
  };

  return (
    <div>
      <h2>Orders</h2>
      <button onClick={handleAddOrder}>Add Order</button>
      <ul>
        {orders.map((order) => (
          <li key={order.orderId}>
            <div>Order ID: {order.orderId}</div>
            <div>Status: {order.status}</div>
            <button onClick={() => handleUpdateStatus(order.orderId)}>
              Mark as Completed
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default OrderPage;
