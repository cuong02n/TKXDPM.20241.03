import React from "react";
import { CartItem } from "../types/cart";
import { formatCurrency } from "../utils/format.ts";

const InvoiceProduct = ({ product }: { product: CartItem }) => {
  const price = formatCurrency(product.price * product.quantity);
  return (
    <div
      className={`flex items-center justify-between text-lg ${
        product.isRush ? "text-orange-600" : ""
      }`}
    >
      <p>
        <span className="font-bold">{product.quantity}</span> x {product.name}
      </p>
      <div className="flex items-center gap-4">
        {product.isRush && (
          <span className="text-orange-500 font-bold border-2 border-orange-700 px-2 rounded-lg bg-yellow-50">
            Rush
          </span>
        )}
        <p>{price}</p>
      </div>
    </div>
  );
};

export default InvoiceProduct;
