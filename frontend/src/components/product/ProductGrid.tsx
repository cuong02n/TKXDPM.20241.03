import React from "react";
import ProductCard from "./ProductCard.tsx";

const ProductGrid = ({
  products,
}: {
  products: Array<{
    id: number;
    name: string;
    price: number;
    imageUrl: string;
    store: number;
    description: string;
    category: string;
  }>;
}) => {
  return (
    <div className="grid grid-cols-3 gap-4">
      {products.map((product) => (
        <ProductCard key={product.id} product={product} />
      ))}
    </div>
  );
};

export default ProductGrid;
