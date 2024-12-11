import apiClient from "../apiClient";

export const getCartApi = async (cartId: string) => {
  try {
    const response = await apiClient.get(`/api/cart/get/${cartId}`);
    return response.data;
  } catch (error) {
    console.error("Error getting cart:", error);
    throw error;
  }
};
