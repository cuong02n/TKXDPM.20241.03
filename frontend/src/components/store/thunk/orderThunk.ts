import { createAsyncThunk } from "@reduxjs/toolkit";
import apiClient from "../../../api/apiClient.ts";

interface PayLoadOrder {
  productIds: string[];
}

export interface PlaceOrderProps {
  productIds: number[];
  name?: string;
  phone: string;
  province: string;
  address: string;
  shippingInstruction: string;
  timeInMinute?: number;
}

export const placeOrder = createAsyncThunk(
  "order/placeOrder",
  async (data: PlaceOrderProps, { rejectWithValue }) => {
    try {
      const response = await apiClient.post("/order/place-order", data);
      console.log("PLACE ORDER RESPONSE", response.data);
      return response.data;
    } catch (error) {
      return rejectWithValue(error.response?.data || "Error placing order");
    }
  }
);

export const createOrder = createAsyncThunk(
  "order/createOrder",
  async (data: PayLoadOrder, { rejectWithValue }) => {
    try {
      const response = await apiClient.post("/order/create", data);
      return response.data;
    } catch (error) {
      return rejectWithValue(error.response?.data || "Error creating order");
    }
  }
);
