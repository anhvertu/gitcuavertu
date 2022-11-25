<?php

require "connect.php";

$maSach = $_POST['maSach'];
$tenSach = $_POST['tenSach'];
$tinhTrangSach = $_POST['tinhTrangSach'];
$choMuon = $_POST['choMuon'];
$maDauSach = $_POST['maDauSach'];

if (strlen($maSach) > 0 && strlen($tenSach) > 0 && strlen($tinhTrangSach) > 0 && strlen($choMuon) > 0 && strlen($maDauSach) > 0) {
  $query = " UPDATE  sach SET tenSach = '$tenSach', tinhTrangSach ='$tinhTrangSach',choMuon = '$choMuon' WHERE maSach = '$maSach' ";
  $data = mysqli_query($con, $query);
  if ($data) {
    echo "Succsess1";
  } else {
    echo "Faild" . mysqli_error($con);
  }
} else {
  echo "NULL";
}
