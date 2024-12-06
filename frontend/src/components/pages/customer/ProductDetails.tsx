import React from "react";
import { useParams } from "react-router-dom";

const ProductDetail = () => {
  const { id } = useParams();

  return (
    <div className="container mx-auto py-10">
      <h1 className="text-2xl font-bold">Product Detail - {id}</h1>
      <p>Product description goes here...</p>
    </div>
  );
};

export default ProductDetail;
