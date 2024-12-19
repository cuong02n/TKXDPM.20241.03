import { createSlice, PayloadAction } from "@reduxjs/toolkit";
<<<<<<< HEAD
import { CartItem, CartState } from "../types/cart";
import { addCartItem } from "./thunk/cartThunk.ts";

const initialState: CartState = { items: [], loading: false, error: null };
=======

// Định nghĩa kiểu cho item trong giỏ hàng
export interface CartItem {
  id: string;
  name: string;
  price: number;
  quantity: number; // Thêm trường quantity
  imageUrl: string;
  description: string;
  category: string;
}

// Định nghĩa kiểu cho state của giỏ hàng
interface CartState {
  items: CartItem[];
}

const initialState: CartState = { items: [] };
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69

const cartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {
    addToCart: (state, action: PayloadAction<CartItem>) => {
<<<<<<< HEAD
      const existingItem = state.items.find(
        (item) => item.id === action.payload.id
      );
      if (existingItem) {
        existingItem.quantity += action.payload.quantity;
      } else {
        state.items.push(action.payload);
      }
=======
      state.items.push(action.payload);
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
    },
    removeFromCart: (state, action: PayloadAction<string>) => {
      // action.payload là ID của sản phẩm cần xóa
      state.items = state.items.filter((item) => item.id !== action.payload);
    },
<<<<<<< HEAD
    updateCartItem: (
      state,
      action: PayloadAction<{ id: string; quantity: number }>
    ) => {
      const { id, quantity } = action.payload;
      const item = state.items.find((item) => item.id === id);
      if (item) {
        item.quantity = quantity;
      }
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(addCartItem.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(addCartItem.fulfilled, (state) => {
        state.loading = false;
      })
      .addCase(addCartItem.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  },
});

export const { addToCart, removeFromCart, updateCartItem } = cartSlice.actions;
=======
  },
});

export const { addToCart, removeFromCart } = cartSlice.actions;
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
export default cartSlice.reducer;
