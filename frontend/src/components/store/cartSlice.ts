import { createSlice, PayloadAction } from "@reduxjs/toolkit";

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

const cartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {
    addToCart: (state, action: PayloadAction<CartItem>) => {
      const existingItem = state.items.find(
        (item) => item.id === action.payload.id
      );
      if (existingItem) {
        existingItem.quantity += action.payload.quantity;
      } else {
        state.items.push(action.payload);
      }
    },
    removeFromCart: (state, action: PayloadAction<string>) => {
      // action.payload là ID của sản phẩm cần xóa
      state.items = state.items.filter((item) => item.id !== action.payload);
    },
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
});

export const { addToCart, removeFromCart, updateCartItem } = cartSlice.actions;
export default cartSlice.reducer;
