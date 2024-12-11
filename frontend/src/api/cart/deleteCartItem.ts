import apiClient from "../apiClient";

export const deleteCartItemApi = async (cartId: string, productId: string) => {
  try {
    const response = await apiClient.delete(
      `/api/cart/delete/?cartId=${cartId}&productId=${productId}`
    );
    return response.data;
  } catch (error) {
    console.error("Error deleting cart item:", error);
    throw error;
  }
};
