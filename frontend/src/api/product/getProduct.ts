import apiClient from "../apiClient";

export const getAllProductApi = async (id: string) => {
  try {
    const response = await apiClient.get("/api/product/getAll");
    return response.data;
  } catch (error) {
    console.error("Error getting all product:", error);
    throw error;
  }
};

/**
 * 
 * @param id 
 * @returns {
 * products: Product[];
 * }
 * type Product{
 * id: string;
  name: string;
  description: string;
  price: number;
  category: "book", "cd", hoặc "dvd"
  imageUrl: string;
  quantity: number;
 * }
 */
export const getProductByIdApi = async (id: string) => {
  try {
    const response = await apiClient.get(`/api/product/getOne/${id}`);
    return response.data;
  } catch (error) {
    console.error("Error getting product by id:", error);
    throw error;
  }
};
