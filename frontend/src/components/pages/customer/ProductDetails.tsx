import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import { LoadingPage } from "../../product/LoadingPage";
import { ProductDetailCard } from "../../product/ProductDetailCard";
import { addProduct, getProductWithId } from "../../store/productSlice";
import { AppDispatch, RootState } from "../../store/store";
import { Product } from "../../types";

const ProductDetail = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { id } = useParams();
  const products: Product[] = useSelector(
    (state: RootState) => state.product.items
  );
  const [isLoading, setIsLoading] = React.useState(true);
  const [isError, setIsError] = React.useState(false);

  React.useEffect(() => {
    dispatch(getProductWithId(Number.parseInt(id)))
      .unwrap()
      .then((data) => {
        dispatch(addProduct(data));
        setIsLoading(false);
        setIsError(false);
      })
      .catch((err) => {
        setIsError(true);
        setIsLoading(false);
      });
  }, [dispatch, isError, id]);

  return !isLoading ? (
    !isError ? (
      <div className="container mx-auto py-10">
        <ProductDetailCard product={products[0]} />
      </div>
    ) : (
      <div className="flex flex-col items-center justify-center h-[360px]">
        <h2 className="text-2xl font-bold text-gray-600">Sorry!</h2>
        <p className="text-gray-500">Couldn't get the product info</p>
        <button
          onClick={() => {
            setIsLoading(true);
            setIsError(false);
          }}
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
