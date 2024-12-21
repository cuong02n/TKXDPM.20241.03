import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";

interface PayInfo {
    total: number;
    orderInfo: string;
}
export const submitOrder = createAsyncThunk(
  "pay/submitOrder",
  async ( data : PayInfo , { rejectWithValue }) => {
    try {
      const response = await axios.post("/api/vnpay/submitOrder", data );
      return response.data; 
    } catch (error) {
      console.error("Error Pay order:", error);
      return rejectWithValue(error.response?.data || "Please try again.");
    }
  }
);

// Initial state
const initialState = {
  loading: false,
  error: null as string | null,
};

const paySlice = createSlice({
  name: "pay",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(submitOrder.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(submitOrder.fulfilled, (state, action) => {
        state.loading = false;
        // Redirect to the payment URL
        window.location.href = action.payload;
      })
      .addCase(submitOrder.rejected, (state, action) => {
        state.loading = false;
        state.error = (action.payload as string) || "An error occurred.";
        alert(state.error);
      });
  },
});

export default paySlice.reducer;