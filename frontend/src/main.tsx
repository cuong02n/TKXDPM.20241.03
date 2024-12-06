// src/main.tsx
import React from "react";
import ReactDOM from "react-dom/client";
import { Provider } from "react-redux";
import App from "./App";
import store from "../src/components/store/store"; // Đảm bảo rằng store của bạn được export từ src/redux/store.ts

const rootElement = document.getElementById("root") as HTMLElement; // Lấy phần tử root trong index.html

const root = ReactDOM.createRoot(rootElement);

root.render(
  <Provider store={store}>
    {" "}
    {/* Bao bọc toàn bộ ứng dụng trong Provider của Redux */}
    <App /> {/* Đây là component chính của ứng dụng */}
  </Provider>
);
