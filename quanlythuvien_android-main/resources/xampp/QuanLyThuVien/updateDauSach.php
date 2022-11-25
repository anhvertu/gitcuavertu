<?php

require "connect.php";

$maDauSach = $_POST['maDauSach'];
$tenDauSach = $_POST['tenDauSach'];
$maTheLoai = $_POST['maTheLoai'];
$maNhaXuatBan = $_POST['maNhaXuatBan'];
$maTacGia = $_POST['maTacGia'];
$noiDungTomLuoc = $_POST['noiDungTomLuoc'];
$soLuong = $_POST['soLuong'];
$maViTri = $_POST['maViTri'];
$ngayXuatBan = $_POST['ngayXuatBan'];
$soTrang = $_POST['soTrang'];
$gia = $_POST['gia'];
$anhDauSach = $_POST['anhDauSach'];
$soDanhGia = $_POST['soDanhGia'];
$soNguoiDanhGia = $_POST['soNguoiDanhGia'];

if (strlen($maDauSach) > 0 && strlen($tenDauSach) > 0 && strlen($maTheLoai) > 0 && strlen($maNhaXuatBan) > 0 && strlen($maTacGia) > 0 && strlen($noiDungTomLuoc) > 0 && strlen($soLuong) > 0 && strlen($maViTri) > 0 && strlen($ngayXuatBan) > 0 && strlen($soTrang) > 0 && strlen($gia) > 0 &&  strlen($anhDauSach) > 0 && strlen($soDanhGia) > 0 && strlen($soNguoiDanhGia) > 0) {
    $query = " UPDATE  dausach SET tenDauSach='$tenDauSach',maTheLoai ='$maTheLoai',maNhaXuatBan ='$maNhaXuatBan'
                                     ,maTacGia='$maTacGia',noiDungTomLuoc='$noiDungTomLuoc',soLuong='$soLuong'
                                     , maViTri = '$maViTri',ngayXuatBan = '$ngayXuatBan',soTrang = '$soTrang',gia = '$gia'
                                     ,soDanhGia = '$soDanhGia',soNguoiDanhGia = '$soNguoiDanhGia',anhDauSach='$anhDauSach' 
                                     WHERE maDauSach = '$maDauSach' ";
    $data = mysqli_query($con, $query);
    if ($data) {
        unlink("images".$anhDauSach);
        echo "Succsess1";
    } else {
        echo "Faild" . mysqli_error($con);
    }
} else {
    echo "NULL";
}
