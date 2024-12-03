// components/layout/Header.tsx
import React from "react";
import { Link } from "react-router-dom"; // Import Link để tạo các liên kết

const Header = () => {
  return (
    <header className="bg-blue-500 text-white p-4">
      <div className="container mx-auto flex justify-between items-center">
        <h1 className="text-2xl font-bold">AIMS Store</h1>
        <nav>
          <ul className="flex space-x-4">
            <li>
              <Link to="/" className="text-white hover:text-gray-200">
                Home
              </Link>
            </li>
            <li>
              <Link to="/products" className="text-white hover:text-gray-200">
                Products
              </Link>
            </li>
            <li>
              <Link to="/cart" className="text-white hover:text-gray-200">
                Cart
              </Link>
            </li>
            <li>
              <Link to="/login" className="text-white hover:text-gray-200">
                Login
              </Link>
            </li>
            <li>
              <Link to="/register" className="text-white hover:text-gray-200">
                Register
              </Link>
            </li>
          </ul>
        </nav>
      </div>
    </header>
  );
};

export default Header;
