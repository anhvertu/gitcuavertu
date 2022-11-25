<?php

require "connect.php";

$key = $_POST['key'];

class TacGia
{
    public $mangTacGia;
    public $tenTacGia;

    function __construct($maTacGia, $tenTacGia)
    {
        $this->maTacGia = $maTacGia;
        $this->tenTacGia = $tenTacGia;
    }
}


$sql = "SELECT * FROM tacgia ";
$result = $con->query($sql);

$mangTacGia  = array();

if ($result->num_rows > 0) {

    while ($row = $result->fetch_assoc()) {
        array_push($mangTacGia, new TacGia(
            $row['maTacGia'],
            $row['tenTacGia']
        ));
    }
    if (count($mangTacGia) > 0) {
        echo json_encode($mangTacGia, JSON_UNESCAPED_UNICODE);
    } else {
        echo "Fail" . mysqli_error($con);
    }
} else {
    echo "NULL";
}
