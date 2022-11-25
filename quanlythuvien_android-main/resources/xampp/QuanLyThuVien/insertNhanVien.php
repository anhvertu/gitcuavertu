<?php

require "connect.php";

$maNhanVien = $_POST['maNhanVien'];
$ten = $_POST['ten'];
$chucVu = $_POST['chucVu'];
$email = $_POST['email'];
$ngaySinh = $_POST['ngaySinh'];
$gioiTinh = $_POST['gioiTinh'];
$soDienThoai = $_POST['soDienThoai'];
$diaChi = $_POST['diaChi'];
$ngayVaoLam = $_POST['ngayVaoLam'];
$anhNhanVien = $_POST['anhNhanVien'];

if (strlen($maNhanVien) > 0 && strlen($ten) > 0 && strlen($chucVu) > 0  && strlen($email) > 0   && strlen($ngaySinh) > 0 && strlen($gioiTinh) > 0 && strlen($soDienThoai) > 0 && strlen($diaChi) > 0 && strlen($ngayVaoLam) > 0 && strlen($anhNhanVien) > 0) {

    $query = "INSERT INTO nhanvien VALUES ('$maNhanVien','$ten','$chucVu','$email','$ngaySinh','$gioiTinh','$soDienThoai','$diaChi','$ngayVaoLam','$anhNhanVien')";

    if ($con->query($query) === TRUE) {
        echo "Succsess";
    } else {
        echo "Error updating record:" . mysqli_error($con);
    }
} else {
    echo "NULL";
}
