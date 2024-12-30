import axios, { AxiosResponse } from "axios";
import { toast } from "react-toastify";

interface ApiResponse<T> {
  error: number;
  message: string;
  data: T;
}

const apiClient = axios.create({
  baseURL: "/api",
  headers: {
    "Content-Type": "application/json",
  },
});

apiClient.interceptors.request.use(
  (config) => {
    let token = localStorage.getItem("jwt");
    if (!token) {
      localStorage.setItem(
        "jwt",
        "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQ1VTVE9NRVIiLCJzdWIiOiJndWVzdEBndWVzdC5jb20iLCJpYXQiOjE3MzU1NDk5NzYsImV4cCI6MTc0NzU0OTk3Nn0.CD3KepdSsqlALhg5K9ZZ-LdqHeELMxVn3_8Shl7GQS0"
      );
      token = localStorage.getItem("jwt");
      config.headers.Authorization = `Bearer ${token}`;
    }
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
  (response: AxiosResponse<ApiResponse<any>>) => {
    return response;
  },
  (error) => {
    const { status } = error.response;

    switch (status) {
      case 400:
        // Handle bad request error
        toast.error(
          `${
            error.response?.data?.message ??
            "Bad request - please check your input data."
          }`
        );
        // toast.error("Bad request - please check your input data.");
        break;
      case 401:
        // Handle unauthorized error
        toast.error(
          `${
            error.response?.data?.message ??
            "Unauthorized access - perhaps you need to log in?"
          }`
        );
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
