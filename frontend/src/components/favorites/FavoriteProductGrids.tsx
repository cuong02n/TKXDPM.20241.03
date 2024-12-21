import React from "react";
import { ProductCategory } from "../constants";
import { Product } from "../types/product";
import { FavoriteProductCard } from "./FavoriteProductCard.tsx";
import PageSwitcher from "./FavoriteProductPageSwitcher.tsx";

interface ProductGridProps {
  products: Product[];
  filter: (category: ProductCategory) => boolean;
}

const PAGE_SIZE = 2;
const FavoriteProductGrids: React.FC<ProductGridProps> = ({
  products,
  filter,
}) => {
  const [currentPage, setCurrentPage] = React.useState(1);
  const [currentProducts, setCurrentProducts] = React.useState<Product[]>([]);
  const [totalPages, setTotalPages] = React.useState(1);

  // Refresh page every time filter change
  React.useEffect(() => {
    const filteredProducts = products.filter((product) =>
      filter(product.category)
    );
    setCurrentProducts(filteredProducts);
    setTotalPages(Math.ceil(filteredProducts.length / PAGE_SIZE));
  }, [filter, products]);

  const onNextPage = () => {
    if (currentPage < totalPages) {
      setCurrentPage(currentPage + 1);
    }
  };
  const onPreviousPage = () => {
    if (currentPage > 1) {
      setCurrentPage(currentPage - 1);
    }
  };

  return (
    <div className="flex flex-col w-full">
      <div className="flex justify-end">
        <PageSwitcher
          totalPages={totalPages}
          currentPage={currentPage}
          onNextPage={onNextPage}
          onPrevPage={onPreviousPage}
        />
      </div>
      <div className="grid grid-cols-2 gap-4 p-4">
        {currentProducts &&
          currentProducts
            .slice((currentPage - 1) * PAGE_SIZE, currentPage * PAGE_SIZE)
            .map((product) => (
              <FavoriteProductCard key={product.id} product={product} />
            ))}
      </div>
    </div>
  );
};

export default FavoriteProductGrids;
