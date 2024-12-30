import { CartItem } from "./cart";
import { DeliveryInformation } from "./deliveryInfo";

export interface OneOrder {
  orderId: string;
  userId: string;
  items: CartItem[];

  totalAmount: number;
  shippingFee: number;
  deliveryInfo: DeliveryInformation;
  status: "pending" | "completed" | "cancelled";
  loading?: boolean;
  error?: string | null;
}

export interface OrderState {
  items: OneOrder[];
  loading: boolean;
  error: string | null;
}
