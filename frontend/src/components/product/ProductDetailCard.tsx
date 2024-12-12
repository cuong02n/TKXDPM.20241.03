import { Product } from "../types";
import { Image } from "lucide-react";

export const ProductDetailCard = ({ product }: { product: Product }) => {
  return (
    <div className="max-w-5xl mx-auto p-0 bg-white rounded-lg shadow-md">
      <div className="flex flex-col md:flex-row gap-6">
        <div className="flex-shrink-0">
          {product.imageUrl ? (
            <img
              src={product.imageUrl}
              alt={product.name}
              className="w-full md:w-80 h-auto rounded-md shadow-sm"
            />
          ) : (
            <Image className="w-full md:w-80 h-auto rounded-md text-zinc-400 bg-slate-200" />
          )}
        </div>

        <div className="flex-1">
          <h1 className="text-2xl font-bold text-gray-800 mb-4">
            {product.name}
          </h1>
          <p className="text-gray-600 mb-4">{product.description}</p>
          <div className="text-lg font-semibold text-gray-800 mb-4">
            Price: ${product.price}
          </div>
          <div className="text-sm text-gray-500 mb-4 capitalize">
            Category: {product.category}
          </div>
          <div className="text-sm text-gray-500 mb-4">
            Available Quantity: {product.quantity}
          </div>

          <button
            className="px-4 py-2 bg-blue-500 text-white rounded-md shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400"
            disabled={product.quantity === 0}
          >
            {product.quantity > 0 ? "Add to Cart" : "Out of Stock"}
          </button>
        </div>
      </div>
    </div>
  );
};
