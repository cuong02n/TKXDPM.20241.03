import React from "react";
import { BrowserRouter as Router } from "react-router-dom";
import AppRoutes from "./components/routes/index.tsx";
import Header from "./components/common/Header.tsx";
import Footer from "./components/common/Footer.tsx";

const App: React.FC = () => {
  return (
    <Router>
      <div className="App">
        <Header /> {/* Hiển thị Header ở tất cả các trang */}
        <AppRoutes />
        <Footer />
      </div>
    </Router>
  );
};

export default App;
