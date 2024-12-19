import { createAsyncThunk } from "@reduxjs/toolkit";
import apiClient from "../../../api/apiClient.ts";
import { Product } from "../../types/product.ts";

const sampleProducts: Product[] = [
  {
    id: "1",
    name: "CD 1",
    price: 120000,
    imageUrl: "",
    quantity: 10,
    description: "temp",
    category: "book",
  },
  {
    id: "2",
    name: "DVD 1",
    price: 45000,
    imageUrl: "",
    quantity: 10,
    description: "temp",
    category: "book",
  },
  {
    id: "3",
    name: "Book 1",
    price: 98000,
    imageUrl: "",
    quantity: 10,
    description: "temp",
    category: "book",
  },
];

export const fetchProducts = createAsyncThunk(
  "product/fetchProducts",
  async (_, { rejectWithValue }) => {
    try {
      const response = await apiClient.get(`/product/all`);
      const data = response.data;
      if (data && data.error === 0) {
        const products = data.data.map((each) => {
          return {
            id: each.id,
            name: each.name,
            price: each.price,
            imageUrl: each.mediaUrls[0],
            quantity: each.available,
            description: each.description,
            category: each.category,
          };
        });
        return products;
      }
    } catch (err) {
      return rejectWithValue(err.response?.data || "Error fetching products");
    }
  },
);

export const getProductWithId = createAsyncThunk(
  "product/:id",
  async (id: string, { rejectWithValue }) => {
    try {
      const res = await apiClient.get(`/product?productId=${id}`);
      if (res.data.data && res.data.data.error === 0) {
        let product: Product = {
          imageUrl: res.data.data.mediaUrls[0],
          ...res.data.data,
        };
        return product;
      }
    } catch (_) {
      return rejectWithValue(`could not get product with id ${id}`);
    }
  },
);
