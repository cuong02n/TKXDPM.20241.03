import { Image, Plus } from "lucide-react";
import React from "react";
import { Link } from "react-router-dom";
import { Product } from "../types/product.ts";
import { formatCurrency } from "../utils/format.ts";
import QuantitySetter from "../common/product-cart/QuantitySetter.tsx";
import { useCart } from "../hooks/useCart.ts";

const ProductCard = ({ product }: { product: Product }) => {
  const [number, setNumber] = React.useState<number>(1);
  const { addItemToCart } = useCart();
  const { id, quantity, ...productWithoutStore } = product;
  const increase = () => {
    if (number + 1 <= product.quantity) {
      setNumber(number + 1);
    }
  };
  const decrease = () => {
    if (number - 1 >= 1) {
      setNumber(number - 1);
    }
  };
  const handleAddtoCart = () => {
    addItemToCart({
      ...productWithoutStore,
      quantity: number,
      id: id.toString(),
    });
    setNumber(1);
  };
  return (
    <div className="border p-4 rounded-2xl shadow hover:shadow-lg">
      <div className="flex justify-between">
        <div className="flex items-center gap-4">
          <div className="w-32 h-32 rounded-[20px] overflow-hidden">
            {product.imageUrl ? (
              <img
                src={product.imageUrl}
                alt={product.name}
                className="w-full h-40 object-cover rounded"
              />
            ) : (
              <Image className="h-full w-full text-zinc-400 bg-slate-200" />
            )}
          </div>
          <div className="flex flex-col justify-between py-2 h-full">
            <h2 className="text-lg font-bold">{product.name}</h2>
            <p className="text-gray-700">{formatCurrency(product.price)}</p>
            <Link
              to={`/products/${product.id}`}
              className="text-blue-600 hover:underline mt-2 inline-block"
            >
              View Details
            </Link>
          </div>
        </div>
        <div className="flex flex-col items-center justify-between h-32 py-2 pr-1 gap-1">
          <p>
            Available:{" "}
            <span className="font-bold text-[#583cf1]">{product.quantity}</span>
          </p>
          <QuantitySetter
            number={number}
            increase={increase}
            decrease={decrease}
          />
          <button
            onClick={handleAddtoCart}
            aria-label="Add to Cart"
            className="flex items-center justify-center bg-sky-600 text-white px-4 py-2 rounded-lg hover:bg-sky-700 active:bg-sky-800 
             transition-colors duration-300 space-x-2 focus:outline-none focus:ring-2 focus:ring-sky-400 focus:ring-opacity-50"
          >
            <Plus className="w-5 h-5" />
            <span>Cart</span>
          </button>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
