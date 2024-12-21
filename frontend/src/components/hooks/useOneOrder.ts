import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../store/store";
import {
  addOrderProduct,
  deleteOrderProduct,
  setDeliveryInfo,
  updateOrderProduct,
} from "../store/oneOrderSlice.ts";
import { CartItem } from "../types/cart";
import { DeliveryInformation } from "../types/deliveryInfo.ts";
import {
  getAllOrders,
  placeOrder,
  placeRushOrder,
} from "../store/thunk/orderThunk.ts";

export const useOneOrder = () => {
  const dispatch = useDispatch<AppDispatch>();
  const orderProducts = useSelector((state: RootState) => state.oneOrder.items);
  const deliveryInfo = useSelector((state: RootState) => state.deliveryInfo);

  const getMyOrders = () => {
    dispatch(getAllOrders());
  };

  const placeOrderNormal = () => {
    const data = {
      productIds: orderProducts.map((item) => Number(item.id)),
      address: deliveryInfo.address,
      phone: deliveryInfo.phone,
      province: deliveryInfo.province,
      shippingInstruction: deliveryInfo.instructions,
    };
    dispatch(placeOrder(data));
  };

  const placeOrderRush = () => {
    const data = {
      productIds: orderProducts.map((item) => Number(item.id)),
      address: deliveryInfo.address,
      phone: deliveryInfo.phone,
      province: deliveryInfo.province,
      shippingInstruction: deliveryInfo.instructions,
      timeInMinutes: 120,
    };
    dispatch(placeRushOrder(data));
  };

  const addProductToOrder = (item: CartItem) => {
    dispatch(addOrderProduct({ item }));
  };
  const deleteProductFromOrder = (productId: string) => {
    dispatch(deleteOrderProduct({ productId }));
  };
  const updateProductInOrder = (productId: string, quantity: number) => {
    dispatch(updateOrderProduct({ productId, quantity }));
  };
  const setDelivery = (info: DeliveryInformation) => {
    dispatch(setDeliveryInfo({ info }));
  };

  return {
    orderProducts,
    addProductToOrder,
    deleteProductFromOrder,
    updateProductInOrder,
    setDelivery,
    placeOrderNormal,
    placeOrderRush,
    getMyOrders,
  };
};
