import React, { useEffect, useState } from "react";
import { PAYMENT_METHODS } from "../../assets/images/payment-method/index.ts";

interface PaymentMethodProps {
  method: string;
  selected: string;
  setMethod: (method: string) => void;
}

const PaymentMethod = ({ method, setMethod, selected }: PaymentMethodProps) => {
  const [chosen, setChosen] = useState(false);
  useEffect(() => {
    if (method === selected) {
      setChosen(true);
    }
  }, [selected]);
  const handleChooseMethod = () => {
    setMethod(method);
  };
  return (
    <div
      onClick={handleChooseMethod}
      className={`border border-solid rounded-3xl flex justify-center items-center py-8 ${
        chosen ? "border-blue-500 border-2 bg-blue-50" : "border-gray-300"
      }`}
    >
      <img
        src={PAYMENT_METHODS[method]}
        alt="VNPay payment"
        className="h-[60px] object-contain"
      />
    </div>
  );
};

export default PaymentMethod;
