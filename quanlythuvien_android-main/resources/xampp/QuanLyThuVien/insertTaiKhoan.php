<?php

require "connect.php";

$taiKhoan = $_POST['taiKhoan'];
$matKhau = $_POST['matKhau'];
$quyenHan = $_POST['quyenHan'];
$maNguoiDung = $_POST['maNguoiDung'];

if (strlen($taiKhoan) > 0 && strlen($matKhau) > 0 && strlen($quyenHan) > 0 && strlen($maNguoiDung) > 0) {
    $query = "INSERT INTO taikhoan VALUES ('$taiKhoan','$matKhau','$quyenHan','$maNguoiDung')";
    $data = mysqli_query($con, $query);
    if ($data) {
        echo "Succsess";
    } else {
        echo "Faild";
    }
} else {
    echo "NULL";
}
