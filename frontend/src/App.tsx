import React from "react";
import { BrowserRouter as Router } from "react-router-dom";
import AppRoutes from "./components/routes/index.tsx";
import Header from "./components/common/Header.tsx";
import Footer from "./components/common/Footer.tsx";
import { Slide, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const App: React.FC = () => {
  return (
    <Router>
      <div className="App">
        <Header /> {/* Hiển thị Header ở tất cả các trang */}
        <AppRoutes />
        <Footer />
        <ToastContainer
          position="top-center"
          autoClose={3000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
          theme="light"
          transition={Slide}
        />
      </div>
    </Router>
  );
};

export default App;
