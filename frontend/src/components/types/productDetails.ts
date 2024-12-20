import { ProductCategory } from "../constants";

export interface DetailedProduct {
  id: string;
  name: string;
  description: string;
  price: number;
  available: number;
  mediaUrls: string[];
  additionalData: { size: string; color: string };
  category: ProductCategory;
  createdTime: string;
  updatedTime: string;
}
