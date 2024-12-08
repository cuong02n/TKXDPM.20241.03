import React from "react";
import DeliveryInfo from "../../delivery/DeliveryInfo.tsx";
import { DeliveryInformation } from "../../types/deliveryInfo.ts";
import { useNavigate } from "react-router-dom";

const DeliveryPage = ({ hasRush = true }: { hasRush: boolean }) => {
  const navigate = useNavigate();
  const [deliveryInfo, setDeliveryInfo] = React.useState<DeliveryInformation>({
    name: "",
    phone: "",
    province: "",
    address: "",
    instructions: "",
    time: "",
  });
  const updateInfo = (field: string, value: any) => {
    setDeliveryInfo({ ...deliveryInfo, [field]: value });
  };
  const handlePlaceOrder = () => {
    navigate("/checkout");
  };
  return (
    <div className="container mx-auto py-10 min-h-screen">
      <h1 className="text-2xl font-bold mb-5">Delivery information</h1>
      <DeliveryInfo
        hasRush={hasRush}
        deliveryInfo={deliveryInfo}
        updateInfo={updateInfo}
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
