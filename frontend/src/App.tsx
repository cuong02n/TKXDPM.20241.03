import React from "react";
import { BrowserRouter as Router } from "react-router-dom";
import AppRoutes from "./components/routes/index.tsx";
import Header from "./components/common/Header.tsx";
import Footer from "./components/common/Footer.tsx";
<<<<<<< HEAD
import { Slide, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
=======
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69

const App: React.FC = () => {
  return (
    <Router>
      <div className="App">
        <Header /> {/* Hiển thị Header ở tất cả các trang */}
        <AppRoutes />
        <Footer />
<<<<<<< HEAD
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
=======
>>>>>>> 7b1f830e94e6bed2b34477e258ca194014d22c69
      </div>
    </Router>
  );
};

export default App;
