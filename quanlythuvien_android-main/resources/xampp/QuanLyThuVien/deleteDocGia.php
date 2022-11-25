<?php

require "connect.php";

$maDocGia = $_POST['maDocGia'];
$anhDocGia = $_POST['anhDocGia'];

if (strlen($maDocGia) > 0 && strlen($anhDocGia) > 0) {
    $query = "DELETE FROM docgia WHERE maDocGia='$maDocGia'";
    $data = mysqli_query($con, $query);
    $query1 = "DELETE FROM taikhoan WHERE maNguoiDung='$maDocGia'";
    $data1 = mysqli_query($con, $query1);

    if ($data && $data1) {
        unlink("images" . $anhDocGia);
        echo "Ok";
    } else {
        echo "Faild";
    }
} else {
    echo "NULL";
}
