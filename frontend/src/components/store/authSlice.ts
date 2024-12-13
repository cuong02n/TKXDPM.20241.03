import { createSlice, createAsyncThunk, PayloadAction } from "@reduxjs/toolkit";
import axios from "axios";
import apiClient from "../../api/apiClient.ts";

// Giao diện trạng thái xác thực
interface AuthState {
  user: { id: string; name: string } | null;
  error: string | null;
  loading: boolean;
  otpVerified: boolean;
  jwt: string | null;
}

const initialState: AuthState = {
  user: null, // Bạn có thể lưu thông tin người dùng ở đây nếu cần
  error: null,
  loading: false,
  otpVerified: false,
  jwt: localStorage.getItem("jwt") || null, // Lấy JWT từ localStorage
};

// Các endpoint API
const API_BASE_URL = "http://116.96.98.153:8080/api/auth";

// Payload đăng ký
interface RegisterPayload {
  email: string;
  password: string;
  name: string;
}

// Payload OTP
interface OtpPayload {
  email: string;
  otp: string;
}

// Payload đăng nhập
interface LoginPayload {
  email: string;
  password: string;
}

// Thunk: Đăng ký
export const register = createAsyncThunk(
  "auth/register",
  async (data: RegisterPayload, { rejectWithValue }) => {
    try {
      // const response = await axios.post(`${API_BASE_URL}/register`, data);
      const response = await apiClient.post("/auth/register", data);
      return response.data; // Trả về dữ liệu nếu thành công
    } catch (error: any) {
      return rejectWithValue(
        error.response?.data?.message || "Đăng ký thất bại"
      );
    }
  }
);

// Thunk: Xác thực OTP
export const verifyOtp = createAsyncThunk(
  "auth/verifyOtp",
  async (data: OtpPayload, { rejectWithValue }) => {
    try {
      const response = await axios.post(
        `${API_BASE_URL}/verify-register`,
        null,
        {
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          params: data, // Sử dụng x-www-form-urlencoded
        }
      );
      return response.data;
    } catch (error: any) {
      return rejectWithValue(
        error.response?.data?.message || "Xác thực OTP thất bại"
      );
    }
  }
);

// Thunk: Đăng nhập
export const login = createAsyncThunk(
  "auth/login",
  async (data: LoginPayload, { rejectWithValue }) => {
    try {
      const response = await axios.post(`${API_BASE_URL}/login`, data);
      const { jwt, role, expired } = response.data.data;

      // Lưu token JWT vào localStorage cùng với role và expiration time
      localStorage.setItem("jwt", jwt);
      localStorage.setItem("role", role);
      localStorage.setItem("expired", expired.toString());

      return response.data.data; // Trả về data chứa thông tin jwt, role, expired
    } catch (error: any) {
      return rejectWithValue(
        error.response?.data?.message || "Đăng nhập thất bại"
      );
    }
  }
);

// Slice
const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    logout(state) {
      state.user = null;
      state.otpVerified = false;
      state.jwt = null; // Xóa JWT khi đăng xuất
      localStorage.removeItem("jwt");
      localStorage.removeItem("role");
      localStorage.removeItem("expired");
    },
    resetError(state) {
      state.error = null;
    },
  },
  extraReducers: (builder) => {
    // Xử lý đăng ký
    builder.addCase(register.pending, (state) => {
      state.loading = true;
      state.error = null;
    });
    builder.addCase(register.fulfilled, (state) => {
      state.loading = false;
    });
    builder.addCase(register.rejected, (state, action: any) => {
      state.loading = false;
      state.error = action.payload;
    });

    // Xử lý xác thực OTP
    builder.addCase(verifyOtp.pending, (state) => {
      state.loading = true;
      state.error = null;
    });
    builder.addCase(verifyOtp.fulfilled, (state) => {
      state.loading = false;
      state.otpVerified = true; // OTP được xác thực
    });
    builder.addCase(verifyOtp.rejected, (state, action: any) => {
      state.loading = false;
      state.error = action.payload;
    });

    // Xử lý đăng nhập
    builder.addCase(login.pending, (state) => {
      state.loading = true;
      state.error = null;
    });
    builder.addCase(login.fulfilled, (state, action: PayloadAction<any>) => {
      state.loading = false;
      state.user = action.payload; // Bạn có thể lưu thông tin người dùng vào state
      state.jwt = localStorage.getItem("jwt"); // Cập nhật JWT từ localStorage
    });
    builder.addCase(login.rejected, (state, action: any) => {
      state.loading = false;
      state.error = action.payload;
    });
  },
});

// Export các actions và reducer
export const { logout, resetError } = authSlice.actions;
export default authSlice.reducer;
