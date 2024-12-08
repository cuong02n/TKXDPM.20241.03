import React from "react";
import { useSelector } from "react-redux";
import CartProduct from "../../cart/CartProduct.tsx";
import CartCost from "../../cart/CartCost.tsx";

const Cart = () => {
  const cartItems = useSelector((state: any) => state.cart.items);

  return (
    <div className="container mx-auto py-10">
      <h1 className="text-2xl font-bold mb-5">Your Cart</h1>
      {cartItems.length === 0 ? (
        <p>Your cart is empty.</p>
      ) : (
        <div className="flex">
          <div className="w-1/2">
            {cartItems.map((item: any) => (
              <CartProduct key={item.id} product={item} />
            ))}
          </div>
          <div className="flex-1 pl-24">
            <CartCost />
          </div>
        </div>
      )}
    </div>
  );
};

export default Cart;
