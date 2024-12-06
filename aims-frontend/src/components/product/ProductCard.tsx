import React from "react";
import { Link } from "react-router-dom";

const ProductCard = ({
  product,
}: {
  product: { id: number; name: string; price: number; image: string };
}) => {
  return (
    <div className="border p-4 rounded shadow hover:shadow-lg">
      <img
        src={product.image}
        alt={product.name}
        className="w-full h-40 object-cover rounded"
      />
      <h2 className="text-lg font-bold mt-2">{product.name}</h2>
      <p className="text-gray-700">${product.price}</p>
      <Link
        to={`/products/${product.id}`}
        className="text-blue-600 hover:underline mt-2 inline-block"
      >
        View Details
      </Link>
    </div>
  );
};

export default ProductCard;
