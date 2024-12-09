import React from "react";
import ProductGrid from "../../product/ProductGrid.tsx";
import { Product } from "../../types/product.ts";

const sampleProducts: Product[] = [
  {
    id: "1",
    name: "CD 1",
    price: 120000,
    imageUrl: "",
    quantity: 10,
    description: "temp",
    category: "book",
  },
  {
    id: "2",
    name: "DVD 1",
    price: 45000,
    imageUrl: "",
    quantity: 10,
    description: "temp",
    category: "book",
  },
  {
    id: "3",
    name: "Book 1",
    price: 98000,
    imageUrl: "",
    quantity: 10,
    description: "temp",
    category: "book",
  },
];

const Home = () => {
  return (
    <div className="container mx-auto py-10 min-h-screen">
      <h1 className="text-3xl font-bold mb-5">Welcome to the Store</h1>
      <ProductGrid products={sampleProducts} />
    </div>
  );
};

export default Home;
