<?php

require "connect.php";

$maUser = $_POST['maUser'];

class DocGia
{
  public $maDocGia;
  public $ten;
  public $email;
  public $ngaySinh;
  public $gioiTinh;
  public $soDienThoai;
  public $diaChi;
  public $ngayLamThe;
  public $anhDocGia;

  function __construct($maDocGia, $ten, $email, $ngaySinh, $gioiTinh, $soDienThoai, $diaChi, $ngayLamThe, $anhDocGia)
  {
    $this->maDocGia = $maDocGia;
    $this->ten = $ten;
    $this->email = $email;
    $this->ngaySinh = $ngaySinh;
    $this->gioiTinh = $gioiTinh;
    $this->soDienThoai = $soDienThoai;
    $this->diaChi = $diaChi;
    $this->ngayLamThe = $ngayLamThe;
    $this->anhDocGia = $anhDocGia;
  }
}

$sql = "SELECT * FROM docgia WHERE maDocGia = '$maUser' ";
$result = $con->query($sql);

$mangDocGia  = array();

if ($result->num_rows > 0) {
  while ($row = $result->fetch_assoc()) {
    array_push($mangDocGia, new DocGia(
      $row['maDocGia'],
      $row['ten'],
      $row['email'],
      $row['ngaySinh'],
      $row['gioiTinh'],
      $row['soDienThoai'],
      $row['diaChi'],
      $row['ngayLamThe'],
      $row['anhDocGia']
    ));
  }
  if (count($mangDocGia) > 0) {
    echo json_encode($mangDocGia, JSON_UNESCAPED_UNICODE);
  } else {
    echo "Fail";
  }
} else {
  echo "NULL";
}
