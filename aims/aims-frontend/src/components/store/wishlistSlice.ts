// src/redux/wishlist/wishlistSlice.ts
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface WishlistState {
  items: { id: string; name: string; price: number }[]; // Ví dụ với các thuộc tính đơn giản
}

const initialState: WishlistState = {
  items: [],
};

const wishlistSlice = createSlice({
  name: "wishlist",
  initialState,
  reducers: {
    addToWishlist: (
      state,
      action: PayloadAction<{ id: string; name: string; price: number }>
    ) => {
      state.items.push(action.payload);
    },
    removeFromWishlist: (state, action: PayloadAction<string>) => {
      state.items = state.items.filter((item) => item.id !== action.payload);
    },
  },
});

export const { addToWishlist, removeFromWishlist } = wishlistSlice.actions;
export default wishlistSlice.reducer;
