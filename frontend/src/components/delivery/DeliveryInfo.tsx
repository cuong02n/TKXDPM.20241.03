import React from "react";
import { DeliveryInformation } from "../types/deliveryInfo.ts";
import Select from "./Select.tsx";
import { provinces } from "../constants/provinces.ts";
import DeliveryDateTimePicker from "./DateTimePicker.tsx";
import { toast } from "react-toastify";

const DeliveryInfo = ({
  deliveryInfo,
  updateInfo,
  hasRush,
  checkInfo,
}: {
  deliveryInfo: DeliveryInformation;
  updateInfo: (field: string, value: string) => void;
  hasRush: boolean;
  checkInfo: React.MutableRefObject<() => boolean>;
}) => {
  const [selectedProvince, setSelectedProvince] = React.useState(
    deliveryInfo.province
      ? { label: deliveryInfo.province, value: deliveryInfo.province }
      : null
  );

  const [isValid, setIsValid] = React.useState({
    name: true,
    phone: true,
    province: true,
    address: true,
  });
  React.useEffect(() => {
    if (hasRush) {
      updateInfo("province", "Hà Nội");
      setSelectedProvince({ label: "Hà Nội", value: "Hà Nội" });
    }
  }, []);

  React.useEffect(() => {
    if (deliveryInfo.province === "") setSelectedProvince(null);
  }, [deliveryInfo]);

  React.useEffect(() => {
    checkInfo.current = checkInfoValid;
  }, [deliveryInfo]);

  const checkInfoValid = () => {
    const { name, phone, province, address } = deliveryInfo;
    // Name validation: only letters, max 30 chars
    const validateName = (name) => {
      const nameStr = name.trim();
      if (nameStr.length === 0) {
        toast.error("Name is required");
        return false;
      }
      if (nameStr.length > 30) {
        toast.error("Name must not exceed 30 characters");
        return false;
      }
      if (!/^[a-zA-Z]+$/.test(nameStr)) {
        toast.error("Name can only contain letters (a-z, A-Z)");
        return false;
      }
      return true;
    };

    // Phone validation: starts with 0, 10 digits, optional consistent separator
    const validatePhone = (phone) => {
      const phoneStr = phone.trim();
      if (phoneStr.length === 0) {
        toast.error("Phone number is required");
        return false;
      }

      // Check if there are any separators
      const hasDot = phoneStr.includes(".");
      const hasHyphen = phoneStr.includes("-");
      const hasSlash = phoneStr.includes("/");

      // Ensure only one type of separator is used
      const separatorCount = [hasDot, hasHyphen, hasSlash].filter(
        Boolean
      ).length;
      if (separatorCount > 1) {
        toast.error(
          "Please use only one type of separator (. or - or /) in phone number"
        );
        return false;
      }

      // Remove separator if exists
      const digitsOnly = phoneStr.replace(/[.\-\/]/g, "");

      if (!digitsOnly.startsWith("0")) {
        toast.error("Phone number must start with 0");
        return false;
      }

      if (digitsOnly.length !== 10) {
        toast.error("Phone number must have exactly 10 digits");
        return false;
      }

      // If there's a separator, ensure it's properly interleaved with digits
      if (separatorCount === 1) {
        const separator = hasDot ? "." : hasHyphen ? "-" : "/";
        const parts = phoneStr.split(separator);
        // Check if all parts contain only digits
        if (!parts.every((part) => /^\d+$/.test(part))) {
          toast.error(
            `Invalid phone format. Example: 012${separator}345${separator}6789`
          );
          return false;
        }
      } else if (!/^\d{10}$/.test(digitsOnly)) {
        toast.error(
          "Phone number can only contain digits and optional separators"
        );
        return false;
      }

      return true;
    };

    // Address validation: letters, digits, slashes, max 100 chars
    const validateAddress = (address) => {
      const addressStr = address.trim();
      if (addressStr.length === 0) {
        toast.error("Address is required");
        return false;
      }
      if (addressStr.length > 100) {
        toast.error("Address must not exceed 100 characters");
        return false;
      }
      if (!/^[a-zA-Z0-9\/ ]+$/.test(addressStr)) {
        toast.error("Address can only contain letters, numbers, and slashes");
        return false;
      }
      return true;
    };

    // Province validation
    const validateProvince = (province) => {
      if (province.trim().length === 0) {
        toast.error("Province is required");
        return false;
      }
      return true;
    };

    const validName = validateName(name);
    const validPhone = validatePhone(phone);
    const validProvince = validateProvince(province);
    const validAddress = validateAddress(address);

    setIsValid({
      name: validName,
      phone: validPhone,
      province: validProvince,
      address: validAddress,
    });
    return validName && validPhone && validProvince && validAddress;
  };

  const handleProvinceChange = (value) => {
    setSelectedProvince(value);
    updateInfo("province", value.value.name);
    if (hasRush && value.value.name !== "Hà Nội") {
      toast.error("Rush order is only available in Hà Nội!");
      updateInfo("province", "");
    }
    // console.log(value.value.name);
  };
  return (
    <div>
      <div className="mb-5">
        <label className="block text-base font-medium text-gray-700">
          Name <span className="text-rose-500">*</span>
        </label>
        <input
          type="text"
          value={deliveryInfo.name}
          onChange={(e) => updateInfo("name", e.target.value)}
          className={`mt-1 p-2 w-full border ${
            isValid.name ? "border-gray-300" : "border-red-500"
          } rounded-md`}
        />
        {!isValid.name && (
          <p className="text-red-500 text-sm">Name is required.</p>
        )}
      </div>
      <div className="mb-5">
        <label className="block text-base font-medium text-gray-700">
          Phone <span className="text-rose-500">*</span>
        </label>
        <input
          type="text"
          value={deliveryInfo.phone}
          onChange={(e) => updateInfo("phone", e.target.value)}
          className={`mt-1 p-2 w-full border ${
            isValid.phone ? "border-gray-300" : "border-red-500"
          } rounded-md`}
        />
        {!isValid.phone && (
          <p className="text-red-500 text-sm">Phone is required.</p>
        )}
      </div>
      <div className="mb-5">
        <Select
          label="Province / City"
          value={selectedProvince}
          onChange={handleProvinceChange}
          isValid={isValid.province}
          options={provinces.map((province) => ({
            label: province.name,
            value: province,
          }))}
        />
        {!isValid.province && (
          <p className="text-red-500 text-sm">Province is required.</p>
        )}
      </div>
      <div className="mb-5">
        <label className="block text-base font-medium text-gray-700">
          Address <span className="text-rose-500">*</span>
        </label>
        <input
          type="text"
          value={deliveryInfo.address}
          onChange={(e) => updateInfo("address", e.target.value)}
          className={`mt-1 p-2 w-full border ${
            isValid.address ? "border-gray-300" : "border-red-500"
          } rounded-md`}
        />
        {!isValid.address && (
          <p className="text-red-500 text-sm">Address is required.</p>
        )}
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
