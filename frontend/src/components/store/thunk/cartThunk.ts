import { createAsyncThunk } from "@reduxjs/toolkit";
import apiClient from "../../../api/apiClient.ts";

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
      console.log("ADD TO CART ", response);
      return response.data;
    } catch (err) {
      return rejectWithValue(err.response?.data || "Error adding to cart");
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
