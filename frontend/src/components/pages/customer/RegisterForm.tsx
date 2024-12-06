import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { register, verifyOtp, resetError } from "../../store/authSlice.ts";
import { AppDispatch, RootState } from "../../store/store.ts";
import { FaEye, FaEyeSlash } from "react-icons/fa"; // Import các icon mắt

const RegistrationForm = () => {
  const [confirmPassword, setConfirmPassword] = useState("");
  const [confirmPasswordError, setConfirmPasswordError] = useState<
    string | null
  >(null);
  const [showOtpPopup, setShowOtpPopup] = useState(false);
  const [otp, setOtp] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [showPassword, setShowPassword] = useState(false); // Trạng thái để hiển thị mật khẩu
  const [showConfirmPassword, setShowConfirmPassword] = useState(false); // Trạng thái để hiển thị mật khẩu xác nhận

  const dispatch: AppDispatch = useDispatch();
  const navigate = useNavigate();
  const { error, loading, otpVerified } = useSelector(
    (state: RootState) => state.auth
  );

  useEffect(() => {
    if (otpVerified) {
      navigate("/login");
    }
  }, [otpVerified, navigate]);

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();
    if (password !== confirmPassword) {
      setConfirmPasswordError("Mật khẩu xác nhận không khớp.");
      return;
    }
    setConfirmPasswordError(null);

    const registerPayload = { email, password, name };
    const resultAction = await dispatch(register(registerPayload));

    if (register.fulfilled.match(resultAction)) {
      setShowOtpPopup(true);
    }
  };

  const handleOtpSubmit = async () => {
    const verifyPayload = { email, otp };
    const resultAction = await dispatch(verifyOtp(verifyPayload));

    if (verifyOtp.fulfilled.match(resultAction)) {
      alert("Xác thực OTP thành công!");
    }
  };

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
        {/* Email */}
        <InputField
          id="email"
          label="Email *"
          type="email"
          value={email}
          placeholder="Nhập email"
          onChange={handleInputChange(setEmail)}
        />
        {/* Mật khẩu */}
        <InputField
          id="password"
          label="Mật khẩu *"
          type={showPassword ? "text" : "password"}
          value={password}
          placeholder="********"
          onChange={handleInputChange(setPassword)}
          showPassword={showPassword}
          setShowPassword={setShowPassword}
        />
        {/* Tên */}
        <InputField
          id="name"
          label="Tên *"
          type="text"
          value={name}
          placeholder="Nhập tên của bạn"
          onChange={handleInputChange(setName)}
        />
        {/* Xác nhận mật khẩu */}
        <InputField
          id="confirmPassword"
          label="Xác nhận mật khẩu *"
          type={showConfirmPassword ? "text" : "password"}
          value={confirmPassword}
          placeholder="********"
          onChange={(e) => setConfirmPassword(e.target.value)}
          showPassword={showConfirmPassword}
          setShowPassword={setShowConfirmPassword}
        />
        {confirmPasswordError && (
          <p className="text-red-500 text-sm">{confirmPasswordError}</p>
        )}
        {error && <p className="text-red-500 text-sm">{error}</p>}

        <div className="mb-5">
          <button
            type="submit"
            className="w-full h-12 bg-[#1E90FF] text-white font-semibold rounded-lg shadow-md hover:bg-[#A87758] transition duration-300"
            disabled={loading}
          >
            {loading ? "Đang xử lý..." : "Đăng ký"}
          </button>
        </div>
      </form>

      {/* Popup OTP */}
      {showOtpPopup && (
        <OtpPopup
          otp={otp}
          setOtp={setOtp}
          loading={loading}
          error={error}
          handleOtpSubmit={handleOtpSubmit}
          setShowOtpPopup={setShowOtpPopup}
        />
      )}
    </div>
  );
};

const InputField = ({
  id,
  label,
  type,
  value,
  placeholder,
  onChange,
  showPassword,
  setShowPassword,
}: {
  id: string;
  label: string;
  type: string;
  value: string;
  placeholder: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  showPassword?: boolean;
  setShowPassword?: React.Dispatch<React.SetStateAction<boolean>>;
}) => (
  <div className="mb-5">
    <label htmlFor={id} className="block mb-2">
      {label}
    </label>
    <div className="relative">
      <input
        type={type}
        id={id}
        name={id}
        value={value}
        placeholder={placeholder}
        required
        onChange={onChange}
        className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:border-gray-500 transition-all"
      />
      {setShowPassword && (
        <button
          type="button"
          onClick={() => setShowPassword(!showPassword)}
          className="absolute inset-y-0 right-3 flex items-center"
        >
          {showPassword ? (
            <FaEyeSlash className="text-gray-500" />
          ) : (
            <FaEye className="text-gray-500" />
          )}
        </button>
      )}
    </div>
  </div>
);

const OtpPopup = ({
  otp,
  setOtp,
  loading,
  error,
  handleOtpSubmit,
  setShowOtpPopup,
}: {
  otp: string;
  setOtp: React.Dispatch<React.SetStateAction<string>>;
  loading: boolean;
  error: string | null;
  handleOtpSubmit: () => Promise<void>;
  setShowOtpPopup: React.Dispatch<React.SetStateAction<boolean>>;
}) => (
  <div className="fixed inset-0 bg-black bg-opacity-50 z-50 flex justify-center items-center">
    <div className="bg-white p-6 rounded-lg shadow-xl text-center max-w-md w-full relative">
      <button
        onClick={() => setShowOtpPopup(false)}
        className="absolute top-2 right-2 text-xl text-gray-600 hover:text-gray-900"
      >
        &times;
      </button>
      <h2 className="text-xl font-semibold mb-4">Nhập mã OTP</h2>
      <p className="text-gray-700 mb-4">
        Vui lòng kiểm tra email để nhận mã OTP và nhập vào ô bên dưới.
      </p>
      <input
        type="text"
        value={otp}
        onChange={(e) => setOtp(e.target.value)}
        placeholder="Nhập mã OTP"
        className="w-full p-3 border border-gray-300 rounded-md focus:outline-none focus:border-gray-500 transition-all mb-4"
      />
      {error && <p className="text-red-500 text-sm mb-4">{error}</p>}
      <button
        onClick={handleOtpSubmit}
        className="w-full h-12 bg-[#4CAF50] text-white font-semibold rounded-lg shadow-md hover:bg-[#45A049] transition duration-300"
        disabled={loading}
      >
        {loading ? "Đang xử lý..." : "Xác nhận"}
      </button>
    </div>
  </div>
);

export default RegistrationForm;
