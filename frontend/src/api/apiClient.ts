import axios from "axios";

const apiClient = axios.create({
  baseURL: "http://116.96.98.153:8080",
  headers: {
    "Content-Type": "application/json",
  },
});

apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
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
        console.error("Unauthorized access - perhaps you need to log in?");
        break;
      case 403:
        // Handle forbidden error
        console.error(
          "Access forbidden - you do not have permission to access this resource."
        );
        break;
      case 404:
        // Handle not found error
        console.error(
          "Resource not found - the requested resource does not exist."
        );
        break;
      default:
        // Handle other errors
        console.error("An error occurred:", error.message);
        break;
    }

    return Promise.reject(error);
  }
);

export default apiClient;
