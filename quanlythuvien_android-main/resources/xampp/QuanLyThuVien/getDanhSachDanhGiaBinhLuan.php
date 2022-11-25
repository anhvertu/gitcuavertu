<?php

require "connect.php";

$maDauSach = $_POST['maDauSach'];
$batDau = intval($_POST['batDau']);

class DanhGiaBinhLuan
{
  public $maDanhGia;
  public $tenNguoiDung;
  public $moTa;
  public $soDanhGia;
  public $soLuotThich;
  public $ngayDang;
  public $anhNguoiDung;
  public $maDauSach;
  
  function __construct($maDanhGia, $tenNguoiDung, $moTa, $soDanhGia, $soLuotThich, $ngayDang, $anhNguoiDung, $maDauSach)
  {
    $this->maDanhGia = $maDanhGia;
    $this->tenNguoiDung = $tenNguoiDung;
    $this->moTa = $moTa;
    $this->soDanhGia = $soDanhGia;
    $this->soLuotThich = $soLuotThich;
    $this->ngayDang = $ngayDang;
    $this->anhNguoiDung = $anhNguoiDung;
    $this->maDauSach = $maDauSach;
  }
}

$sql = "SELECT * FROM binhluandanhgia WHERE maDauSach = '$maDauSach' ORDER BY maDanhGia DESC LIMIT $batDau,6 ";
$result = $con->query($sql);

$mangDanhGia  = array();

if ($result->num_rows > 0) {

  while ($row = $result->fetch_assoc()) {

    $maDocGia = $row['maDocGia'];
    $sql1 = "SELECT ten,anhDocGia from docgia WHERE maDocGia = '$maDocGia'";
    $data1 = mysqli_query($con, $sql1);
    $row1 = mysqli_fetch_assoc($data1);
    array_push($mangDanhGia, new DanhGiaBinhLuan(
      $row['maDanhGia'],
      $row1['ten'],
      $row['moTa'],
      $row['soDanhGia'],
      $row['soLuotThich'],
      $row['ngayDang'],
      $row1['anhDocGia'],
      $row['maDauSach']
    ));
  }
  if (count($mangDanhGia) > 0) {
    echo json_encode($mangDanhGia, JSON_UNESCAPED_UNICODE);
  } else {
    echo "Fail";
  }
} else {
  echo "NULL";
}


$con->close();
