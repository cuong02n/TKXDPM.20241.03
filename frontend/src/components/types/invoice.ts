import { DeliveryInformation } from "./deliveryInfo";
import { OneOrder } from "./oneOrder";

export interface Invoice {
  id: string;
  order: OneOrder;
  deliveryInfo: DeliveryInformation;
  total: number;
  VAT: number;
  shippingFee: number;
}
