import React, { ReactNode } from "react"; // Import ReactNode từ react
import Header from "../common/Header";
import Footer from "../common/Footer";

interface MainLayoutProps {
  children: ReactNode; // Định nghĩa kiểu cho children
}

const MainLayout: React.FC<MainLayoutProps> = ({ children }) => {
  return (
    <div className="min-h-screen flex flex-col">
      <Header />
      <main className="flex-grow">{children}</main>
      <Footer />
    </div>
  );
};

export default MainLayout;
