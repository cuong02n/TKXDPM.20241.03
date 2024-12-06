import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../store/store";
import { Product } from "../../types/product";
import { addToCart } from "../../store/cartSlice.ts";
import { CartItem } from "../../types/cart";

const ProductPage = () => {
  const dispatch = useDispatch();
  const products = useSelector((state: RootState) => state.product.items);

  const handleAddToCart = (product: Product) => {
    const cartItem: CartItem = {
      ...product, // Spread các thuộc tính của product vào
      quantity: 1, // Thêm trường quantity mặc định là 1
    };
    dispatch(addToCart(cartItem)); // Gửi CartItem vào store
  };

  return (
    <div className="product-page">
      <h2>Our Products</h2>
      <div className="product-list">
        {products.map((product) => (
          <div key={product.id} className="product-item">
            <img src={product.imageUrl} alt={product.name} />
            <h3>{product.name}</h3>
            <p>{product.description}</p>
            <span>{product.price} USD</span>
            <div>Category: {product.category}</div>
            <button onClick={() => handleAddToCart(product)}>
              Add to Cart
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProductPage;
