import { CheckCircle, XCircle } from "lucide-react";
import React, { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { formatCurrency } from "../../utils/format.ts";
import { useDispatch } from "react-redux";
import { AppDispatch } from "../../store/store.ts";
import { setInitialOrder } from "../../store/oneOrderSlice.ts";
import { setInvoiceInfo } from "../../store/invoiceSlice.ts";
import CancelOrder from "../../cancel-order/CancelOrder.tsx";

type Props = {};

const PaymentSuccess = (props: Props) => {
  const [searchParams] = useSearchParams();

  const orderId = searchParams.get("orderId");
  const totalPrice = searchParams.get("totalPrice");
  const paymentTime = searchParams.get("paymentTime");
  const transactionId = searchParams.get("transactionId");
  const status = searchParams.get("status");
  const dispatch = useDispatch<AppDispatch>();
  useEffect(() => {
    if (status && +status === 1) {
      dispatch(setInitialOrder);
    } else {
      console.log("Payment Failed");
    }
    dispatch(setInvoiceInfo);
  }, []);
  return (
    <div className="container mx-auto py-10 min-h-screen">
      <div className="max-w-2xl mx-auto">
        {status && +status === 1 ? (
          <div className="bg-white rounded-lg shadow-md p-8 border-2 border-dashed border-teal-200">
            <div className="flex items-center justify-center mb-6">
              <CheckCircle className="w-16 h-16 text-green-500" />
            </div>
            <h1 className="text-3xl font-bold text-center text-green-500 mb-8">
              Payment Successful!
            </h1>
            <div className="space-y-4">
              <div className="flex justify-between py-3 border-b">
                <span className="text-gray-600 font-medium">Order ID</span>
                <span className="text-gray-900">{orderId}</span>
              </div>
              <div className="flex justify-between py-3 border-b">
                <span className="text-gray-600 font-medium">Total Amount</span>
                <span className="text-gray-900">
                  {formatCurrency(parseInt(totalPrice!))}
                </span>
              </div>
              <div className="flex justify-between py-3 border-b">
                <span className="text-gray-600 font-medium">
                  Transaction ID
                </span>
                <span className="text-gray-900">{transactionId}</span>
              </div>
              <div className="flex justify-between py-3 border-b">
                <span className="text-gray-600 font-medium">Payment Time</span>
                <span className="text-gray-900">{paymentTime}</span>
              </div>
            </div>
            <div className="mt-8 text-center">
              <button
                onClick={() => (window.location.href = "/")}
                className="bg-green-500 text-white px-6 py-2 rounded-md hover:bg-green-600 transition-colors"
              >
                Return to Home
              </button>
            </div>
          </div>
        ) : (
          <div className="bg-white rounded-lg shadow-md p-8 border-2 border-dashed border-rose-300">
            <div className="flex items-center justify-center mb-6">
              <XCircle className="w-16 h-16 text-red-500" />
            </div>
            <h1 className="text-3xl font-bold text-center text-red-500 mb-8">
              Payment Failed
            </h1>
            <div className="space-y-4">
              <div className="flex justify-between py-3 border-b">
                <span className="text-gray-600 font-medium">Order ID</span>
                <span className="text-gray-900">{orderId}</span>
              </div>
              <div className="flex justify-between py-3 border-b">
                <span className="text-gray-600 font-medium">Total Amount</span>
                <span className="text-gray-900">
                  {formatCurrency(parseInt(totalPrice!))}
                </span>
              </div>
              <div className="flex justify-between py-3 border-b">
                <span className="text-gray-600 font-medium">
                  Transaction ID
                </span>
                <span className="text-gray-900">{transactionId}</span>
              </div>
              <div className="flex justify-between py-3 border-b">
                <span className="text-gray-600 font-medium">Payment Time</span>
                <span className="text-gray-900">{paymentTime}</span>
              </div>
            </div>
            <div className="mt-8 text-center">
              <button
                onClick={() => (window.location.href = "/")}
                className="bg-red-500 text-white px-6 py-2 rounded-md hover:bg-red-600 transition-colors"
              >
                Return home
              </button>
            </div>
          </div>
        )}
      </div>
      <div className="w-full flex justify-center mt-8">
        <CancelOrder />
      </div>
    </div>
  );
};

export default PaymentSuccess;
