import React from "react";
import { DeliveryInformation } from "../types/deliveryInfo.ts";
import Select from "./Select.tsx";
import { provinces } from "../constants/provinces.ts";
import DeliveryDateTimePicker from "./DateTimePicker.tsx";

const DeliveryInfo = ({
  deliveryInfo,
  updateInfo,
  hasRush,
}: {
  deliveryInfo: DeliveryInformation;
  updateInfo: (field: string, value: string) => void;
  hasRush: boolean;
}) => {
  const [selectedProvince, setSelectedProvince] = React.useState(
    deliveryInfo.province || null
  );

  const handleProvinceChange = (value) => {
    setSelectedProvince(value);
    updateInfo("province", value.value.name);
    // console.log(value.value.name);
  };
  return (
    <div>
      <div className="mb-5">
        <label className="block text-base font-medium text-gray-700">
          Name
        </label>
        <input
          type="text"
          value={deliveryInfo.name}
          onChange={(e) => updateInfo("name", e.target.value)}
          className="mt-1 p-2 w-full border border-gray-300 rounded-md"
        />
      </div>
      <div className="mb-5">
        <label className="block text-base font-medium text-gray-700">
          Phone
        </label>
        <input
          type="text"
          value={deliveryInfo.phone}
          onChange={(e) => updateInfo("phone", e.target.value)}
          className="mt-1 p-2 w-full border border-gray-300 rounded-md"
        />
      </div>
      <div className="mb-5">
        <Select
          label="Province / City"
          value={selectedProvince}
          onChange={handleProvinceChange}
          options={provinces.map((province) => ({
            label: province.name,
            value: province,
          }))}
        />
      </div>
      <div className="mb-5">
        <label className="block text-base font-medium text-gray-700">
          Address
        </label>
        <input
          type="text"
          value={deliveryInfo.address}
          onChange={(e) => updateInfo("address", e.target.value)}
          className="mt-1 p-2 w-full border border-gray-300 rounded-md"
        />
      </div>
      <div className="mb-5">
        <label className="block text-base font-medium text-gray-700">
          Instructions
        </label>
        <input
          type="text"
          value={deliveryInfo.instructions}
          onChange={(e) => updateInfo("instructions", e.target.value)}
          className="mt-1 p-2 w-full border border-gray-300 rounded-md"
        />
      </div>
      {hasRush && (
        <div className="mb-5">
          <label className="block text-base font-medium text-gray-700">
            Time
          </label>
          <DeliveryDateTimePicker
            value={deliveryInfo.time}
            onChange={updateInfo}
          />
        </div>
      )}
    </div>
  );
};

export default DeliveryInfo;
