import { DeliveryInformation } from "./deliveryInfo";
import { OneOrder } from "./oneOrder";

export interface Invoice {
  id: string;
  order: OneOrder;
  orderId: number;
  deliveryInfo: DeliveryInformation;
  shippingFee: number;
  totalWithoutVAT: number;
  totalWithVAT: number;
  total: number;
}
