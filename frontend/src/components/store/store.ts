// src/redux/store.ts
import { configureStore } from "@reduxjs/toolkit";
import authReducer from "../store/authSlice.ts"; // Import authReducer
import cartReducer from "../store/cartSlice.ts"; // Giả sử bạn có reducer cho giỏ hàng
import wishlistReducer from "../store/wishlistSlice.ts"; // Import wishlistReducer
import productReducer from "../store/productSlice.ts";
import orderReducer from "../store/orderSlice.ts";
<<<<<<< HEAD
import oneOrderReducer from "../store/oneOrderSlice.ts";
import deliveryInfoReducer from "../store/deliveryInfoSlice.ts";
import { thunk } from "redux-thunk";
=======
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69

const store = configureStore({
  reducer: {
    auth: authReducer, // Kết nối authSlice vào store
    cart: cartReducer, // Kết nối cartSlice vào store
    wishlist: wishlistReducer, // Kết nối wishlistSlice vào store
    product: productReducer,
    order: orderReducer,
<<<<<<< HEAD
    oneOrder: oneOrderReducer,
    deliveryInfo: deliveryInfoReducer,
  },
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(thunk),
=======
  },
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
});

// Định nghĩa RootState dựa trên store
export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;
