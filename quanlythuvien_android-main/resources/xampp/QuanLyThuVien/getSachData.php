<?php

require "connect.php";

$maSach = $_POST['maSach'];

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

if (strlen($maSach) > 0) {
  $mangSach  = array();
  $sql = "SELECT * FROM sach WHERE FIND_IN_SET('$maSach',maSach) ";
  $data = mysqli_query($con, $sql);
  if ($data) {
    while ($row = mysqli_fetch_assoc($data)) {
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
  }
} else {
  echo "NULL";
}
