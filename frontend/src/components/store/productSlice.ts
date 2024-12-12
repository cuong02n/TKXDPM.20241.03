import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Product } from "../types/product";
import { fetchProducts } from "./thunk/productThunk.ts";

interface ProductState {
  items: Product[];
  loading: boolean;
  error: string | null;
}

const initialState: ProductState = {
  items: [],
  loading: false,
  error: null,
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
        (product) => product.id !== action.payload,
      );
    },
  },
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
});

<<<<<<< HEAD
=======
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

>>>>>>> db2edc2 (Update loading page)
export const { setProducts, addProduct, removeProduct } = productSlice.actions;
export default productSlice.reducer;
