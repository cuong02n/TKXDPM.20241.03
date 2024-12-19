import { useDispatch, useSelector } from "react-redux";
<<<<<<< HEAD
import { AppDispatch, RootState } from "../store/store";
import {
  addToCart,
  removeFromCart,
  updateCartItem,
} from "../store/cartSlice.ts";
import { addCartItem } from "../store/thunk/cartThunk.ts";

export const useCart = () => {
  const dispatch = useDispatch<AppDispatch>();
=======
import { RootState } from "../store/store";
import { addToCart, removeFromCart } from "../store/cartSlice";

export const useCart = () => {
  const dispatch = useDispatch();
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
  const cartItems = useSelector((state: RootState) => state.cart.items);

  const addItemToCart = (item: any) => {
    dispatch(addToCart(item));
<<<<<<< HEAD
    dispatch(addCartItem({ productId: item.id, quantity: item.quantity }));
    console.log("clicked item", item);
=======
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
  };

  const removeItemFromCart = (itemId: string) => {
    dispatch(removeFromCart(itemId));
  };

<<<<<<< HEAD
  const updateItemInCart = (id: string, quantity: number) => {
    dispatch(updateCartItem({ id, quantity }));
  };

=======
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
  return {
    cartItems,
    addItemToCart,
    removeItemFromCart,
<<<<<<< HEAD
    updateItemInCart,
=======
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
  };
};
