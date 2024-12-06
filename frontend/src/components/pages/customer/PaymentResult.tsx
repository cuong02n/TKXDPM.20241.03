import React from "react";

const PaymentResult = ({ success }: { success: boolean }) => {
  return (
    <div className="container mx-auto py-10">
      {success ? (
        <h1 className="text-green-600 text-2xl">Payment Successful!</h1>
      ) : (
        <h1 className="text-red-600 text-2xl">Payment Failed!</h1>
      )}
    </div>
  );
};

export default PaymentResult;
