import React from "react";
import { useSelector, useDispatch } from "react-redux";
<<<<<<< HEAD
import { RootState } from "../../store/store";
import { addOrder, updateOrderStatus } from "../../store/orderSlice";
=======
import { RootState } from "../store/store";
import { addOrder, removeOrder, updateOrderStatus } from "../store/orderSlice";
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69

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
<<<<<<< HEAD
    // dispatch(addOrder(newOrder));
=======
    dispatch(addOrder(newOrder));
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
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
