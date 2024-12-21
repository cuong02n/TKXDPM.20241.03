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

export const getAllOrders = createAsyncThunk(
  "order/getAllOrders",
  async (_, { rejectWithValue }) => {
    try {
      const response = await apiClient.get("/order/my-orders");
      console.log("GET ALL ORDERS RESPONSE", response.data);

      return response.data;
    } catch (error) {
      return rejectWithValue(error.response?.data || "Error fetching orders");
    }
  }
);

export const placeRushOrder = createAsyncThunk(
  "order/placeRushOrder",
  async (data: PlaceOrderProps, { rejectWithValue }) => {
    try {
      const response = await apiClient.post("/order/place-rush-order", data);
      console.log("PLACE ORDER RESPONSE", response.data);
      return response.data;
    } catch (error) {
      return rejectWithValue(
        error.response?.data || "Error placing rush order"
      );
    }
  }
);

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
