<?php

require "connect.php";

$taiKhoan = $_POST['taiKhoan'];
$matKhau = $_POST['matKhau'];

if (strlen($taiKhoan) > 0 && strlen($matKhau) > 0) {
    $query = " UPDATE  taikhoan SET matKhau='$matKhau' WHERE taiKhoan = '$taiKhoan' ";
    $data = mysqli_query($con, $query);
    if ($data) {
        echo "Succsess";
    } else {
        echo "Faild" . mysqli_error($con);
    }
} else {
    echo "NULL";
}
