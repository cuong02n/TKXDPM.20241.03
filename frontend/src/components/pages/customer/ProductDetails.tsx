import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import { LoadingPage } from "../../product/LoadingPage.tsx";
import { ProductDetailCard } from "../../product/ProductDetailCard.tsx";
import { addProduct } from "../../store/productSlice.ts";
import { AppDispatch, RootState } from "../../store/store";
import { getProductWithId } from "../../store/thunk/productThunk.ts";
import { Product } from "../../types";
import { DetailedProduct } from "../../types/productDetails";

const ProductDetail = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { id } = useParams();
  const [isLoading, setIsLoading] = React.useState(true);
  const [isError, setIsError] = React.useState(false);
  const [foundProduct, setFoundProduct] =
    React.useState<DetailedProduct | null>(null);

  React.useEffect(() => {
    if (!foundProduct) {
      dispatch(getProductWithId(id))
        .unwrap()
        .then((data) => {
          console.log(data);
          setFoundProduct(data);
          setIsError(false);
          setIsLoading(false);
        })
        .catch((err) => {
          setIsError(true);
          setIsLoading(false);
        });
    } else {
      setIsLoading(false);
      setIsError(false);
    }
  }, [dispatch, id, foundProduct]);

  return !isLoading ? (
    !isError ? (
      <div className="container mx-auto py-10">
        <ProductDetailCard product={foundProduct} />
      </div>
    ) : (
      <div className="flex flex-col items-center justify-center h-[360px]">
        <h2 className="text-2xl font-bold text-gray-600">Sorry!</h2>
        <p className="text-gray-500">Couldn't get the product info</p>
        <button
          onClick={() => {}}
          className="bg-blue-600 text-white px-4 py-2 mt-4 rounded-lg"
          name="Reload"
        >
          Reload
        </button>
      </div>
    )
  ) : (
    <LoadingPage />
  );
};

export default ProductDetail;
