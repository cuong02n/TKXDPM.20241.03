import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import { formatCurrency } from "../../utils/format.ts";
import PaymentMethod from "../../payment/PaymentMethod.tsx";
import axios from "axios";
import { useDispatch } from "react-redux";
import { submitOrder } from "../../store/paySlice.ts";
import { AppDispatch } from "../../store/store.ts";

const PaymentPage = () => {
  const location = useLocation();
  const { total , orderInfo } = location.state || { total: 0 };
  const [method, setMethod] = useState("vnpay");
  const paymentMethods = ["vnpay"];
  const dispatch = useDispatch<AppDispatch>()

  const handlePayOrder = async () => {
    
    // Call thẳng tới API vnpay
    // try {
    //   const response = await axios.post("/api/vnpay/submitOrder", {
    //     total,
    //     orderInfo,
    //   });
    //   const paymentUrl = response.data; 

    //   window.location.href = paymentUrl; 
    // } catch (error) {
    //   console.error("Error Pay order:", error);
    //   alert("Something went wrong. Please try again.");
    // }


    // Thông qua dispatch
    dispatch(submitOrder({ total, orderInfo }));
  };

  return (
    <div className="container mx-auto py-10 min-h-screen">
      <h1 className="text-2xl font-bold mb-5">Payment</h1>
      <div className="flex gap-3 flex-wrap">
        <div className="text-3xl flex gap-5 items-baseline w-1/3">
          <p>Total:</p>
          <p className="font-black text-4xl">{formatCurrency(total)}</p>
        </div>
        <div className="flex-1 min-w-80">
          {paymentMethods.map((item, index) => (
            <PaymentMethod
              key={index}
              method={item}
              selected={method}
              setMethod={setMethod}
            />
          ))}
          <div className="flex justify-end mt-6">
            <button
              onClick={handlePayOrder}
              className="w-48 mt-4 bg-blue-600 text-white py-3 rounded-lg hover:bg-gradient-to-r from-blue-900 to-blue-800 transition-colors duration-300 font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 active:scale-[0.99] disabled:opacity-50 disabled:cursor-not-allowed"
            >
              <span className="text-xl drop-shadow-md">Pay order </span>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PaymentPage;
