import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { registerRequest, resetError } from "../../store/authSlice.ts";

const images = {
  tick: require("../../../assets/images/404-tick.png"),
};

const RegistrationForm = () => {
  const [confirmPasswordError, setConfirmPasswordError] = useState<
    string | null
  >(null);
  const [timer, setTimer] = useState<NodeJS.Timeout | null>(null);
  const [showPopup, setShowPopup] = useState(false);

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [lastName, setLastName] = useState("");
  const [province, setProvince] = useState("");
  const [city, setCity] = useState("");
  const [ward, setWard] = useState("");
  const [phone, setPhone] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");

  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { error, user } = useSelector((state: any) => state.auth);

  useEffect(() => {
    if (timer) {
      clearTimeout(timer);
    }

    setTimer(
      setTimeout(() => {
        if (confirmPassword && password !== confirmPassword) {
          setConfirmPasswordError("Mật khẩu xác nhận không khớp.");
        } else {
          setConfirmPasswordError(null);
        }
      }, 800)
    );

    return () => {
      if (timer) {
        clearTimeout(timer);
      }
    };
  }, [confirmPassword, password]);

  useEffect(() => {
    if (showPopup) {
      const timeout = setTimeout(() => {
        navigate("/login");
      }, 5000);

      return () => clearTimeout(timeout);
    }
  }, [showPopup, navigate]);

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();
    if (password !== confirmPassword) {
      setConfirmPasswordError("Mật khẩu xác nhận không khớp.");
      return;
    }

    setConfirmPasswordError(null);

    // Xử lý đăng ký
    dispatch(
      registerRequest({
        email,
        password,
        name,
        address: `${province}, ${city}, ${ward}`,
        phoneNumber: phone,
      })
    );
    setShowPopup(true);
  };

  useEffect(() => {
    if (user) {
      navigate("/");
    }
  }, [user, navigate]);

  const handleInputChange =
    (setter: React.Dispatch<React.SetStateAction<string>>) =>
    (event: React.ChangeEvent<HTMLInputElement>) => {
      setter(event.target.value);
      if (error) dispatch(resetError());
    };

  return (
    <div className="max-w-2xl mx-auto p-5 border border-gray-300 rounded-lg shadow-lg">
      <h2 className="text-center text-2xl mb-6 text-[#C69774]">Đăng ký</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-5">
          <label htmlFor="email" className="block mb-2">
            Email *
          </label>
          <input
            type="email"
            id="email"
            name="email"
            placeholder="Nhập email"
            required
            value={email}
            onChange={handleInputChange(setEmail)}
            className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:border-gray-500 transition-all"
          />
        </div>
        <div className="mb-5">
          <label htmlFor="password" className="block mb-2">
            Mật khẩu *
          </label>
          <input
            type="password"
            id="password"
            name="password"
            placeholder="********"
            required
            value={password}
            onChange={handleInputChange(setPassword)}
            className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:border-gray-500 transition-all"
          />
        </div>
        <div className="mb-5">
          <label htmlFor="confirmPassword" className="block mb-2">
            Xác nhận mật khẩu *
          </label>
          <input
            type="password"
            id="confirmPassword"
            name="confirmPassword"
            placeholder="********"
            required
            value={confirmPassword}
            onChange={handleInputChange(setConfirmPassword)}
            className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:border-gray-500 transition-all"
          />
        </div>
        {confirmPasswordError && (
          <p className="text-red-500 text-sm">{confirmPasswordError}</p>
        )}
        <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 mt-5">
          <div className="mb-5">
            <label htmlFor="lastName" className="block mb-2">
              Họ *
            </label>
            <input
              type="text"
              id="lastName"
              name="lastName"
              placeholder=""
              required
              value={lastName}
              onChange={handleInputChange(setLastName)}
              className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:border-gray-500 transition-all"
            />
          </div>
          <div className="mb-5">
            <label htmlFor="firstName" className="block mb-2">
              Tên *
            </label>
            <input
              type="text"
              id="firstName"
              name="firstName"
              placeholder=""
              required
              value={name}
              onChange={handleInputChange(setName)}
              className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:border-gray-500 transition-all"
            />
          </div>
          <div className="mb-5">
            <label htmlFor="city" className="block mb-2">
              Tỉnh/Thành phố *
            </label>
            <input
              type="text"
              id="city"
              name="city"
              placeholder=""
              required
              value={province}
              onChange={handleInputChange(setProvince)}
              className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:border-gray-500 transition-all"
            />
          </div>
          <div className="mb-5">
            <label htmlFor="district" className="block mb-2">
              Quận/Huyện *
            </label>
            <input
              type="text"
              id="district"
              name="district"
              placeholder=""
              required
              value={city}
              onChange={handleInputChange(setCity)}
              className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:border-gray-500 transition-all"
            />
          </div>
          <div className="mb-5">
            <label htmlFor="ward" className="block mb-2">
              Phường/Xã *
            </label>
            <input
              type="text"
              id="ward"
              name="ward"
              placeholder=""
              required
              value={ward}
              onChange={handleInputChange(setWard)}
              className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:border-gray-500 transition-all"
            />
          </div>
          <div className="mb-5">
            <label htmlFor="phoneNumber" className="block mb-2">
              Số điện thoại *
            </label>
            <input
              type="number"
              id="phoneNumber"
              name="phoneNumber"
              placeholder=""
              required
              value={phone}
              onChange={handleInputChange(setPhone)}
              className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:border-gray-500 transition-all"
            />
          </div>
        </div>
        {error && <p className="text-red-500 text-sm">{error.message}</p>}
        <div className="mb-5">
          <button
            type="submit"
            className="w-full h-12 bg-[#1E90FF] text-white font-semibold rounded-lg shadow-md hover:bg-[#A87758] transition duration-300"
          >
            Đăng ký
          </button>
        </div>
      </form>

      <div className="text-center text-sm text-gray-500 mt-5">
        <p>
          Việc tiếp tục sử dụng trang web này đồng nghĩa bạn đồng ý với điều
          khoản sử dụng của chúng tôi.
        </p>
      </div>

      {showPopup && (
        <div className="fixed inset-0 bg-black bg-opacity-50 z-50">
          <div className="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 bg-white p-6 rounded-lg shadow-xl text-center max-w-md w-full">
            <div className="flex flex-col items-center">
              <img src={images.tick} alt="Success" className="w-16 h-16 mb-4" />
              <h2 className="text-2xl font-semibold text-[#4CAF50] mb-4">
                Cảm ơn bạn!
              </h2>
              <p className="text-lg text-gray-700">
                Bạn vừa yêu cầu đăng ký tài khoản. Vui lòng đăng nhập sau 5
                giây...
              </p>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default RegistrationForm;
