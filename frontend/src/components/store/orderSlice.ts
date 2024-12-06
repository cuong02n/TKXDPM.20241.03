import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Order } from "../types/order";

interface OrderState {
  orders: Order[];
}

const initialState: OrderState = {
  orders: [],
};

const orderSlice = createSlice({
  name: "order",
  initialState,
  reducers: {
    addOrder: (
      state,
      action: PayloadAction<Omit<Order, "orderId" | "status">>
    ) => {
      const newOrder: Order = {
        orderId: `order_${Date.now()}`, // Tạo ID tự động
        status: "pending", // Gán trạng thái mặc định
        ...action.payload,
      };
      state.orders.push(newOrder);
    },
    updateOrderStatus: (
      state,
      action: PayloadAction<{ orderId: string; status: Order["status"] }>
    ) => {
      const order = state.orders.find(
        (o) => o.orderId === action.payload.orderId
      );
      if (order) {
        order.status = action.payload.status;
      }
    },
  },
});

export const { addOrder, updateOrderStatus } = orderSlice.actions;
export default orderSlice.reducer;
