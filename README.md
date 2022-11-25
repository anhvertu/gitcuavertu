# gitcuavertu

## Gồm các chức năng sau

- Đăng nhập (độc giả,thủ thư)
- Quản lý sách, đầu sách(thêm,xóa,sửa,tìm kiếm
- Quản lý độc giả, nhân viên
- Quản lý đề mục
- Ngoài ra còn tìm kiếm sách,dộc giả qua quét mã QR,tìm kiếm theo tên,mã,...

## Hướng dẫn cài đặt
- Bạn cần tải xampp để tạo database
- Sau khi cài xampp, vào link https://127.0.0.1/phpmyadmin/index.php(hoặc https://localhost/phpmyadmin/index.php) tạo mới database tên quanlythuvien va chọn "import" từ file resources/database/quanlythuvien.sql để tạo tables với data
- Copy folder QuanLyThuVien tại resources/xampp/ vào dường dẫn C:\xampp\htdocs
*** Lưu ý cần đổi địa chỉ url lại trước khi chạy bằng cách vào cmd gõ ipconfig tìm IPv4 adress sao chép và thay thế ở file app/java/com.example.admin.quan_ly_thu_vien/Retrofit2/APIUtils (vd ipv4 của bạn là 172.16.1.29 thì thay thế dòng public static final String Base_Url = "http://172.16.0.234/QuanLyThuVien/"; thành public static final String Base_Url = "http://172.16.1.29/QuanLyThuVien/";) 