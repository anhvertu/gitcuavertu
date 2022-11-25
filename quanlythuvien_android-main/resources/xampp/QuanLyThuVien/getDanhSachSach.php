<?php

require "connect.php";

$maDauSach = $_POST['maDauSach'];

class Sach
{
  public $MaSach;
  public $tenSach;
  public $TinhTrangSach;
  public $ChoMuon;
  public $MaDauSach;

  function __construct($MaSach, $tenSach, $TinhTrangSach, $ChoMuon, $MaDauSach)
  {
    $this->maSach = $MaSach;
    $this->tenSach = $tenSach;
    $this->tinhTrangSach = $TinhTrangSach;
    $this->choMuon = $ChoMuon;
    $this->maDauSach = $MaDauSach;
  }
}

$sql = "SELECT * FROM sach WHERE maDauSach = '$maDauSach' ";
$result = $con->query($sql);

$mangSach  = array();

if ($result->num_rows > 0) {

  while ($row = $result->fetch_assoc()) {
    array_push($mangSach, new Sach(
      $row['maSach'],
      $row['tenSach'],
      $row['tinhTrangSach'],
      $row['choMuon'],
      $row['maDauSach']
    ));
  }
  if (count($mangSach) > 0) {
    echo json_encode($mangSach, JSON_UNESCAPED_UNICODE);
  } else {
    echo "Fail";
  }
} else {
  echo "NULL";
}
