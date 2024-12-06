import React, { useState } from "react";

const ProductFilter = ({ onFilter }: { onFilter: (filters: any) => void }) => {
  const [category, setCategory] = useState("");
  const [priceRange, setPriceRange] = useState("");

  const handleFilter = () => {
    onFilter({ category, priceRange });
  };

  return (
    <div className="bg-gray-100 p-4 rounded">
      <h3 className="font-bold mb-2">Filter Products</h3>
      <div>
        <label>Category:</label>
        <select
          value={category}
          onChange={(e) => setCategory(e.target.value)}
          className="border p-2 w-full"
        >
          <option value="">All</option>
          <option value="cd">CD</option>
          <option value="dvd">DVD</option>
          <option value="book">Book</option>
        </select>
      </div>
      <div className="mt-2">
        <label>Price Range:</label>
        <select
          value={priceRange}
          onChange={(e) => setPriceRange(e.target.value)}
          className="border p-2 w-full"
        >
          <option value="">All</option>
          <option value="0-20">0 - 20</option>
          <option value="20-50">20 - 50</option>
        </select>
      </div>
      <button
        onClick={handleFilter}
        className="bg-blue-600 text-white px-4 py-2 mt-4 rounded"
      >
        Apply
      </button>
    </div>
  );
};

export default ProductFilter;
