import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../store/store";
import { Product } from "../../types/product";
import FavoriteProjectLeftBar from "../../favorites/FavoriteProductLeftBar.tsx";
import FavoriteProductGrids from "../../favorites/FavoriteProductGrids.tsx";
import { fetchFavorites } from "../../store/thunk/favoritesThunk.ts";

const sampleProducts: Product[] = [
  {
    id: "1",
    name: "Book 1",
    price: 120000,
    imageUrl: "",
    quantity: 10,
    description: "temp",
    category: "book",
  },
  {
    id: "2",
    name: "CD 2",
    price: 45000,
    imageUrl: "",
    quantity: 10,
    description: "temp",
    category: "CD",
  },
  {
    id: "3",
    name: "DVD 3",
    price: 98000,
    imageUrl: "",
    quantity: 10,
    description: "temp",
    category: "DVD",
  },
];

const FavoriteProductPage = () => {
  const dispatch = useDispatch<AppDispatch>();
  const products = useSelector((state: RootState) => state.wishlist.items);

  const [selectedCategory, setSelectedCategory] = React.useState("All");
  React.useEffect(() => {
    dispatch(fetchFavorites());
  }, [dispatch]);

  return (
    <div className="flex flex-row min-h-screen">
      <FavoriteProjectLeftBar
        selectedCategory={selectedCategory}
        onCategoryChange={setSelectedCategory}
      />
      <div className="flex flex-col w-full">
        <FavoriteProductGrids
          products={products}
          filter={(category) =>
            selectedCategory === "All" || selectedCategory === category
          }
        />
      </div>
    </div>
  );
};

export default FavoriteProductPage;
