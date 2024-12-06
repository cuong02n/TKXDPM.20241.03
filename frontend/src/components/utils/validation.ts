// src/utils/validation.ts

export const isValidEmail = (email: string): boolean => {
  const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  return regex.test(email);
};

export const isValidPhoneNumber = (phone: string): boolean => {
  const regex = /^[0-9]{10}$/; // Cái này tùy vào quy định từng quốc gia
  return regex.test(phone);
};

export const isValidPassword = (password: string): boolean => {
  // Kiểm tra mật khẩu có ít nhất 8 ký tự, bao gồm chữ cái và số
  const regex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
  return regex.test(password);
};
