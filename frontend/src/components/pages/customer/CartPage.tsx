import React from "react";
import { useSelector } from "react-redux";

const Cart = () => {
  const cartItems = useSelector((state: any) => state.cart.items);

  return (
    <div className="container mx-auto py-10">
      <h1 className="text-2xl font-bold mb-5">Your Cart</h1>
      {cartItems.length === 0 ? (
        <p>Your cart is empty.</p>
      ) : (
        <div>
          {cartItems.map((item: any) => (
            <div key={item.id} className="border p-4 mb-2">
              <h2>{item.name}</h2>
              <p>${item.price}</p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Cart;
