import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store/store";
import {
  addOrder,
  addOrderProduct,
  deleteOrderProduct,
  updateOrderStatus,
} from "../store/orderSlice";
import { Order } from "../types/order";
import { CartItem } from "../types/cart";

const useOrder = () => {
  const dispatch = useDispatch();
  const orders = useSelector((state: RootState) => state.order.orders);
  const addNewOrder = (orderData: Omit<Order, "orderId" | "status">) => {
    dispatch(addOrder(orderData));
  };

  const changeOrderStatus = (orderId: string, status: Order["status"]) => {
    dispatch(updateOrderStatus({ orderId, status }));
  };

  const addProductToOrder = (orderId: string, item: CartItem) => {
    dispatch(addOrderProduct({ orderId, item }));
  };
  const deleteProductFromOrder = (orderId: string, productId: string) => {
    dispatch(deleteOrderProduct({ orderId, productId }));
  };

  return {
    orders,
    addNewOrder,
    changeOrderStatus,
    addProductToOrder,
    deleteProductFromOrder,
  };
};

export default useOrder;
