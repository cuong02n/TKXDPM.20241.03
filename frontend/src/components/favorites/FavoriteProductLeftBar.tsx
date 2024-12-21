import React from "react";
import { ProductCategory, PRODUCT_CATEGORIES } from "../constants/index.ts";
import { Book, Disc, DiscAlbum } from "lucide-react";

interface LeftBarProps {
  selectedCategory: ProductCategory;
  onCategoryChange: (category: ProductCategory) => void;
}

const FavoriteProductLeftBar: React.FC<LeftBarProps> = ({
  selectedCategory,
  onCategoryChange,
}) => {
  const categories = ["All", ...PRODUCT_CATEGORIES] as ProductCategory[]; // Get all categories
  const icons = [null, DiscAlbum, Book, Disc]; // Corresponding icons for each category

  return (
    <div className="px-4 py-16 bg-gray-100 rounded-md shadow w-48">
      <ul className="space-y-2">
        {categories.map((category, index) => {
          const Icon = icons[index]; // Get the corresponding icon
          return (
            <li
              key={category}
              className={`flex items-center space-x-2 cursor-pointer p-3 rounded-lg transition duration-200 ${
                selectedCategory === category
                  ? "bg-blue-500 text-white shadow-md"
                  : "bg-gray-200 text-gray-700 hover:bg-blue-100 hover:text-blue-500"
              }`}
              onClick={() => onCategoryChange(category)}
            >
              {Icon && <Icon className="w-5 h-5" />}{" "}
              {/* Render icon dynamically */}
              <span>{category}</span>
            </li>
          );
        })}
      </ul>
    </div>
  );
};

export default FavoriteProductLeftBar;
