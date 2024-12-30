import { createAsyncThunk } from "@reduxjs/toolkit";
import apiClient from "../../../api/apiClient.ts";
import { CartItem } from "../../types/cart.ts";
const sampleProducts: CartItem[] = [
  {
    id: "1",
    name: "CD 1",
    price: 120000,
    imageUrl: "",
    quantity: 10,
    description: "temp",
    category: "book",
    isRush: false,
  },
];

export const fetchCart = createAsyncThunk(
  "cart/fetchCart",
  async (cartId: string, { rejectWithValue }) => {
    try {
      const response = await apiClient.get(`/cart/get/${cartId}`);
      return response.data;
    } catch (err) {
      return rejectWithValue(err.response?.data || "Error fetching cart");
    }
  }
);

interface UpdateCartItemProps {
  productId: string;
  cartId: string;
  quantity: number;
}

export const addCartItem = createAsyncThunk(
  "cart/addCartItem",
  async (
    { productId, quantity }: { productId: string; quantity: number },
    { rejectWithValue }
  ) => {
    try {
      const params = {
        productId: Number(productId),
        quantity: Number(quantity),
      };
      const response = await apiClient.post(`/cart/add-to-cart`, null, {
        params,
      });
      // console.log("ADD TO CART ", response);
      return response.data;
    } catch (err) {
      return rejectWithValue(err.response?.data || "Error adding to cart");
    }
  }
);

export const getCart = createAsyncThunk(
  "cart/getCart",
  async (_, { rejectWithValue }) => {
    try {
      const response = await apiClient.get(`/cart`);
      console.log("GET CART ", response.data);
      const products = response.data.map((each) => {
        return {
          id: each.product.id,
          name: each.product.name,
          price: each.product.price,
          imageUrl: each.product.mediaUrls[0],
          quantity: each.quantity,
          description: each.product.description,
          category: each.product.category,
          isRush: false,
        };
      });
      return products;
    } catch (err) {
      return rejectWithValue(err.response?.data || "Error getting cart");
    }
  }
);

export const updateCartItem = createAsyncThunk(
  "cart/updateCartItem",
  async ({ productId, quantity }: UpdateCartItemProps, { rejectWithValue }) => {
    try {
      const response = await apiClient.put(`/cart/update/`, {
        productId,
        quantity,
      });
      return response.data;
    } catch (err) {
      return rejectWithValue(err.response?.data || "Error updating cart");
    }
  }
);

export const deleteCartItem = createAsyncThunk(
  "cart/deleteCartItem",
  async (productId: string, { rejectWithValue }) => {
    try {
      const response = await apiClient.delete(`/cart/delete/${productId}`);
      return response.data;
    } catch (err) {
      return rejectWithValue(err.response?.data || "Error deleting cart item");
    }
  }
);
