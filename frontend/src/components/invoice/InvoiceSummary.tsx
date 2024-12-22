import React from "react";
import { formatCurrency } from "../utils/format.ts";

interface InvoiceSummaryProps {
  subtotal: number;
  shippingFee: number;
  total: number;
  withVAT: number;
}

const InvoiceSummary = ({
  subtotal,
  shippingFee,
  total,
  withVAT,
}: InvoiceSummaryProps) => {
  return (
    <div>
      <div className="flex justify-between text-lg">
        <p>Subtotal:</p>
        <p>{formatCurrency(subtotal)}</p>
      </div>
      <div className="flex justify-between text-lg">
        <p>Subtotal with VAT:</p>
        <p>{formatCurrency(withVAT)}</p>
      </div>
      <div className="flex justify-between text-lg">
        <p>Shipping fee:</p>
        <p>{formatCurrency(shippingFee)}</p>
      </div>
      <div className="flex justify-between text-lg mt-2">
        <p className="pt-2 font-bold">Total:</p>
        <p className="border-t-2 border-t-slate-500 pt-2">
          {formatCurrency(total)}
        </p>
      </div>
    </div>
  );
};

export default InvoiceSummary;
