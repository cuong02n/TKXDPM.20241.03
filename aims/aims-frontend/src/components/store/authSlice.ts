// src/redux/auth/authSlice.ts
import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface AuthState {
  user: { id: string; name: string } | null;
  error: string | null;
  loading: boolean;
}

const initialState: AuthState = {
  user: null,
  error: null,
  loading: false,
};

interface RegisterPayload {
  email: string;
  password: string;
  name: string;
  address: string;
  phoneNumber: string;
}

const authSlice = createSlice({
  name: "auth",
  initialState,
  reducers: {
    loginRequest(state) {
      state.loading = true;
      state.error = null;
    },
    loginSuccess(state, action: PayloadAction<{ id: string; name: string }>) {
      state.loading = false;
      state.user = action.payload;
    },
    loginFailure(state, action: PayloadAction<string>) {
      state.loading = false;
      state.error = action.payload;
    },
    logout(state) {
      state.user = null;
    },
    // Action đăng ký
    registerRequest(state, action: PayloadAction<RegisterPayload>) {
      state.loading = true;
      state.error = null;
    },
    registerSuccess(
      state,
      action: PayloadAction<{ id: string; name: string }>
    ) {
      state.loading = false;
      state.user = action.payload;
    },
    registerFailure(state, action: PayloadAction<string>) {
      state.loading = false;
      state.error = action.payload;
    },
    // Action reset lỗi
    resetError(state) {
      state.error = null;
    },
  },
});

export const {
  loginRequest,
  loginSuccess,
  loginFailure,
  logout,
  registerRequest,
  registerSuccess,
  registerFailure,
  resetError,
} = authSlice.actions;

export default authSlice.reducer;
