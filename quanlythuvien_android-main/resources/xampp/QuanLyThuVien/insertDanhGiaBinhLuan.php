<?php

require "connect.php";

$moTa = $_POST['moTa'];
$soDanhGia = $_POST['soDanhGia'];
$soLuotThich = $_POST['soLuotThich'];
$ngayDang = $_POST['ngayDang'];
$maDocGia = $_POST['maDocGia'];
$maDauSach = $_POST['maDauSach'];

if (strlen($moTa) > 0 && strlen($soDanhGia) > 0 && strlen($soLuotThich) > 0 && strlen($ngayDang) > 0 && strlen($maDocGia) > 0 && strlen($maDauSach) > 0) {
    $query = "INSERT INTO binhluandanhgia VALUES ('null','$moTa','$soDanhGia','$soLuotThich','$ngayDang','$maDocGia','$maDauSach')";
    $data = mysqli_query($con, $query);
    if ($data) {
        echo "Succsess";
    } else {
        echo "Faild";
    }
} else {
    echo "NULL";
}
