import { DeliveryInformation } from "../../components/types/deliveryInfo";
import apiClient from "../apiClient";

/**
 *
 * @param orderId
 * @param deliveryInfo :{
 *   name: string;
 *   phone: string;
 *   province: string;
 *   address: string;
 *   instructions?: string;
 *   time?: any;
 * }
 * @returns deliveryInfoId: string
 */
export const createDeliveryInfoApi = async (
  orderId: string,
  deliveryInfo: DeliveryInformation
) => {
  try {
    const response = await apiClient.post(
      `/api/delivery-info/create/${orderId}`,
      deliveryInfo
    );
    return response.data;
  } catch (error) {
    console.error("Error creating delivery info:", error);
    throw error;
  }
};
