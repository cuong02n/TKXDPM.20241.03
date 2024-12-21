import { createAsyncThunk } from "@reduxjs/toolkit";
import apiClient from "../../../api/apiClient.ts";
import { Product } from "../../types/product";

export const fetchFavorites = createAsyncThunk(
  "products/wish-list",
  async (_: any, { rejectWithValue }) => {
    try {
      const response = await apiClient.get("/product/wish-list");
      if (response.data.data && response.data.error === 0) {
        return response.data.data.map((val) => {
          return {
            id: val.product.id,
            name: val.product.name,
            description: val.product.description,
            price: val.product.price,
            category: val.product.category,
            imageUrl: val.product.mediaUrls[0],
          } as Product;
        });
      }
    } catch (err) {
      return rejectWithValue(err.response?.data || "Error fetching favorites");
    }
  },
);

export const addFavorite = createAsyncThunk(
  "products/add-favorite",
  async (productId: number, { rejectWithValue }) => {
    try {
      const response = await apiClient.post(
        `/product/wish-list?productId=${productId}`,
      );
      if (response.data.error === 0) {
        return productId;
      } else {
        return rejectWithValue("Error adding favorite");
      }
    } catch (err) {
      return rejectWithValue(err.response?.data || "Error adding favorite");
    }
  },
);
