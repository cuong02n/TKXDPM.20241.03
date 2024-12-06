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
      state.items.push(action.payload);
    },
    removeFromCart: (state, action: PayloadAction<string>) => {
      // action.payload là ID của sản phẩm cần xóa
      state.items = state.items.filter((item) => item.id !== action.payload);
    },
  },
});

export const { addToCart, removeFromCart } = cartSlice.actions;
export default cartSlice.reducer;
