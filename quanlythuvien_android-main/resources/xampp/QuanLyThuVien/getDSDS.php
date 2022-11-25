<?php

require "connect.php";

$maDauSach = $_POST['maDauSach'];

if (strlen($maDauSach) > 0) {
  $sql = "SELECT count(*) as total from sach WHERE maDauSach = '$maDauSach'";
  $data = mysqli_query($con, $sql);
  if ($data) {
    $row = mysqli_fetch_assoc($data);
    echo $row['total'];
  }
} else {
  echo "NULL";
}
