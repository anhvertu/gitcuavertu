<?php

require "connect.php";

$maDocGia = $_POST['maDocGia'];
$ten = $_POST['ten'];
$email = $_POST['email'];
$ngaySinh = $_POST['ngaySinh'];
$gioiTinh = $_POST['gioiTinh'];
$soDienThoai = $_POST['soDienThoai'];
$diaChi = $_POST['diaChi'];
$ngayLamThe = $_POST['ngayLamThe'];
$anhDocGia = $_POST['anhDocGia'];


if (strlen($maDocGia) > 0 && strlen($ten) > 0 && strlen($email) > 0 && strlen($ngaySinh) > 0 && strlen($gioiTinh) > 0 && strlen($soDienThoai) > 0 && strlen($diaChi) > 0 && strlen($ngayLamThe) > 0 && strlen($anhDocGia) > 0) {
    $query = "INSERT INTO docgia VALUES ('$maDocGia','$ten','$email','$ngaySinh','$gioiTinh','$soDienThoai','$diaChi','$ngayLamThe','$anhDocGia')";
    $data = mysqli_query($con, $query);
    if ($data) {
        echo "Succsess";
    } else {
        echo "Faild";
    }
} else {
    echo "NULL";
}
