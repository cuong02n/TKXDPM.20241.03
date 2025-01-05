import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../store/store";
import {
  addOrderProduct,
  deleteOrderProduct,
  resetProvince,
  setDeliveryInfo,
  setInitialOrder,
  setNormal,
  setRushItem,
  updateOrderProduct,
} from "../store/oneOrderSlice.ts";
import { CartItem } from "../types/cart";
import { DeliveryInformation } from "../types/deliveryInfo.ts";
import { getAllOrders } from "../store/thunk/orderThunk.ts";
import { setInvoiceInfo } from "../store/invoiceSlice.ts";
import apiClient from "../../api/apiClient.ts";
import { toast } from "react-toastify";

export const useOneOrder = () => {
  const dispatch = useDispatch<AppDispatch>();
  const orderProducts = useSelector((state: RootState) => state.oneOrder.items);
  const deliveryInfo = useSelector((state: RootState) => state.deliveryInfo);

  const getMyOrders = () => {
    dispatch(getAllOrders());
  };

  const placeOrderNormal = async () => {
    const data = {
      listProductRush: orderProducts
        .filter((item) => item.isRush)
        .map((item) => Number(item.id)),
      listProductNoRush: orderProducts
        .filter((item) => !item.isRush)
        .map((item) => Number(item.id)),
      address: deliveryInfo.address,
      phone: deliveryInfo.phone,
      province: deliveryInfo.province,
      shippingInstruction: deliveryInfo.instructions,
      timeInMinutes: 120,
    };
    try {
      const response = await apiClient.post("/order/place-order-v2", data);
      console.log("PLACE ORDER RESPONSE", response.data.data);
      const inp = response.data.data;
      const info = {
        shippingFee: inp.shippingFee,
        totalWithoutVAT: inp.totalAmountWithoutVAT,
        totalWithVAT: inp.totalAmountIncludeVAT,
        total: inp.totalAmountIncludeShippingFee,
        orderId: inp.orderId,
      };
      dispatch(setInvoiceInfo(info));
    } catch (error) {
      toast.error(error.response?.data || "Error placing order");
      const match = error.response?.data?.message?.match(
        /^This product currently not support rush: (\d+)$/
      );
      if (match !== null && match !== undefined) {
        return { error: match[1] };
      }
    }
  };

  const placeOrderRush = async () => {
    const data = {
      productIds: orderProducts.map((item) => Number(item.id)),
      address: deliveryInfo.address,
      phone: deliveryInfo.phone,
      province: deliveryInfo.province,
      shippingInstruction: deliveryInfo.instructions,
      timeInMinutes: 120,
    };
    try {
      const response = await apiClient.post("/order/place-rush-order", data);
      console.log("PLACE ORDER RESPONSE", response.data.data);
      const inp = response.data.data;
      const info = {
        shippingFee: inp.shippingFee,
        totalWithoutVAT: inp.totalAmountWithoutVAT,
        totalWithVAT: inp.totalAmountIncludeVAT,
        total: inp.totalAmountIncludeShippingFee,
        orderId: inp.orderId,
      };
      dispatch(setInvoiceInfo(info));
    } catch (error) {
      toast.error(error.response?.data || "Error placing order");
    }
  };

  const setNullProvince = () => {
    dispatch(resetProvince());
  };

  const resetNoRush = () => {
    dispatch(setNormal());
  };

  const updateRushItem = (id: string) => {
    dispatch(setRushItem({ id }));
  };

  const addProductToOrder = (item: CartItem) => {
    dispatch(addOrderProduct({ item }));
  };
  const deleteProductFromOrder = (productId: string) => {
    dispatch(deleteOrderProduct({ productId }));
  };
  const updateProductInOrder = (productId: string, quantity: number) => {
    dispatch(updateOrderProduct({ productId, quantity }));
  };
  const setDelivery = (info: DeliveryInformation) => {
    dispatch(setDeliveryInfo({ info }));
  };
  const resetOrder = () => {
    dispatch(setInitialOrder());
  };

  return {
    orderProducts,
    addProductToOrder,
    deleteProductFromOrder,
    updateProductInOrder,
    setDelivery,
    placeOrderNormal,
    placeOrderRush,
    getMyOrders,
    resetOrder,
    updateRushItem,
    resetNoRush,
    setNullProvince,
  };
};
