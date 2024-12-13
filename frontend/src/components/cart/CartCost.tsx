import React from "react";
import { useSelector } from "react-redux";
import { formatCurrency } from "../utils/format.ts";

const CartCost = () => {
  const cartItems = useSelector((state: any) => state.oneOrder.items);
  const total = formatCurrency(
    cartItems.reduce((acc: number, item: any) => {
      return acc + item.price * item.quantity;
    }, 0)
  );
  return (
    <div>
      {cartItems.map((item: any) => (
        <div className="flex justify-between mx-4">
          <p>{item.name}</p>
          <p>{formatCurrency(item.price * item.quantity)}</p>
        </div>
      ))}
      <hr className="border-t-2 border-dashed border-zinc-500 my-4" />
      <div className="flex justify-between">
        <p>{"Total (excluding VAT)"}</p>
        <p className="font-bold text-3xl">{total}</p>
      </div>
    </div>
  );
};

export default CartCost;
