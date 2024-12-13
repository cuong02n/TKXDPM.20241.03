import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../store/store";
import { updateInfo } from "../store/deliveryInfoSlice.ts";

export const useDelivery = () => {
  const dispatch = useDispatch();
  const delivery = useSelector((state: RootState) => state.deliveryInfo);

  const updateDelivery = (field: string, value: string) => {
    dispatch(updateInfo({ field, value }));
  };

  return {
    delivery,
    updateDelivery,
  };
};
