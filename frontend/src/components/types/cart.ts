// src/types/cart.ts

export interface CartItem {
  id: string;
  name: string;
  price: number;
  quantity: number; // Thêm trường quantity
  imageUrl: string; // Thêm trường imageUrl
  description: string; // Thêm trường description
  category: string; // Thêm trường category
}

export interface CartState {
  items: CartItem[];
<<<<<<< HEAD
  loading: boolean;
  error: string | null;
=======
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
}
