import { ProductCategory } from "../constants";

export interface DetailedProduct {
  id: string;
  name: string;
  description: string;
  price: number;
  available: number;
  mediaUrls: string[];
  additionalData: object | undefined;
  category: ProductCategory;
  createdTime: string;
  updatedTime: string;
}
