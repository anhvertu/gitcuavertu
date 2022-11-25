<?php

require "connect.php";

$key = $_POST['key'];

class NhanVien
{
  public $maNhanVien;
  public $ten;
  public $chucVu;
  public $email;
  public $ngaySinh;
  public $gioiTinh;
  public $soDienThoai;
  public $diaChi;
  public $ngayVaoLam;
  public $anhNhanVien;

  function __construct($maNhanVien, $ten, $chucVu, $email, $ngaySinh, $gioiTinh, $soDienThoai, $diaChi, $ngayVaoLam, $anhNhanVien)
  {
    $this->maNhanVien = $maNhanVien;
    $this->ten = $ten;
    $this->chucVu = $chucVu;
    $this->email = $email;
    $this->ngaySinh = $ngaySinh;
    $this->gioiTinh = $gioiTinh;
    $this->soDienThoai = $soDienThoai;
    $this->diaChi = $diaChi;
    $this->ngayVaoLam = $ngayVaoLam;
    $this->anhNhanVien = $anhNhanVien;
  }
}

$sql = "SELECT * FROM nhanvien ";
$result = $con->query($sql);

$mangNhanVien  = array();

if ($result->num_rows > 0) {
  while ($row = $result->fetch_assoc()) {
    array_push($mangNhanVien, new NhanVien(
      $row['maNhanVien'],
      $row['ten'],
      $row['chucVu'],
      $row['email'],
      $row['ngaySinh'],
      $row['gioiTinh'],
      $row['soDienThoai'],
      $row['diaChi'],
      $row['ngayVaoLam'],
      $row['anhNhanVien']
    ));
  }
  if (count($mangNhanVien) > 0) {
    echo json_encode($mangNhanVien, JSON_UNESCAPED_UNICODE);
  } else {
    echo "Fail";
  }
} else {
  echo "NULL";
}
