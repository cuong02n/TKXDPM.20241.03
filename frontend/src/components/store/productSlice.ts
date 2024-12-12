import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Product } from "../types/product";
import { createAsyncThunk } from "@reduxjs/toolkit";
import axios from "axios";
import { API_BASE_URL } from "../constants";

interface ProductState {
  items: Product[];
}

const initialState: ProductState = {
  items: [],
};

const productSlice = createSlice({
  name: "product",
  initialState,
  reducers: {
    setProducts: (state, action: PayloadAction<Product[]>) => {
      state.items = action.payload;
    },
    addProduct: (state, action: PayloadAction<Product>) => {
      state.items.push(action.payload);
    },
    removeProduct: (state, action: PayloadAction<string>) => {
      state.items = state.items.filter(
        (product) => product.id !== action.payload
      );
    },
  },
});

export const getProductWithId = createAsyncThunk(
  "product/:id",
  async (id: number, { rejectWithValue }) => {
    try {
      const res = await axios.get(`${API_BASE_URL}/product/${id}`);
      return res.data.data;
    } catch (_) {
      return rejectWithValue(`could not get product with id ${id}`);
    }
  }
);

export const { setProducts, addProduct, removeProduct } = productSlice.actions;
export default productSlice.reducer;
