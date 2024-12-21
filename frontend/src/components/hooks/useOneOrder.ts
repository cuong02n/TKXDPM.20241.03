import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../store/store";
import {
  addOrderProduct,
  deleteOrderProduct,
  setDeliveryInfo,
  updateOrderProduct,
} from "../store/oneOrderSlice.ts";
import { CartItem } from "../types/cart";
import { DeliveryInformation } from "../types/deliveryInfo.ts";
import {
  getAllOrders,
  placeOrder,
  placeRushOrder,
} from "../store/thunk/orderThunk.ts";
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
      productIds: orderProducts.map((item) => Number(item.id)),
      address: deliveryInfo.address,
      phone: deliveryInfo.phone,
      province: deliveryInfo.province,
      shippingInstruction: deliveryInfo.instructions,
    };
    try {
      const response = await apiClient.post("/order/place-order", data);
      console.log("PLACE ORDER RESPONSE", response.data.data);
      const inp = response.data.data;
      const info = {
        shippingFee: inp.shippingFee,
        totalWithoutVAT: inp.totalWithoutVAT,
        totalWithVAT: inp.totalWithVAT,
        total: inp.total,
        orderId: inp.orderId,
      };
      dispatch(setInvoiceInfo(info));
    } catch (error) {
      toast.error(error.response?.data || "Error placing order");
    }
  };

  const placeOrderRush = () => {
    const data = {
      productIds: orderProducts.map((item) => Number(item.id)),
      address: deliveryInfo.address,
      phone: deliveryInfo.phone,
      province: deliveryInfo.province,
      shippingInstruction: deliveryInfo.instructions,
      timeInMinutes: 120,
    };
    dispatch(placeRushOrder(data));
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

  return {
    orderProducts,
    addProductToOrder,
    deleteProductFromOrder,
    updateProductInOrder,
    setDelivery,
    placeOrderNormal,
    placeOrderRush,
    getMyOrders,
  };
};
