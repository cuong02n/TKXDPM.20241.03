import React from "react";
import { DeliveryInformation } from "../types/deliveryInfo";

const InvoiceDelivery = ({ delivery }: { delivery: DeliveryInformation }) => {
  return (
    <div>
      <div className="flex">
        <p className="w-40">Name</p>
        <p>: {delivery.name}</p>
      </div>
      <div className="flex">
        <p className="w-40">Phone number</p>
        <p>: {delivery.phone}</p>
      </div>
      <div className="flex">
        <p className="w-40">Province</p>
        <p>: {delivery.province}</p>
      </div>
      <div className="flex">
        <p className="w-40">Address</p>
        <p>: {delivery.address}</p>
      </div>
      <div className="flex">
        <p className="w-40">Delivery instructions</p>
        <p>: {delivery.instructions}</p>
      </div>
      {delivery.time && (
        <div className="flex">
          <p className="w-40">Delivery time</p>
          <p>: {delivery.time}</p>
        </div>
      )}
    </div>
  );
};

export default InvoiceDelivery;
