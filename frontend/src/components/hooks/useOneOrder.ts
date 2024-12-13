import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store/store";
import {
  addOrderProduct,
  deleteOrderProduct,
  setDeliveryInfo,
  updateOrderProduct,
} from "../store/oneOrderSlice.ts";
import { CartItem } from "../types/cart";
import { DeliveryInformation } from "../types/deliveryInfo.ts";

export const useOneOrder = () => {
  const dispatch = useDispatch();
  const orderProducts = useSelector((state: RootState) => state.oneOrder.items);

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
  };
};
