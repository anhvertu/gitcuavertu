<?php

require "connect.php";

$ma = $_POST['ma'];
$ten = $_POST['ten'];
$loaiDeMuc = $_POST['loaiDeMuc'];

if (strlen($ma) > 0 && strlen($ten) > 0 && strlen($loaiDeMuc) > 0) {

    switch ($loaiDeMuc) {
        case "0":
            $query = " UPDATE  theloai SET tenTheLoai = '$ten' WHERE maTheLoai = '$ma' ";
            break;
        case "1":
            $query = " UPDATE  tacgia SET tenTacGia = '$ten' WHERE maTacGia = '$ma' ";
            break;
        case "2":
            $query = " UPDATE  nhaxuatban SET tenNhaXuatBan = '$ten' WHERE maNhaXuatBan = '$ma' ";
            break;

        case "3":
            $query = " UPDATE  vitri SET tenViTri = '$ten' WHERE maViTri = '$ma' ";
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
