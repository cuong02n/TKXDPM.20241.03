import React, { useEffect } from "react";
import ProductGrid from "../../product/ProductGrid.tsx";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../store/store.ts";
import { fetchProducts } from "../../store/thunk/productThunk.ts";
import { fetchFavorites } from "../../store/thunk/favoritesThunk.ts";

const Home = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { items, loading, error } = useSelector(
    (state: RootState) => state.product,
  );
  const favorites = useSelector((state: RootState) => state.wishlist);
  const products = items;
  const [searchQuery, setSearchQuery] = React.useState("");
  const [filteredProducts, setFilteredProducts] = React.useState(items);
  useEffect(() => {
    dispatch(fetchProducts());
    dispatch(fetchFavorites());
  }, [dispatch]);
  useEffect(() => {
    setFilteredProducts(
      items.filter((product) =>
        product.name.toLowerCase().includes(searchQuery.toLowerCase()),
      ),
    );
  }, [items, searchQuery]);

  const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchQuery(e.target.value);
  };
  return (
    <div className="container mx-auto py-10 min-h-screen">
      <h1 className="text-3xl font-bold mb-5">Welcome to the Store</h1>
      <input
        type="text"
        value={searchQuery}
        onChange={handleSearchChange}
        placeholder="Search products..."
        className="mb-5 p-2 border border-gray-300 rounded-md w-full"
      />
      <ProductGrid
        products={filteredProducts}
        favoriteProducts={favorites.items}
      />
    </div>
  );
};

export default Home;
