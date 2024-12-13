import axios from "axios";
import { toast } from "react-toastify";

const apiClient = axios.create({
  headers: {
    "Content-Type": "application/json",
  },
});

apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("jwt");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

apiClient.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    const { status } = error.response;

    switch (status) {
      case 401:
        // Handle unauthorized error
        toast.error("Unauthorized access - perhaps you need to log in?");
        break;
      case 403:
        // Handle forbidden error
        toast.error(
          "Access forbidden - you do not have permission to access this resource."
        );
        break;
      case 404:
        // Handle not found error
        toast.error(
          "Resource not found - the requested resource does not exist."
        );
        break;
      default:
        // Handle other errors
        toast.error(`${error.response?.data?.message}`);
        break;
    }

    return Promise.reject(error);
  }
);

export default apiClient;
