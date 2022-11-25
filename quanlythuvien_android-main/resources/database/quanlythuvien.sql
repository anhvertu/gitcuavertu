-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 07, 2021 at 03:23 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quanlythuvien`
--

-- --------------------------------------------------------

--
-- Table structure for table `binhluandanhgia`
--

CREATE TABLE `binhluandanhgia` (
  `maDanhGia` int(11) NOT NULL,
  `moTa` varchar(255) NOT NULL,
  `soDanhGia` int(11) NOT NULL,
  `soLuotThich` int(11) NOT NULL,
  `ngayDang` varchar(15) NOT NULL,
  `maDocGia` varchar(25) NOT NULL,
  `maDauSach` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `binhluandanhgia`
--

INSERT INTO `binhluandanhgia` (`maDanhGia`, `moTa`, `soDanhGia`, `soLuotThich`, `ngayDang`, `maDocGia`, `maDauSach`) VALUES
(1, '...', 4, 0, '15/01/2019', 'DG00001', 'DS00001'),
(2, 'Tuyệt vời', 5, 0, '15/01/2019', 'DG00001', 'DS00002'),
(3, 'Good!!', 5, 0, '15/01/2019', 'DG00002', 'DS00001'),
(4, 'Sách rất bổ ích', 5, 0, '15/01/2019', 'DG00003', 'DS00001'),
(5, 'cũng được', 5, 0, '15/01/2019', 'DG00002', 'DS00001'),
(6, 'hay ...', 3, 0, '17/01/2019', 'DG00001', 'DS00001'),
(9, 'hay lắm...', 5, 0, '17/01/2019', 'DG00001', 'DS00001'),
(10, 'tam tạm', 4, 0, '17/01/2019', 'DG00001', 'DS00003'),
(11, 'cũng được', 4, 0, '17/01/2019', 'DG00001', 'DS00001'),
(12, 'dở ẹc', 1, 0, '17/01/2019', 'DG00001', 'DS00003'),
(13, 'hay ..!', 5, 0, '17/01/2019', 'DG00001', 'DS00002');

-- --------------------------------------------------------

--
-- Table structure for table `dausach`
--

CREATE TABLE `dausach` (
  `maDauSach` varchar(25) NOT NULL,
  `tenDauSach` varchar(25) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL,
  `maTheLoai` int(11) NOT NULL,
  `maNhaXuatBan` int(11) NOT NULL,
  `maTacGia` int(11) NOT NULL,
  `noiDungTomLuoc` varchar(1000) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL,
  `soLuong` int(11) NOT NULL,
  `maViTri` int(11) NOT NULL,
  `ngayXuatBan` varchar(15) NOT NULL,
  `soTrang` int(11) NOT NULL,
  `gia` int(11) NOT NULL,
  `anhDauSach` varchar(255) NOT NULL,
  `soDanhGia` float NOT NULL,
  `soNguoiDanhGia` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dausach`
--

INSERT INTO `dausach` (`maDauSach`, `tenDauSach`, `maTheLoai`, `maNhaXuatBan`, `maTacGia`, `noiDungTomLuoc`, `soLuong`, `maViTri`, `ngayXuatBan`, `soTrang`, `gia`, `anhDauSach`, `soDanhGia`, `soNguoiDanhGia`) VALUES
('DS00001', 'Lịch sử lớp 8', 6, 8, 17, 'Sách giáo khoa giải tích', 5, 3, '03/01/2019', 513, 15000, 'JPEG_20190117_130341_.jpg', 31, 7),
('DS00002', 'Tin học 10', 3, 8, 9, 'sách giáo khoa tin học 10', 10, 1, '17/01/2019', 512, 15000, 'JPEG_20190117_130628_.jpg', 0, 0),
('DS00003', 'Ngữ văn 11', 3, 3, 5, 'sách giao khoa', 0, 1, '04/10/2018', 512, 15000, 'JPEG_20190117_130913_.jpg', 0, 0),
('DS00004', 'Hinh học', 3, 6, 9, 'sach giao khoa', 0, 7, '06/11/2018', 512, 15000, 'JPEG_20190117_131044_.jpg', 0, 0),
('DS00005', 'Bổ trợ tiếng anh', 3, 6, 7, 'sách tham khảo', 0, 18, '02/01/2019', 512, 15000, 'JPEG_20190117_165210_.jpg', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `docgia`
--

CREATE TABLE `docgia` (
  `maDocGia` varchar(25) NOT NULL,
  `ten` varchar(25) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL,
  `email` varchar(50) NOT NULL,
  `ngaySinh` varchar(15) NOT NULL,
  `gioiTinh` varchar(10) NOT NULL,
  `soDienThoai` varchar(20) NOT NULL,
  `diaChi` varchar(50) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL,
  `ngayLamThe` varchar(15) NOT NULL,
  `anhDocGia` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `docgia`
--

INSERT INTO `docgia` (`maDocGia`, `ten`, `email`, `ngaySinh`, `gioiTinh`, `soDienThoai`, `diaChi`, `ngayLamThe`, `anhDocGia`) VALUES
('DG00001', 'Lê Tuấn Khải', 'tuankhai123456@gmail.com', '09/03/1998', 'Nữ', '012548752', 'quận 12', '05/01/2019', 'JPEG_20190117_172029_.jpg'),
('DG00002', 'Lê Tuấn Khôi', 'tuankhoi123@gmail.com', '01/01/2000', 'Nam', '01234567', 'q 12', '17/01/2019', 'JPEG_20190117_131155_.jpg'),
('DG00003', 'Châu Thị Ngọc', 'ngoc24@gmail.com', '01/01/2000', 'Nữ', '0123454', 'q12', '17/01/2019', 'JPEG_20190117_131312_.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `nhanvien`
--

CREATE TABLE `nhanvien` (
  `maNhanVien` varchar(25) NOT NULL,
  `ten` varchar(25) NOT NULL,
  `chucVu` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `ngaySinh` varchar(15) NOT NULL,
  `gioiTinh` varchar(10) NOT NULL,
  `soDienThoai` varchar(20) NOT NULL,
  `diaChi` varchar(255) NOT NULL,
  `ngayVaoLam` varchar(15) NOT NULL,
  `anhNhanVien` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `nhanvien`
--

INSERT INTO `nhanvien` (`maNhanVien`, `ten`, `chucVu`, `email`, `ngaySinh`, `gioiTinh`, `soDienThoai`, `diaChi`, `ngayVaoLam`, `anhNhanVien`) VALUES
('NV001', 'Trương Tố Xuân', 'Thủ thư', 'toxuan@gmail.com', '03/04/1998', 'Nữ', '01256789521', 'quận 12', '05/01/2019', 'JPEG_20190117_131944_.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `nhaxuatban`
--

CREATE TABLE `nhaxuatban` (
  `maNhaXuatBan` int(11) NOT NULL,
  `tenNhaXuatBan` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `nhaxuatban`
--

INSERT INTO `nhaxuatban` (`maNhaXuatBan`, `tenNhaXuatBan`) VALUES
(1, 'Nhà xuất bản chính trị và pháp luật'),
(3, 'Nhà xuất bản Mỹ thuật'),
(4, 'Nhà xuất bản Sân khấu'),
(5, 'Nhà xuất bản Hội nhà văn'),
(6, 'Nhà xuất bản Thông tin và truyền thông'),
(7, 'Nhà xuất bản Lao động xã hội'),
(8, 'Nhà xuất bản Khoa học xã hội'),
(9, 'Nhà xuất bản Kim Đồng');

-- --------------------------------------------------------

--
-- Table structure for table `sach`
--

CREATE TABLE `sach` (
  `maSach` varchar(25) NOT NULL,
  `tenSach` varchar(25) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL,
  `tinhTrangSach` varchar(25) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL,
  `choMuon` varchar(25) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL,
  `maDauSach` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sach`
--

INSERT INTO `sach` (`maSach`, `tenSach`, `tinhTrangSach`, `choMuon`, `maDauSach`) VALUES
('S00001', 'Giải tích 2', 'Thanh lý', 'Chưa cho Mượn', 'DS00001'),
('S00002', 'Giải tích 2', 'Tốt', 'Chưa cho Mượn', 'DS00001'),
('S00003', 'Giải tích 2', 'Tốt', 'Chưa cho Mượn', 'DS00001'),
('S00004', 'Giải tích 2', 'Tốt', 'Chưa cho Mượn', 'DS00001'),
('S00005', 'Giải tích 2', 'Tốt', 'Chưa cho Mượn', 'DS00001'),
('S00006', 'Tin học 10', 'Tốt', 'Chưa cho Mượn', 'DS00002'),
('S00007', 'Tin học 10', 'Tốt', 'Chưa cho Mượn', 'DS00002'),
('S00008', 'Tin học 10', 'Tốt', 'Chưa cho Mượn', 'DS00002'),
('S00009', 'Tin học 10', 'Tốt', 'Chưa cho Mượn', 'DS00002'),
('S00010', 'Tin học 10', 'Tốt', 'Chưa cho Mượn', 'DS00002'),
('S00011', 'Tin học 10', 'Tốt', 'Chưa cho Mượn', 'DS00002'),
('S00012', 'Tin học 10', 'Tốt', 'Chưa cho Mượn', 'DS00002'),
('S00013', 'Tin học 10', 'Tốt', 'Chưa cho Mượn', 'DS00002'),
('S00014', 'Tin học 10', 'Tốt', 'Chưa cho Mượn', 'DS00002'),
('S00015', 'Tin học 10', 'Tốt', 'Chưa cho Mượn', 'DS00002');

-- --------------------------------------------------------

--
-- Table structure for table `tacgia`
--

CREATE TABLE `tacgia` (
  `maTacGia` int(11) NOT NULL,
  `tenTacGia` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tacgia`
--

INSERT INTO `tacgia` (`maTacGia`, `tenTacGia`) VALUES
(1, 'Hồ Xuân Hương'),
(2, 'Nguyễn Du'),
(3, 'Văn Cao'),
(4, 'Tố Hữu'),
(5, 'Antôn Nguyễn Ngọc Sơn'),
(6, 'Bảo Ninh'),
(7, 'Băng Sơn'),
(8, 'Bùi Hiển'),
(9, 'Bùi Huy Phồn'),
(10, 'Bùi Ngọc Tấn'),
(11, 'Lan Cao'),
(12, 'Chu Cẩm Phong'),
(13, 'Dũng Hà'),
(14, 'Duy Khán'),
(15, 'Dương Hướng'),
(16, 'Dương Thị Xuân Quý'),
(17, 'Dương Thụy'),
(18, 'Dương Tường'),
(19, 'Văn cao');

-- --------------------------------------------------------

--
-- Table structure for table `taikhoan`
--

CREATE TABLE `taikhoan` (
  `taiKhoan` varchar(25) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL,
  `matKhau` varchar(25) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL,
  `quyenHan` varchar(25) CHARACTER SET utf8 COLLATE utf8_vietnamese_ci NOT NULL,
  `maNguoiDung` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `taikhoan`
--

INSERT INTO `taikhoan` (`taiKhoan`, `matKhau`, `quyenHan`, `maNguoiDung`) VALUES
('khai', '5555', 'DG', 'DG00001'),
('khoi', '1111', 'DG', 'DG00002'),
('ngoc', '1111', 'DG', 'DG00003'),
('xuan', '2222', 'TT', 'NV001');

-- --------------------------------------------------------

--
-- Table structure for table `theloai`
--

CREATE TABLE `theloai` (
  `maTheLoai` int(11) NOT NULL,
  `tenTheLoai` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `theloai`
--

INSERT INTO `theloai` (`maTheLoai`, `tenTheLoai`) VALUES
(1, 'khác'),
(2, 'Chính trị - Pháp luật'),
(3, 'Giáo trình - Giáo khoa'),
(4, 'Kinh tế'),
(5, 'Khoa học - Kĩ Thuật'),
(6, 'Lịch sử - Địa lý'),
(7, 'Ngoại văn - Từ điển'),
(8, 'Tâm lý - Kĩ Năng'),
(12, 'Thiếu nhi');

-- --------------------------------------------------------

--
-- Table structure for table `vitri`
--

CREATE TABLE `vitri` (
  `maViTri` int(11) NOT NULL,
  `tenViTri` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `vitri`
--

INSERT INTO `vitri` (`maViTri`, `tenViTri`) VALUES
(1, 'Kệ A - Ngăn A1'),
(3, 'Kệ A - Ngăn A2'),
(4, 'Kệ A - Ngăn A3'),
(5, 'Kệ A - Ngăn A4'),
(6, 'Kệ A - Ngăn A5'),
(7, 'Kệ A - Ngăn A6'),
(8, 'Kệ A - Ngăn A7'),
(9, 'Kệ A - Ngăn A8'),
(10, 'Kệ A - Ngăn A9'),
(11, 'Kệ A - Ngăn A10'),
(12, 'Kệ B - Ngăn B1'),
(13, 'Kệ B - Ngăn B2'),
(14, 'Kệ B - Ngăn B3'),
(15, 'Kệ B - Ngăn B4'),
(16, 'Kệ B - Ngăn B5'),
(17, 'Kệ B - Ngăn B6'),
(18, 'Kệ B - Ngăn B7'),
(19, 'Kệ B - Ngăn B8'),
(20, 'Kệ B - Ngăn B9'),
(21, 'Kệ B - Ngăn B10'),
(22, 'Kệ C - Ngăn C1'),
(23, 'Kệ C - Ngăn C2'),
(24, 'Kệ C - Ngăn C3'),
(25, 'Kệ C - Ngăn C4'),
(26, 'Kệ C - Ngăn C5'),
(27, 'Kệ C - Ngăn C6'),
(28, 'Kệ C - Ngăn C7'),
(29, 'Kệ C - Ngăn C8'),
(30, 'Kệ C - Ngăn C9'),
(31, 'Kệ C - Ngăn C10');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `binhluandanhgia`
--
ALTER TABLE `binhluandanhgia`
  ADD PRIMARY KEY (`maDanhGia`),
  ADD KEY `maDauSach` (`maDauSach`),
  ADD KEY `maDocGia` (`maDocGia`);

--
-- Indexes for table `dausach`
--
ALTER TABLE `dausach`
  ADD PRIMARY KEY (`maDauSach`),
  ADD KEY `maTheLoai` (`maTheLoai`),
  ADD KEY `maNhaXuatBan` (`maNhaXuatBan`),
  ADD KEY `maTacGia` (`maTacGia`),
  ADD KEY `maViTri` (`maViTri`);

--
-- Indexes for table `docgia`
--
ALTER TABLE `docgia`
  ADD PRIMARY KEY (`maDocGia`);

--
-- Indexes for table `nhanvien`
--
ALTER TABLE `nhanvien`
  ADD PRIMARY KEY (`maNhanVien`);

--
-- Indexes for table `nhaxuatban`
--
ALTER TABLE `nhaxuatban`
  ADD PRIMARY KEY (`maNhaXuatBan`);

--
-- Indexes for table `sach`
--
ALTER TABLE `sach`
  ADD PRIMARY KEY (`maSach`),
  ADD KEY `maDauSach` (`maDauSach`);

--
-- Indexes for table `tacgia`
--
ALTER TABLE `tacgia`
  ADD PRIMARY KEY (`maTacGia`);

--
-- Indexes for table `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`taiKhoan`);

--
-- Indexes for table `theloai`
--
ALTER TABLE `theloai`
  ADD PRIMARY KEY (`maTheLoai`);

--
-- Indexes for table `vitri`
--
ALTER TABLE `vitri`
  ADD PRIMARY KEY (`maViTri`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `binhluandanhgia`
--
ALTER TABLE `binhluandanhgia`
  MODIFY `maDanhGia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `nhaxuatban`
--
ALTER TABLE `nhaxuatban`
  MODIFY `maNhaXuatBan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `tacgia`
--
ALTER TABLE `tacgia`
  MODIFY `maTacGia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `theloai`
--
ALTER TABLE `theloai`
  MODIFY `maTheLoai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `vitri`
--
ALTER TABLE `vitri`
  MODIFY `maViTri` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `binhluandanhgia`
--
ALTER TABLE `binhluandanhgia`
  ADD CONSTRAINT `binhluandanhgia_ibfk_1` FOREIGN KEY (`maDauSach`) REFERENCES `dausach` (`maDauSach`),
  ADD CONSTRAINT `binhluandanhgia_ibfk_2` FOREIGN KEY (`maDocGia`) REFERENCES `docgia` (`maDocGia`);

--
-- Constraints for table `dausach`
--
ALTER TABLE `dausach`
  ADD CONSTRAINT `dausach_ibfk_1` FOREIGN KEY (`maTheLoai`) REFERENCES `theloai` (`maTheLoai`),
  ADD CONSTRAINT `dausach_ibfk_2` FOREIGN KEY (`maNhaXuatBan`) REFERENCES `nhaxuatban` (`maNhaXuatBan`),
  ADD CONSTRAINT `dausach_ibfk_3` FOREIGN KEY (`maTacGia`) REFERENCES `tacgia` (`maTacGia`),
  ADD CONSTRAINT `dausach_ibfk_4` FOREIGN KEY (`maViTri`) REFERENCES `vitri` (`maViTri`);

--
-- Constraints for table `sach`
--
ALTER TABLE `sach`
  ADD CONSTRAINT `sach_ibfk_1` FOREIGN KEY (`maDauSach`) REFERENCES `dausach` (`maDauSach`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
