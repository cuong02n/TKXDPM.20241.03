import apiClient from "../apiClient";

interface AddToCartApiProps {
  productId: string;
  cartId: string;
  quantity: number;
}

export const addCartItemApi = async ({
  productId,
  cartId,
  quantity,
}: AddToCartApiProps) => {
  try {
    const response = await apiClient.post(`/api/cart/add/${cartId}`, {
      productId,
      quantity,
    });
    return response.data;
  } catch (error) {
    console.error("Error adding to cart:", error);
    throw error;
  }
};
