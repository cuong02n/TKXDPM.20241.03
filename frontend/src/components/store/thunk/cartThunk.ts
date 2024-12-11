import { createAsyncThunk } from "@reduxjs/toolkit";
import apiClient from "../../../api/apiClient";

export const fetchCart = createAsyncThunk(
  "cart/fetchCart",
  async (cartId: string, { rejectWithValue }) => {
    try {
      const response = await apiClient.get(`/api/cart/get/${cartId}`);
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
  async ({ productId, quantity }: UpdateCartItemProps, { rejectWithValue }) => {
    try {
      const response = await apiClient.post(`/api/cart/add/`, {
        productId,
        quantity,
      });
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
      const response = await apiClient.put(`/api/cart/update/`, {
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
      const response = await apiClient.delete(`/api/cart/delete/${productId}`);
      return response.data;
    } catch (err) {
      return rejectWithValue(err.response?.data || "Error deleting cart item");
    }
  }
);
