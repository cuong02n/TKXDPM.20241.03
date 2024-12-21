// components/layout/Header.tsx
import React from "react";
import { Link } from "react-router-dom"; // Import Link để tạo các liên kết
import { useSelector } from "react-redux";
import { RootState } from "../store/store.ts"; // Import RootState của bạn từ Redux store

const Header = () => {
  // Lấy thông tin người dùng từ Redux store
  const user = useSelector((state: RootState) => state.auth.user);

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
              <Link to="/favorite" className="text-white hover:text-gray-200">
                Favorites
              </Link>
            </li>
            <li>
              <Link to="/cart" className="text-white hover:text-gray-200">
                Cart
              </Link>
            </li>

            {/* Nếu người dùng đã đăng nhập, hiển thị avatar và tên */}
            {user ? (
              <li className="flex items-center">
                <img
                  src={
                    "https://png.pngtree.com/png-clipart/20200701/original/pngtree-black-default-avatar-png-image_5407174.jpg"
                  } // Avatar mặc định nếu không có avatar
                  alt="User Avatar"
                  className="w-8 h-8 rounded-full mr-2"
                />
                <span>{user.name}</span>
              </li>
            ) : (
              // Nếu chưa đăng nhập, hiển thị nút đăng nhập và đăng ký
              <>
                <li>
                  <Link to="/login" className="text-white hover:text-gray-200">
                    Login
                  </Link>
                </li>
                <li>
                  <Link
                    to="/register"
                    className="text-white hover:text-gray-200"
                  >
                    Register
                  </Link>
                </li>
              </>
            )}
          </ul>
        </nav>
      </div>
    </header>
  );
};

export default Header;
