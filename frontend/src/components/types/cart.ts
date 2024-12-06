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
}
