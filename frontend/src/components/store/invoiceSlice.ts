import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Invoice } from "../types/invoice";
import { DeliveryInformation } from "../types/deliveryInfo";

const initialState: Invoice = {
  id: "",
  order: {
    orderId: "",
    userId: "",
    items: [],
    totalAmount: 0,
    shippingFee: 0,
    deliveryInfo: {
      name: "",
      phone: "",
      province: "",
      address: "",
      instructions: "",
    },
    status: "pending",
    loading: false,
    error: null,
  },
  deliveryInfo: {
    name: "",
    phone: "",
    province: "",
    address: "",
    instructions: "",
  },
  shippingFee: 0,
  totalWithoutVAT: 0,
  totalWithVAT: 0,
  total: 0,
  orderId: 0,
};

const invoiceSlice = createSlice({
  name: "invoice",
  initialState,
  reducers: {
    setInvoiceInfo: (
      state,
      action: PayloadAction<{
        shippingFee: number;
        totalWithoutVAT: number;
        totalWithVAT: number;
        total: number;
        orderId: number;
      }>
    ) => {
      const { shippingFee, totalWithoutVAT, totalWithVAT, total, orderId } =
        action.payload;
      state.shippingFee = shippingFee;
      state.totalWithoutVAT = totalWithoutVAT;
      state.totalWithVAT = totalWithVAT;
      state.total = total;
      state.orderId = orderId;
    },
    setInitialInvoice: (state) => {
      return initialState;
    },
  },
});

export const { setInitialInvoice, setInvoiceInfo } = invoiceSlice.actions;
export default invoiceSlice.reducer;
