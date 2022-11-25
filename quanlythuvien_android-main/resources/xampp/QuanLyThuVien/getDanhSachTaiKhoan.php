<?php

require "connect.php";

$key = $_POST['key'];

class TaiKhoan
{
  public $taiKhoan;
  public $matKhau;
  public $quyenHan;
  public $maNguoiDung;

  function __construct($taiKhoan, $matKhau, $quyenHan, $maNguoiDung)
  {
    $this->taiKhoan = $taiKhoan;
    $this->matKhau = $matKhau;
    $this->quyenHan = $quyenHan;
    $this->maNguoiDung = $maNguoiDung;
  }
}

$sql = "SELECT * FROM taikhoan";
$result = $con->query($sql);

$mangTaiKhoan  = array();

if ($result->num_rows > 0) {

  while ($row = $result->fetch_assoc()) {

    array_push($mangTaiKhoan, new TaiKhoan(
      $row['taiKhoan'],
      $row['matKhau'],
      $row['quyenHan'],
      $row['maNguoiDung']
    ));
  }
  if (count($mangTaiKhoan) > 0) {
    echo json_encode($mangTaiKhoan, JSON_UNESCAPED_UNICODE);
  } else {
    echo "Fail";
  }
} else {
  echo "NULL";
}
