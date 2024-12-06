import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store/store";
import { addToWishlist, removeFromWishlist } from "../store/wishlistSlice";

export const useWishlist = () => {
  const dispatch = useDispatch();
  const wishlistItems = useSelector((state: RootState) => state.wishlist.items);

  const addItemToWishlist = (item: any) => {
    dispatch(addToWishlist(item));
  };

  const removeItemFromWishlist = (itemId: string) => {
    dispatch(removeFromWishlist(itemId));
  };

  return {
    wishlistItems,
    addItemToWishlist,
    removeItemFromWishlist,
  };
};
