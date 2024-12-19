// Chứa các API endpoints
export const API_BASE_URL = "https://api.example.com";

// Các trạng thái của đơn hàng
export const ORDER_STATUSES = ["pending", "completed", "cancelled"] as const;

// Các loại sản phẩm (có thể dùng trong filter, sort)
export const PRODUCT_CATEGORIES = ["DVD", "book", "CD"];

export type ProductCategory = (typeof PRODUCT_CATEGORIES)[number];
<<<<<<< HEAD
export type OrderStatus = (typeof ORDER_STATUSES)[number];
=======
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
