<?php

require "connect.php";

$key = $_POST['key'];

class DauSach
{
  public $MaDauSach;
  public $TenDauSach;
  public $maTheLoai;
  public $maNhaXuatBan;
  public $maTacGia;
  public $NoiDungTomLuoc;
  public $SoLuong;
  public $maViTri;
  public $NgayXuatBan;
  public $SoTrang;
  public $Gia;
  public $AnhDauSach;
  public $soDanhGia;
  public $soNguoiDanhGia;

  function __construct($MaDauSach, $TenDauSach, $maTheLoai, $maNhaXuatBan, $maTacGia, $NoiDungTomLuoc, $SoLuong, $maViTri, $NgayXuatBan, $SoTrang, $Gia, $AnhDauSach, $soDanhGia, $soNguoiDanhGia)
  {
    $this->maDauSach = $MaDauSach;
    $this->tenDauSach = $TenDauSach;
    $this->maTheLoai = $maTheLoai;
    $this->maNhaXuatBan = $maNhaXuatBan;
    $this->maTacGia = $maTacGia;
    $this->noiDungTomLuoc = $NoiDungTomLuoc;
    $this->soLuong = $SoLuong;
    $this->maViTri = $maViTri;
    $this->ngayXuatBan = $NgayXuatBan;
    $this->soTrang = $SoTrang;
    $this->gia = $Gia;
    $this->anhDauSach = $AnhDauSach;
    $this->soDanhGia = $soDanhGia;
    $this->soNguoiDanhGia = $soNguoiDanhGia;
  }
}

$sql = "SELECT * FROM dausach ";
$result = $con->query($sql);

$mangDauSach  = array();

if ($result->num_rows > 0) {

  while ($row = $result->fetch_assoc()) {

    $maDauSach1 = $row['maDauSach'];
    $sql1 = "SELECT count(*) as total from sach WHERE maDauSach = '$maDauSach1'";
    $data1 = mysqli_query($con, $sql1);
    $row1 = mysqli_fetch_assoc($data1);
    $soLuong1 = $row1['total'];

    $sql2 = "UPDATE dausach SET soLuong = '$soLuong1' WHERE maDauSach = '$maDauSach1' ";
    $data2 = mysqli_query($con, $sql2);

    $sql3 = "SELECT sum(soDanhGia) as tongDanhGia,count(*) as tongNguoi from binhluandanhgia WHERE maDauSach = '$maDauSach1'";
    $data3 = mysqli_query($con, $sql3);
    $row3 = mysqli_fetch_assoc($data3);

    array_push($mangDauSach, new DauSach(
      $row['maDauSach'],
      $row['tenDauSach'],
      $row['maTheLoai'],
      $row['maNhaXuatBan'],
      $row['maTacGia'],
      $row['noiDungTomLuoc'],
      $row['soLuong'],
      $row['maViTri'],
      $row['ngayXuatBan'],
      $row['soTrang'],
      $row['gia'],
      $row['anhDauSach'],
      $row3['tongDanhGia'],
      $row3['tongNguoi']
    ));
  }
  if (count($mangDauSach) > 0) {
    echo json_encode($mangDauSach, JSON_UNESCAPED_UNICODE);
  } else {
    echo "Fail";
  }
} else {
  echo "NULL";
}
