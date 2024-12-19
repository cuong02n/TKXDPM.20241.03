import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Product } from "../types/product";
<<<<<<< HEAD
import { fetchProducts } from "./thunk/productThunk.ts";

interface ProductState {
  items: Product[];
  loading: boolean;
  error: string | null;
=======

interface ProductState {
  items: Product[];
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
}

const initialState: ProductState = {
  items: [],
<<<<<<< HEAD
  loading: false,
  error: null,
=======
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
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
<<<<<<< HEAD
  extraReducers: (builder) => {
    builder
      .addCase(fetchProducts.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchProducts.fulfilled, (state, action) => {
        state.loading = false;
        state.items = action.payload;
      })
      .addCase(fetchProducts.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload as string;
      });
  },
=======
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
});

export const { setProducts, addProduct, removeProduct } = productSlice.actions;
export default productSlice.reducer;
