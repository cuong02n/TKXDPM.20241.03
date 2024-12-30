import React, { useEffect, useState } from "react";
import { CartItem } from "../types/cart";
import { Image, Trash2 } from "lucide-react";
import { formatCurrency } from "../utils/format.ts";
import QuantitySetter from "../common/product-cart/QuantitySetter.tsx";
import { useCart } from "../hooks/useCart.ts";
import { useOneOrder } from "../hooks/useOneOrder.ts";

const CartProduct = ({
  product,
  error,
}: {
  product: CartItem;
  error?: string;
}) => {
  const [selected, setSelected] = useState<boolean>(false);
  const [orderR, setR] = useState<boolean>(false);
  const { removeItemFromCart, updateItemInCart } = useCart();
  const [showError, setShowError] = useState(false);
  const {
    addProductToOrder,
    deleteProductFromOrder,
    updateProductInOrder,
    orderProducts,
    updateRushItem,
  } = useOneOrder();
  useEffect(() => {
    if (error && error == product.id) {
      setShowError(true);
      const timer = setTimeout(() => {
        setShowError(false);
      }, 2000);
      return () => clearTimeout(timer);
    }
  }, [error, product.id]);
  useEffect(() => {
    const item = orderProducts.find((item) => item.id === product.id);
    if (item) {
      setSelected(true);
      setR(item.isRush);
    }
  }, [orderProducts, product.id]);
  const handleSelected = () => {
    if (!selected) {
      addProductToOrder(product);
    } else {
      deleteProductFromOrder(product.id);
    }
    setSelected((prev) => !prev);
  };
  const handleDelete = () => {
    removeItemFromCart(product.id);
    if (selected) {
      deleteProductFromOrder(product.id);
    }
  };
  const increase = () => {
    updateItemInCart(product.id, product.quantity + 1);
    if (selected) {
      updateProductInOrder(product.id, product.quantity + 1);
    }
  };
  const decrease = () => {
    if (product.quantity - 1 > 0) {
      updateItemInCart(product.id, product.quantity - 1);
      if (selected) {
        updateProductInOrder(product.id, product.quantity - 1);
      }
    }
  };
  const handleSetRush = () => {
    updateRushItem(product.id);
    setR((prev) => !prev);
  };
  return (
    <div
      className={`border rounded-2xl p-4 mb-2 flex justify-between cursor-pointer ${
        selected && !showError && "border-2 border-blue-500 bg-blue-50"
      } ${showError && "border-2 border-red-500 bg-red-50"}`}
      onClick={handleSelected}
    >
      <div className="flex gap-4">
        <div className="w-20 h-20 rounded-[20px] overflow-hidden">
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
        <div className="self-center">
          <h2>{product.name}</h2>
          <p className="font-bold">{formatCurrency(product.price)}</p>
        </div>
      </div>
      <div className="flex gap-24 items-center">
        <p className="text-3xl font-bold text-zinc-300">
          {formatCurrency(product.price * product.quantity)}
        </p>
        <div className="self-center flex flex-col gap-2">
          <QuantitySetter
            number={product.quantity}
            increase={increase}
            decrease={decrease}
          />
          <button
            onClick={(e) => {
              e.stopPropagation();
              handleDelete();
            }}
            className="text-white hover:text-rose-600 bg-rose-500 hover:bg-red-100 px-3 py-1.5 rounded-md border border-red-500/30 hover:border-red-500/50 transition-colors duration-200 flex items-center justify-center"
          >
            <Trash2 className="w-5 h-5" />
          </button>
        </div>
      </div>
      {selected && (
        <div
          className={`border-2  self-center p-2 rounded-lg font-bold cursor-pointer ${
            orderR
              ? "border-orange-600 bg-yellow-100 text-orange-500"
              : "border-zinc-500 bg-white text-zinc-500"
          }`}
          onClick={(e) => {
            e.stopPropagation();
            handleSetRush();
          }}
        >
          Rush
        </div>
      )}
    </div>
  );
};

export default CartProduct;
