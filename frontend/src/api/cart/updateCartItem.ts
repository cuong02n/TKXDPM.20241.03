import apiClient from "../apiClient";

interface UpdateCartItemApiProps {
  productId: string;
  cartId: string;
  quantity: number;
}

export const updateCartItemApi = async ({
  cartId,
  productId,
  quantity,
}: UpdateCartItemApiProps) => {
  try {
    const response = await apiClient.put(`/api/cart/update/${cartId}`, {
      productId,
      quantity,
    });
    return response.data;
  } catch (error) {
    console.error("Error updating cart:", error);
    throw error;
  }
};
