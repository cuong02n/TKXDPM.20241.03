import React from "react";
import DeliveryInfo from "../../delivery/DeliveryInfo.tsx";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { useDelivery } from "../../hooks/useDelivery.ts";
import { useOneOrder } from "../../hooks/useOneOrder.ts";
import { useSelector } from "react-redux";
import { RootState } from "../../store/store.ts";

const DeliveryPage = () => {
  const navigate = useNavigate();
  const [rush, setRush] = React.useState<boolean>(() => {
    const isRush = localStorage.getItem("rush");
    if (isRush) return JSON.parse(isRush);
  });
  const checkInfoValid = React.useRef<() => boolean>(() => true);
  const { updateDelivery } = useDelivery();
  const { setDelivery, placeOrderNormal, placeOrderRush } = useOneOrder();
  const deliveryInfo = useSelector((state: RootState) => state.deliveryInfo);
  const updateInfo = (field: string, value: any) => {
    updateDelivery(field, value.toString());
    // setDeliveryInfo({ ...deliveryInfo, [field]: value });
  };

  const handlePlaceOrder = async () => {
    if (checkInfoValid.current()) {
      setDelivery(deliveryInfo);
      // if (rush) {
      //   placeOrderRush();
      // } else {
      const res = await placeOrderNormal();
      console.log("PLACE ORDER RESPONSE DELIVERYPAFE", res);
      if (!res?.error) {
        navigate("/checkout");
      } else {
        console.log(res?.error);
        navigate("/cart", { state: { error: res?.error } });
      }
    } else {
      toast.error("Please fill in all required fields.");
    }
  };
  return (
    <div className="container mx-auto py-10 min-h-screen">
      <h1 className="text-2xl font-bold mb-5">Delivery information</h1>
      <DeliveryInfo
        hasRush={rush}
        deliveryInfo={deliveryInfo}
        updateInfo={updateInfo}
        checkInfo={checkInfoValid}
      />
      <div className="flex justify-end">
        <button
          onClick={handlePlaceOrder}
          className="w-48 mt-4 bg-blue-600 text-white py-3 rounded-lg hover:bg-gradient-to-r from-blue-900 to-blue-800 transition-colors duration-300 font-semibold shadow-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2 active:scale-[0.99] disabled:opacity-50 disabled:cursor-not-allowed"
        >
          <span className="text-xl drop-shadow-md">Place order</span>
        </button>
      </div>
    </div>
  );
};

export default DeliveryPage;
