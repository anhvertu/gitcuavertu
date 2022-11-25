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
$anhCu = $_POST['anhCu'];

if (strlen($maDocGia) > 0 && strlen($ten) > 0 && strlen($email) > 0   && strlen($ngaySinh) > 0 && strlen($gioiTinh) > 0 && strlen($soDienThoai) > 0 && strlen($diaChi) > 0 && strlen($ngayLamThe) > 0 && strlen($anhDocGia) > 0) {
    $query1 = "SELECT anhDocGia FROM docgia WHERE maDocGia = '$maDocGia'";
    $data1 = mysqli_query($con, $query1);

    $query = " UPDATE  docgia SET ten='$ten',email ='$email'
                                     ,ngaySinh='$ngaySinh',gioiTinh='$gioiTinh'
                                     ,soDienThoai = '$soDienThoai',diaChi = '$diaChi',ngayLamThe = '$ngayLamThe'
                                     ,anhDocGia = '$anhDocGia' WHERE maDocGia = '$maDocGia' ";
    if ($con->query($query) === TRUE) {
        echo "Succsess1";
        if (strlen($anhCu) > 1) {
            unlink("images" . $anhCu);
        }
    } else {
        echo "Error updating record:" . mysqli_error($con);
    }
} else {
    echo "NULL";
}
