import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../store/store";
import {
  addToCart,
  removeFromCart,
  updateCartItem,
} from "../store/cartSlice.ts";
import { addCartItem, getCart } from "../store/thunk/cartThunk.ts";

export const useCart = () => {
  const dispatch = useDispatch<AppDispatch>();
  const cartItems = useSelector((state: RootState) => state.cart.items);

  const getCartItems = () => {
    dispatch(getCart());
  };

  const addItemToCart = (item: any) => {
    // dispatch(addToCart(item));
    dispatch(addCartItem({ productId: item.id, quantity: item.quantity }));
    console.log("clicked item", item);
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
    getCartItems,
  };
};
