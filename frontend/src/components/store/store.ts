// src/redux/store.ts
import { configureStore } from "@reduxjs/toolkit";
import authReducer from "../store/authSlice.ts"; // Import authReducer
import cartReducer from "../store/cartSlice.ts"; // Giả sử bạn có reducer cho giỏ hàng
import wishlistReducer from "../store/wishlistSlice.ts"; // Import wishlistReducer
import productReducer from "../store/productSlice.ts";
import orderReducer from "../store/orderSlice.ts";
import oneOrderReducer from "../store/oneOrderSlice.ts";
import deliveryInfoReducer from "../store/deliveryInfoSlice.ts";
import { thunk } from "redux-thunk";

const store = configureStore({
  reducer: {
    auth: authReducer, // Kết nối authSlice vào store
    cart: cartReducer, // Kết nối cartSlice vào store
    wishlist: wishlistReducer, // Kết nối wishlistSlice vào store
    product: productReducer,
    order: orderReducer,
    oneOrder: oneOrderReducer,
    deliveryInfo: deliveryInfoReducer,
  },
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(thunk),
});

// Định nghĩa RootState dựa trên store
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;
