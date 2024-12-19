import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Order } from "../types/order";
<<<<<<< HEAD
import { CartItem } from "../types/cart";
=======
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69

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
<<<<<<< HEAD
    addOrderProduct: (
      state,
      action: PayloadAction<{ orderId: String; item: CartItem }>
    ) => {
      const { orderId, item } = action.payload;
      const order = state.orders.find((o) => o.orderId === orderId);
      if (order) {
        order.items.push(item);
        order.totalAmount += item.price * item.quantity;
      }
    },
    deleteOrderProduct: (
      state,
      action: PayloadAction<{ orderId: string; productId: string }>
    ) => {
      const { orderId, productId } = action.payload;
      const order = state.orders.find((o) => o.orderId === orderId);
      if (order) {
        const index = order.items.findIndex((i) => i.id === productId);
        if (index !== -1) {
          const item = order.items[index];
          order.totalAmount -= item.price * item.quantity;
          order.items.splice(index, 1);
        }
      }
    },
  },
});

export const {
  addOrder,
  updateOrderStatus,
  addOrderProduct,
  deleteOrderProduct,
} = orderSlice.actions;
=======
  },
});

export const { addOrder, updateOrderStatus } = orderSlice.actions;
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
export default orderSlice.reducer;
