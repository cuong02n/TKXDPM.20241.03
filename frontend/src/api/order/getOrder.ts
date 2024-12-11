import apiClient from "../apiClient";

/**
 *
 * @param userId
 * @param cartId
 * @returns orderId: string
 */
export const getOrderApi = async (userId: string, cartId: string) => {
  try {
    const response = await apiClient.get(
      `/api/order/create/?userId=${userId}&cartId=${cartId}`
    );
    return response.data;
  } catch (error) {
    console.error("Error creating order:", error);
    throw error;
  }
};
