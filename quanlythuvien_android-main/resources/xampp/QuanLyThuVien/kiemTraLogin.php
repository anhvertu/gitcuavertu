<?php

require "connect.php";

$taiKhoan = $_POST['taiKhoan'];
$matKhau = $_POST['matKhau'];

$sql = "SELECT quyenHan,maNguoiDung FROM taikhoan WHERE taiKhoan = '$taiKhoan' AND matKhau = '$matKhau'";
$result = $con->query($sql);
if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    $maNguoiDung = $row['maNguoiDung'];
    $sql  = "SELECT * FROM nhanvien WHERE maNhanVien = '$maNguoiDung'";
    if ($row['quyenHan'] == "DG")
        $sql = "SELECT * FROM docgia WHERE maDocGia = '$maNguoiDung'";
    $result = $con->query($sql);

    if ($result->num_rows > 0) {
        $obj = new stdClass();
        $obj->quyenHan = $row['quyenHan'];
        $obj->maNguoiDung = $maNguoiDung;

        echo  json_encode($obj);
    }
} else {
    echo "NULL";
}
