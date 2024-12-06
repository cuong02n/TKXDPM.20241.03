import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store/store";
import { addToCart, removeFromCart } from "../store/cartSlice";

export const useCart = () => {
  const dispatch = useDispatch();
  const cartItems = useSelector((state: RootState) => state.cart.items);

  const addItemToCart = (item: any) => {
    dispatch(addToCart(item));
  };

  const removeItemFromCart = (itemId: string) => {
    dispatch(removeFromCart(itemId));
  };

  return {
    cartItems,
    addItemToCart,
    removeItemFromCart,
  };
};
