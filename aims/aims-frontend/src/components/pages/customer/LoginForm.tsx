import React, { useState } from "react";

const LoginForm = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();
    setIsLoading(true);

    // Mô phỏng xử lý đăng nhập
    setTimeout(() => {
      if (email === "user@example.com" && password === "password123") {
        alert("Đăng nhập thành công!");
        setError("");
      } else {
        setError("Email hoặc mật khẩu không đúng.");
      }
      setIsLoading(false);
    }, 1000);
  };

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100">
      <div className="w-full max-w-md bg-white shadow-md rounded-lg p-6">
        <h2 className="text-2xl font-bold text-center mb-6">Đăng nhập</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label
              htmlFor="email"
              className="block text-sm font-medium text-gray-700"
            >
              Email
            </label>
            <input
              type="email"
              id="email"
              name="email"
              placeholder="Nhập email"
              required
              value={email}
              onChange={(event) => setEmail(event.target.value)}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
            />
          </div>
          <div>
            <label
              htmlFor="password"
              className="block text-sm font-medium text-gray-700"
            >
              Mật khẩu
            </label>
            <input
              type="password"
              id="password"
              name="password"
              placeholder="**********"
              required
              value={password}
              onChange={(event) => setPassword(event.target.value)}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500 sm:text-sm"
            />
          </div>
          {error && <p className="text-sm text-red-500">{error}</p>}
          <div className="flex justify-between items-center">
            <label className="flex items-center">
              <input
                type="checkbox"
                className="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded"
              />
              <span className="ml-2 text-sm text-gray-600">Nhớ tài khoản</span>
            </label>
            <a href="#" className="text-sm text-blue-600 hover:underline">
              Quên mật khẩu?
            </a>
          </div>
          <div>
            <button
              type="submit"
              disabled={isLoading}
              className="w-full py-2 px-4 bg-blue-600 text-white font-semibold rounded-md shadow hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
            >
              {isLoading ? "Đang xử lý..." : "Đăng nhập"}
            </button>
          </div>
        </form>
        <div className="mt-4 text-center">
          <p className="text-sm text-gray-600">
            Bạn chưa có tài khoản?{" "}
            <a href="/register" className="text-blue-600 hover:underline">
              Đăng ký
            </a>
          </p>
        </div>
        <div className="mt-4 text-center text-xs text-gray-500">
          Việc tiếp tục sử dụng trang web này đồng nghĩa bạn đồng ý với điều
          khoản sử dụng của chúng tôi.
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
