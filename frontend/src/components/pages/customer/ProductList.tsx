import React from "react";
import { useDispatch } from "react-redux";
import { addToWishlist } from "../../store/wishlistSlice";
import { Product } from "../../types/product"; // Import kiểu dữ liệu Product

// Danh sách sản phẩm mẫu
const products: Product[] = [
  {
    id: "1",
    name: "Book",
    description: "A great book about programming.",
    price: 10,
    category: "book",
    imageUrl: "https://img.lovepik.com/element/40048/7442.png_860.png", // URL ảnh mẫu
    quantity: 10,
  },
  {
    id: "2",
    name: "CD",
    description: "Music album of the year.",
    price: 15,
    category: "cd",
    imageUrl: "https://img.lovepik.com/element/40048/7442.png_860.png",
    quantity: 10,
  },
  {
    id: "3",
    name: "DVD",
    description: "Top-rated movie collection.",
    price: 20,
    category: "dvd",
    imageUrl: "https://img.lovepik.com/element/40048/7442.png_860.png",
    quantity: 10,
  },
];

const ProductList: React.FC = () => {
  const dispatch = useDispatch();

  const handleAddToWishlist = (product: Product) => {
    dispatch(addToWishlist(product)); // Gửi sản phẩm lên wishlist
  };

  return (
    <div className="product-list">
      <h2>Products</h2>
      <div className="product-grid">
        {products.map((product) => (
          <div key={product.id} className="product-item">
            {/* Hiển thị hình ảnh sản phẩm */}
            <img
              src={product.imageUrl}
              alt={product.name}
              className="product-image"
            />

            {/* Hiển thị thông tin sản phẩm */}
            <h3>{product.name}</h3>
            <p className="product-description">{product.description}</p>
            <p className="product-category">Category: {product.category}</p>
            <div className="product-price">{product.price} USD</div>

            {/* Nút thêm vào Wishlist */}
            <button onClick={() => handleAddToWishlist(product)}>
              Add to Wishlist
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProductList;
