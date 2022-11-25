<?php

require "connect.php";

$email = $_POST['email'];

if (strlen($email) > 0) {
  $sql = "SELECT maDocGia from docgia WHERE email = '$email'";
  $result = $con->query($sql);

  if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    $ma = $row['maDocGia'];

    $sql = "SELECT taiKhoan from taikhoan WHERE maNguoiDung = '$ma'";
    $result = $con->query($sql);
    if ($result->num_rows > 0) {
      $row = $result->fetch_assoc();
      echo $row['taiKhoan'];
    } else {
      echo "fail";
    }
  } else {
    echo "fail";
  }
} else {
  echo "fail";
}
