import React from "react";
import { useSelector } from "react-redux";
import CartProduct from "../../cart/CartProduct.tsx";
import CartCost from "../../cart/CartCost.tsx";
import { useNavigate } from "react-router-dom";
import { formatCurrency } from "../../utils/format.ts";
import { useCart } from "../../hooks/useCart.ts";

const Cart = () => {
  const cartItems = useSelector((state: any) => state.cart.items);
  const orderItems = useSelector((state: any) => state.oneOrder.items);
  const [rush, setRush] = React.useState<boolean>(false);
  const navigate = useNavigate();
  const { getCartItems } = useCart();
  React.useEffect(() => {
    getCartItems();
  }, []);
  React.useEffect(() => {
    const isRush = localStorage.getItem("rush");
    if (isRush) setRush(JSON.parse(isRush));
    else {
      localStorage.setItem("rush", JSON.stringify(false));
    }
  }, []);
  React.useEffect(() => {
    if (orderItems.length === 0) {
      localStorage.setItem("rush", JSON.stringify(false));
      setRush(false);
    }
  }, [orderItems]);
  const cartTotal = formatCurrency(
    cartItems.reduce((acc, item) => acc + item.price * item.quantity, 0)
  );
  const handlePlaceOrder = () => {
    navigate("/delivery-info", { state: { rush } });
  };
  const handleSetRush = () => {
    localStorage.setItem("rush", JSON.stringify(!rush));
    setRush((prev) => !prev);
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
            <div className="mt-3 flex justify-center">
              <p>
                All in cart:{" "}
                <span className="font-bold text-zinc-500">{cartTotal}</span>
              </p>
            </div>
          </div>
          <div className="flex-1 pl-24 ">
            <CartCost />
            <div className="flex justify-end gap-8">
              <button
                disabled={orderItems.length === 0}
                onClick={handleSetRush}
                className={`
                  px-4 py-2 mt-4 rounded-md transition-colors duration-300 font-bold disabled:opacity-50 disabled:cursor-not-allowed
                  ${
                    rush
                      ? "bg-yellow-400 text-black hover:bg-yellow-500 border-2 border-yellow-600 shadow-lg"
                      : "bg-gray-200 text-gray-800 hover:bg-gray-300"
                  }
                `}
              >
                RUSH
              </button>

              <button
                disabled={orderItems.length === 0}
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
