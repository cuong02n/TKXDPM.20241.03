import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store/store";
import {
  addToCart,
  removeFromCart,
  updateCartItem,
} from "../store/cartSlice.ts";

export const useCart = () => {
  const dispatch = useDispatch();
  const cartItems = useSelector((state: RootState) => state.cart.items);

  const addItemToCart = (item: any) => {
    dispatch(addToCart(item));
  };

  const removeItemFromCart = (itemId: string) => {
    dispatch(removeFromCart(itemId));
  };

  const updateItemInCart = (id: string, quantity: number) => {
    dispatch(updateCartItem({ id, quantity }));
  };

  return {
    cartItems,
    addItemToCart,
    removeItemFromCart,
    updateItemInCart,
  };
};
