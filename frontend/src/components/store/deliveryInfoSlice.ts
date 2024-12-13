import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { DeliveryInformation } from "../types/deliveryInfo";

const initialState: DeliveryInformation = {
  address: "",
  name: "",
  phone: "",
  province: "",
  instructions: "",
};

const deliveryInfoSlice = createSlice({
  name: "deliveryInfo",
  initialState,
  reducers: {
    updateInfo: (
      state,
      action: PayloadAction<{ field: string; value: string }>
    ) => {
      const { field, value } = action.payload;
      state[field] = value;
    },
  },
});

export const { updateInfo } = deliveryInfoSlice.actions;
export default deliveryInfoSlice.reducer;
