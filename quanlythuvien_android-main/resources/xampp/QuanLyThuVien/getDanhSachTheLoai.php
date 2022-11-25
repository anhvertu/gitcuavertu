<?php

require "connect.php";


$key = $_POST['key'];



class TheLoai
{
    public $mangTheLoai;
    public $tenTheLoai;

    function __construct($maTheLoai, $tenTheLoai)
    {
        $this->maTheLoai = $maTheLoai;
        $this->tenTheLoai = $tenTheLoai;
    }
}


$sql = "SELECT * FROM theloai ";
$result = $con->query($sql);

$mangTheLoai  = array();

if ($result->num_rows > 0) {

    while ($row = $result->fetch_assoc()) {
        array_push($mangTheLoai, new TheLoai(
            $row['maTheLoai'],
            $row['tenTheLoai']
        ));
    }
    if (count($mangTheLoai) > 0) {
        echo json_encode($mangTheLoai, JSON_UNESCAPED_UNICODE);
    } else {
        echo "Fail" . mysqli_error($con);
    }
} else {
    echo "NULL";
}
