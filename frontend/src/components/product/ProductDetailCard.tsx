import React from "react";
import { DetailedProduct } from "../types/productDetails";
import { ImageCarousel } from "./ImageCarousel.tsx";

export const ProductDetailCard = ({
  product,
}: {
  product: DetailedProduct;
}) => {
  return (
    <div className="max-w-6xl h-96 mx-auto bg-white rounded-lg shadow-md">
      <div className="flex flex-col md:flex-row w-full h-full gap-6">
        <ImageCarousel urls={product.mediaUrls} />
        <div className="flex-1 flex flex-col justify-between">
          <div>
            <h1 className="text-2xl font-bold text-gray-800 mb-4">
              {product.name}
            </h1>
            <p className="text-gray-600 mb-4">{product.description}</p>
            <div className="text-lg font-semibold text-gray-800 mb-4">
              Price: {product.price} VND
            </div>
            <div className="text-sm text-gray-500 mb-4 capitalize">
              Category: {product.category}
            </div>
          </div>

          <div className="mt-auto p-4 self-end">
            <div className="flex items-center gap-4">
              <div className="text-sm text-gray-500">
                Available Quantity: {product.available}
              </div>
              <button
                className="px-4 py-2 bg-blue-500 text-white rounded-md shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400"
                disabled={product.available === 0}
              >
                {product.available > 0 ? "Add to Cart" : "Out of Stock"}
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
