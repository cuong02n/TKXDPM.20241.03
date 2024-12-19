<<<<<<< HEAD
import React, { useEffect } from "react";
import ProductGrid from "../../product/ProductGrid.tsx";
import { Product } from "../../types/product.ts";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../store/store.ts";
import { fetchProducts } from "../../store/thunk/productThunk.ts";

const Home = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { items, loading, error } = useSelector(
    (state: RootState) => state.product
  );
  const products = items;
  useEffect(() => {
    dispatch(fetchProducts());
  }, [dispatch]);
  return (
    <div className="container mx-auto py-10 min-h-screen">
      <h1 className="text-3xl font-bold mb-5">Welcome to the Store</h1>
      <ProductGrid products={products} />
=======
import React from "react";
import ProductGrid from "../../product/ProductGrid.tsx";

const sampleProducts = [
  { id: 1, name: "CD 1", price: 10, image: "image_url_1" },
  { id: 2, name: "DVD 1", price: 15, image: "image_url_2" },
  { id: 3, name: "Book 1", price: 20, image: "image_url_3" },
];

const Home = () => {
  return (
    <div className="container mx-auto py-10">
      <h1 className="text-3xl font-bold mb-5">Welcome to the Store</h1>
      <ProductGrid products={sampleProducts} />
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
    </div>
  );
};

export default Home;
