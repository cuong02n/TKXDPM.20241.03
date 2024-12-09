import { MinusCircle, PlusCircle } from "lucide-react";

import React from "react";

interface QuantitySetterProps {
  number: number;
  increase: () => void;
  decrease: () => void;
}

const QuantitySetter = ({
  number,
  increase,
  decrease,
}: QuantitySetterProps) => {
  return (
    <div className="flex justify-center items-center gap-1">
      <MinusCircle
        className="text-gray-600 hover:text-red-500 active:text-red-700 hover:scale-110 active:scale-95 ransition-all duration-200 cursor-pointer hover:drop-shadow-md active:drop-shadow-sm"
        onClick={decrease}
      />
      <div className="border-solid border-zinc-400 border-2 rounded-lg text-center w-10">
        {number}
      </div>
      <PlusCircle
        className="text-gray-600 hover:text-teal-500 active:text-teal-700 hover:scale-110 active:scale-95 ransition-all duration-200 cursor-pointer hover:drop-shadow-md active:drop-shadow-sm"
        onClick={increase}
      />
    </div>
  );
};

export default QuantitySetter;
