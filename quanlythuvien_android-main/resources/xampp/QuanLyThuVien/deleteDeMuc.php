<?php

require "connect.php";

$ma = $_POST['ma'];
$loaiDeMuc = $_POST['loaiDeMuc'];

if (strlen($ma) > 0 && strlen($loaiDeMuc) > 0) {

    switch ($loaiDeMuc) {
        case "0":
            $query = " DELETE FROM theloai WHERE maTheLoai = '$ma' ";
            break;
        case "1":
            $query = " DELETE FROM tacgia   WHERE maTacGia = '$ma' ";
            break;
        case "2":
            $query = " DELETE FROM nhaxuatban  WHERE maNhaXuatBan = '$ma' ";
            break;

        case "3":
            $query = " DELETE FROM vitri WHERE maViTri = '$ma' ";
            break;
        default:
    }

    $data = mysqli_query($con, $query);
    if ($data) {
        echo "Succsess";
    } else {
        echo "Faild";
    }
} else {
    echo "NULL";
}
