import React from "react";
import { Product } from "../types/product";
import { Image, Link } from "lucide-react";
import { useNavigate } from "react-router-dom";

export const FavoriteProductCard = ({ product }: { product: Product }) => {
  const navigate = useNavigate();
  return (
    <div
      className="main-container border p-4 rounded-2xl shadow hover:shadow-lg"
      onClick={() => navigate(`/products/${product.id}`)}
    >
      <div className="flex flex-col">
        <div className="w-full h-96 rounded-[20px] overflow-hidden mb-4">
          {product.imageUrl ? (
            <img
              src={product.imageUrl}
              alt={product.name}
              className="w-full h-full object-scale-down"
            />
          ) : (
            <Image className="h-full w-full text-zinc-400 bg-slate-200" />
          )}
        </div>

        <div className="flex justify-between items-center mb-4">
          <h2 className="text-lg font-bold">{product.name}</h2>
          <p className="text-gray-700 font-semibold">{product.price} VND</p>
        </div>

        <div className="text-slate-500 mb-4">{product.description}</div>

        <div className="flex justify-between items-center">
          <p>
            Available:{" "}
            <span className="font-bold text-[#583cf1]">{product.quantity}</span>
          </p>
          <Link
            to={`/products/${product.id}`}
            className="text-blue-600 hover:underline"
          >
            View Details
          </Link>
        </div>
      </div>
    </div>
  );
};
