import React from "react";
import { CartItem } from "../types/cart";
import { formatCurrency } from "../utils/format.ts";

const InvoiceProduct = ({ product }: { product: CartItem }) => {
  const price = formatCurrency(product.price * product.quantity);
  return (
    <div className="flex items-center justify-between text-lg">
      <p>
        <span className="font-bold">{product.quantity}</span> x {product.name}
      </p>
      <p>{price}</p>
    </div>
  );
};

export default InvoiceProduct;
