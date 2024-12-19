<<<<<<< HEAD
import { ProductCategory } from "../constants"; // Import ProductCategory
=======
import { ProductCategory } from "../constants/reduxAction"; // Import ProductCategory
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69

export interface Product {
  id: string;
  name: string;
  description: string;
  price: number;
  category: ProductCategory; // Loại sản phẩm có thể là "book", "cd", hoặc "dvd"
  imageUrl: string;
<<<<<<< HEAD
  quantity: number;
=======
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
}
