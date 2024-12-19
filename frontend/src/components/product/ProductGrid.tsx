import React from "react";
import ProductCard from "./ProductCard.tsx";
<<<<<<< HEAD
import { Product } from "../types/product.ts";

const ProductGrid = ({ products }: { products: Array<Product> }) => {
=======

const ProductGrid = ({
  products,
}: {
  products: Array<{ id: number; name: string; price: number; image: string }>;
}) => {
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
  return (
    <div className="grid grid-cols-3 gap-4">
      {products.map((product) => (
        <ProductCard key={product.id} product={product} />
      ))}
    </div>
  );
};

export default ProductGrid;
