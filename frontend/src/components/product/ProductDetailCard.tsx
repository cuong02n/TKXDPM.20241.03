import React from "react";
import { DetailedProduct } from "../types/productDetails";
import { ImageCarousel } from "./ImageCarousel.tsx";
import { useCart } from "../hooks/useCart.ts";
import { Product } from "../types/product";

export const ProductDetailCard = ({
  product,
}: {
  product: DetailedProduct;
}) => {
  const { addItemToCart, cartItems } = useCart();

  const handleAddtoCart = () => {
    const currInCart = cartItems.find((item) => item.id === product.id);
    if (currInCart && currInCart.quantity + 1 > product.available) {
      alert(
        "You can't add more than the available quantity.\nCurrently in cart: " +
          currInCart.quantity +
          "\nAvailable: " +
          product.available,
      );
      return;
    }
    const toAdd: Product = {
      id: product.id,
      name: product.name,
      description: product.description,
      price: product.price,
      category: product.category,
      imageUrl: product.mediaUrls[0],
      quantity: 1,
    };
    addItemToCart(toAdd);
  };

  return (
    <div className="max-w-6xl h-96 mx-auto bg-white rounded-lg shadow-md">
      <div className="flex flex-col md:flex-row w-full h-full gap-6">
        <ImageCarousel urls={product.mediaUrls} />
        <div className="flex-1 flex flex-col justify-between">
          <div>
            <div className="flex justify-between items-center">
              <h1 className="text-2xl font-bold text-gray-800 mb-4">
                {product.name}
              </h1>
              <div className="text-sm text-gray-500 capitalize">
                Category: {product.category}
              </div>
            </div>
            <p className="text-gray-600 mb-4">{product.description}</p>

            <div className="flex flex-col gap-2 max-h-40 overflow-hidden">
              {product.additionalData &&
                Object.entries(product.additionalData).map(([key, value]) => (
                  <DetailItem
                    key={key}
                    label={key.charAt(0).toUpperCase() + key.slice(1)}
                    value={typeof value === "string" ? value : String(value)}
                  />
                ))}
              <DetailItem
                label="Last Updated"
                value={new Date(product.updatedTime).toLocaleString()}
              />
            </div>
          </div>

          <div className="mt-auto p-4 self-end">
            <div className="flex items-center gap-4">
              <div>
                <div className="text-lg font-semibold text-gray-800">
                  Price: {product.price} VND
                </div>
                <div className="text-sm text-gray-500">
                  Available Quantity: {product.available}
                </div>
              </div>
              <button
                className="px-4 py-2 bg-blue-500 text-white rounded-md shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400"
                disabled={product.available === 0}
                onClick={handleAddtoCart}
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

const DetailItem = ({ label, value }) => (
  <p className="text-base text-gray-700">
    <span className="font-medium text-gray-800">{label}:</span>{" "}
    <span className={`px-2 py-1 rounded-md `}>{value}</span>
  </p>
);
