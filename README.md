# Bài tập lớn môn thiết kế & xây dựng phần mềm.

### Phân chia công việc

##### Nguyễn Mạnh Cường
- Làm các phần việc ở backend 
- Cơ sở dữ liệu 
- Triển khai máy chủ, môi trường cho team 
- Thiết kế bảo mật
- Usecase: Tìm kiếm sản phẩm
- Usecase: Đánh giá sản phẩm
##### Nguyễn Trung Chiến:
- Làm báo cáo
- Usecase: Quản lý sản phẩm, giỏ hàng
- Usecase: Tìm kiếm sản phẩm
- Usecase: Đặt hàng
##### Vũ Ngọc Anh:
- Usecase: Đăng nhập + đăng kí.
- Template frontend
##### Bùi Minh Hải Đắc
- Usecase: Sản phẩm yêu thích.

##### Trần Đức Chính
- Usecase: Thanh toán


# Hướng dẫn chạy
- Yêu cầu hệ thống
	- [docker](https://docs.docker.com/engine/install/) 
	- [docker-compose](https://docs.docker.com/compose/install/)
	- Hoặc sử dụng [docker desktop](https://docs.docker.com/desktop/) ( _Không cần cài đặt riêng docker và docker compose_)
	- Hệ điều hành: Window, Linux, Mac
	- Ứng dụng cmdline: Window Terminal, Powershell, Git bash, ...
## Build ứng dụng
- Tham khảo file `.env.example`, thay đổi nếu cần thiết và copy nội dung sang file mới `.env`.
- Build và chạy full ứng dụng, dùng lệnh `docker compose up`.
- Chỉ build hoặc chạy riêng từng phần:
	- Tất cả: `docker compose build`
	- Backend: `docker compose build backend` hoặc `docker compose up backend`
	- Frontend: `docker compose build frontend` hoặc `docker compose up frontend`
	- Database: `docker compose build database` hoặc `docker compose up database`
- Xóa containers: `docker compose down`
