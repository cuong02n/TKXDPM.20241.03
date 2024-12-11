import apiClient from "../apiClient";

export const loginApi = async (email: string, password: string) => {
  try {
    const response = await apiClient.post("/api/auth/login", {
      email,
      password,
    });
    return response.data;
  } catch (error) {
    console.error("Error logging in:", error);
    throw error;
  }
};

export const registerApi = async (
  email: string,
  name: string,
  password: string
) => {
  try {
    const response = await apiClient.post("/api/auth/register", {
      email,
      name,
      password,
    });
    return response.data;
  } catch (error) {
    console.error("Error registering:", error);
    throw error;
  }
};
