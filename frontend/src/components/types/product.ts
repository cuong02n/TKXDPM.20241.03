import { ProductCategory } from "../constants"; // Import ProductCategory

export interface Product {
  id: string;
  name: string;
  description: string;
  price: number;
  category: ProductCategory; // Loại sản phẩm có thể là "book", "cd", hoặc "dvd"
  imageUrl: string;
  quantity: number;
}
