import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "../../components/pages/customer/Home.tsx";
import Login from "../pages/customer/LoginForm.tsx";
import Register from "../pages/customer/RegisterForm.tsx";
import Products from "../../components/pages/customer/ProductPage.tsx";
import ProductDetail from "../../components/pages/customer/ProductDetails.tsx";
import Cart from "../pages/customer/CartPage.tsx";
import Checkout from "../pages/customer/CheckoutPage.tsx";
import Profile from "../pages/customer/Profile.tsx";
import PaymentResult from "../pages/customer/PaymentResult.tsx";
import DeliveryPage from "../pages/customer/DeliveryPage.tsx";
import PaymentPage from "../pages/customer/PaymentPage.tsx";
import FavoriteProductPage from "../../components/pages/customer/FavoriteProductsPage.tsx";
import PaymentSuccess from "../pages/customer/PaymentSuccess.tsx";

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/products" element={<Products />} />
      <Route path="/products/:id" element={<ProductDetail />} />
      <Route path="/cart" element={<Cart />} />
      <Route path="/delivery-info" element={<DeliveryPage />} />
      <Route path="/checkout" element={<Checkout />} />
      <Route path="/payment" element={<PaymentPage />} />
      <Route path="/payment/success" element={<PaymentSuccess />} />
      <Route path="/payment/failed" element={<PaymentSuccess />} />
      <Route path="/favorite" element={<FavoriteProductPage />} />
    </Routes>
  );
};

export default AppRoutes;
