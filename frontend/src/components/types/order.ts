// src/types/order.ts
import { CartItem } from "./cart";

export interface Order {
  orderId: string;
  userId: string;
  items: CartItem[];
  totalAmount: number;
  status: "pending" | "completed" | "cancelled";
}
