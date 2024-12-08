import React from "react";
import ProductCard from "./ProductCard.tsx";
import { Product } from "../types/product.ts";

const ProductGrid = ({ products }: { products: Array<Product> }) => {
  return (
    <div className="grid grid-cols-3 gap-4">
      {products.map((product) => (
        <ProductCard key={product.id} product={product} />
      ))}
    </div>
  );
};

export default ProductGrid;
