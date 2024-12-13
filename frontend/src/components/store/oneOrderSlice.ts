import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { CartItem } from "../types/cart";
import { OneOrder } from "../types/oneOrder";
import { DeliveryInformation } from "../types/deliveryInfo";

const initialState: OneOrder = {
  orderId: "",
  userId: "",
  items: [],
  totalAmount: 0,
  shippingFee: 0,
  deliveryInfo: {
    name: "",
    phone: "",
    province: "",
    address: "",
    instructions: "",
  },
  status: "pending",
};

const oneOrderSlice = createSlice({
  name: "order",
  initialState,
  reducers: {
    addOrderProduct: (state, action: PayloadAction<{ item: CartItem }>) => {
      const { item } = action.payload;
      state.totalAmount += item.price * item.quantity;
      state.items.push(item);
    },
    deleteOrderProduct: (
      state,
      action: PayloadAction<{ productId: string }>
    ) => {
      const { productId } = action.payload;
      const index = state.items.findIndex((i) => i.id === productId);
      if (index !== -1) {
        const item = state.items[index];
        state.totalAmount -= item.price * item.quantity;
        state.items.splice(index, 1);
      }
    },
    updateOrderProduct: (
      state,
      action: PayloadAction<{ productId: string; quantity: number }>
    ) => {
      const { productId, quantity } = action.payload;
      const item = state.items.find((i) => i.id === productId);
      if (item) {
        state.totalAmount -= item.price * item.quantity;
        state.totalAmount += item.price * quantity;
        item.quantity = quantity;
      }
    },
    setDeliveryInfo: (
      state,
      action: PayloadAction<{ info: DeliveryInformation }>
    ) => {
      const { info } = action.payload;
      state.deliveryInfo = info;
    },
  },
});

export const {
  addOrderProduct,
  deleteOrderProduct,
  updateOrderProduct,
  setDeliveryInfo,
} = oneOrderSlice.actions;
export default oneOrderSlice.reducer;
