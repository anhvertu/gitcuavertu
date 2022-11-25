<?php

require "connect.php";

$key = $_POST['key'];

class NhaXuatBan
{
    public $maNhaXuatBan;
    public $tenNhaXuatBan;

    function __construct($maNhaXuatBan, $tenNhaXuatBan)
    {
        $this->maNhaXuatBan = $maNhaXuatBan;
        $this->tenNhaXuatBan = $tenNhaXuatBan;
    }
}

$sql = "SELECT * FROM nhaxuatban ";
$result = $con->query($sql);

$mangNhaXuatBan  = array();

if ($result->num_rows > 0) {

    while ($row = $result->fetch_assoc()) {
        array_push($mangNhaXuatBan, new NhaXuatBan(
            $row['maNhaXuatBan'],
            $row['tenNhaXuatBan']
        ));
    }
    if (count($mangNhaXuatBan) > 0) {
        echo json_encode($mangNhaXuatBan, JSON_UNESCAPED_UNICODE);
    } else {
        echo "Fail" . mysqli_error($con);
    }
} else {
    echo "NULL";
}
