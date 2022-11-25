<?php

require "connect.php";

$maSach = $_POST['maSach'];
$tenSach = $_POST['tenSach'];
$tinhTrangSach = $_POST['tinhTrangSach'];
$choMuon = $_POST['choMuon'];
$maDauSach = $_POST['maDauSach'];

if (strlen($maSach) > 0 && strlen($tenSach) > 0 &&  strlen($tinhTrangSach) > 0 && strlen($choMuon) > 0 && strlen($maDauSach) > 0) {
    $query = "INSERT INTO sach  VALUES('$maSach', '$tenSach', '$tinhTrangSach', '$choMuon', '$maDauSach')";
    $data = mysqli_query($con, $query);
    if ($data) {
        echo "Succsess";
    } else {
        echo "Faild";
    }
} else {
    echo "NULL";
}
