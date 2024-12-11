import { createAsyncThunk } from "@reduxjs/toolkit";
import apiClient from "../../../api/apiClient";

interface PayLoadOrder {
  productIds: string[];
}

export const createOrder = createAsyncThunk(
  "order/createOrder",
  async (data: PayLoadOrder, { rejectWithValue }) => {
    try {
      const response = await apiClient.post("/api/order/create", data);
      return response.data;
    } catch (error) {
      return rejectWithValue(error.response?.data || "Error creating order");
    }
  }
);
