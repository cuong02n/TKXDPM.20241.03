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
    </div>
  );
};

export default Home;
