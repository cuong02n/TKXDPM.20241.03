import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { CartItem } from "../types/cart";
import { OneOrder } from "../types/oneOrder";
import { DeliveryInformation } from "../types/deliveryInfo";
import {
  getAllOrders,
  placeOrder,
  placeRushOrder,
} from "./thunk/orderThunk.ts";

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
  loading: false,
  error: null,
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
  extraReducers: (builder) => {
    builder
      .addCase(placeOrder.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(placeOrder.fulfilled, (state, action) => {
        state.loading = false;
        state.status = "pending";
      })
      .addCase(placeOrder.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      })
      .addCase(placeRushOrder.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(placeRushOrder.fulfilled, (state, action) => {
        state.loading = false;
        state.status = "pending";
      })
      .addCase(placeRushOrder.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      })
      .addCase(getAllOrders.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(getAllOrders.fulfilled, (state, action) => {
        state.loading = false;
        // state.items = action.payload;
      })
      .addCase(getAllOrders.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  },
});

export const {
  addOrderProduct,
  deleteOrderProduct,
  updateOrderProduct,
  setDeliveryInfo,
} = oneOrderSlice.actions;
export default oneOrderSlice.reducer;
