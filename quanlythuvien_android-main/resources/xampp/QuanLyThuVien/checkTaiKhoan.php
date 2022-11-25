<?php

require "connect.php";

$taiKhoan = $_POST['taiKhoan'];

if (strlen($taiKhoan) > 0) {
  $sql = "SELECT count(*) as total from taikhoan WHERE taiKhoan = '$taiKhoan'";
  $data = mysqli_query($con, $sql);
  if ($data) {
    $row = mysqli_fetch_assoc($data);
    echo $row['total'];
  }
} else {
  echo "NULL";
}
