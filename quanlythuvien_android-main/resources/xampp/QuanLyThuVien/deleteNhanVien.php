<?php

require "connect.php";

$maNhanVien = $_POST['maNhanVien'];
$anhNhanVien = $_POST['anhNhanVien'];

if (strlen($maNhanVien) > 0 && strlen($anhNhanVien) > 0) {
    $query = "DELETE FROM nhanvien WHERE maNhanVien='$maNhanVien'";
    $data = mysqli_query($con, $query);
    $query1 = "DELETE FROM taikhoan WHERE maNguoiDung='$maNhanVien'";
    $data1 = mysqli_query($con, $query1);

    if ($data && $data1) {
        unlink("images" . $anhNhanVien);
        echo "Ok";
    } else {
        echo "Faild";
    }
} else {
    echo "NULL";
}
