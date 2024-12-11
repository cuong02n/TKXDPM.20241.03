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

export const getProductByIdApi = async (id: string) => {
  try {
    const response = await apiClient.get(`/api/product/getOne/${id}`);
    return response.data;
  } catch (error) {
    console.error("Error getting product by id:", error);
    throw error;
  }
};
