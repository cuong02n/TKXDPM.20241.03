// Chứa các API endpoints
export const API_BASE_URL = "https://api.example.com";

// Các trạng thái của đơn hàng
export const ORDER_STATUSES = ["pending", "completed", "cancelled"] as const;

// Các loại sản phẩm (có thể dùng trong filter, sort)
export const PRODUCT_CATEGORIES = ["DVD", "book", "CD"];

export type ProductCategory = (typeof PRODUCT_CATEGORIES)[number];
