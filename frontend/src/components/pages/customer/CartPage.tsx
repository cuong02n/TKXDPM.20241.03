import React from "react";
import { useSelector } from "react-redux";
import CartProduct from "../../cart/CartProduct.tsx";
import CartCost from "../../cart/CartCost.tsx";
import { useNavigate } from "react-router-dom";

const Cart = () => {
  const cartItems = useSelector((state: any) => state.cart.items);
  const navigate = useNavigate();
  const handlePlaceOrder = () => {
    navigate("/delivery-info");
  };
  return (
    <div className="container mx-auto py-10 min-h-screen">
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
          <div className="flex-1 pl-24 ">
            <CartCost />

            <div className="flex justify-end">
              <button
                onClick={handlePlaceOrder}
                className="w-48 mt-4 bg-blue-600 text-white py-3 rounded-lg hover:bg-gradient-to-r from-blue-900 to-blue-800 transition-colors duration-300 font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 active:scale-[0.99] disabled:opacity-50 disabled:cursor-not-allowed"
              >
                <span className="text-xl drop-shadow-md">Place order</span>
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Cart;
