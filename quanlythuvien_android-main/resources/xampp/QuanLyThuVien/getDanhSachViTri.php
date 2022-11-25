<?php

require "connect.php";

$key = $_POST['key'];

class ViTri
{
    public $maViTri;
    public $tenViTri;

    function __construct($maViTri, $tenViTri)
    {
        $this->maViTri = $maViTri;
        $this->tenViTri = $tenViTri;
    }
}

$sql = "SELECT * FROM vitri ";
$result = $con->query($sql);

$mangViTri  = array();

if ($result->num_rows > 0) {

    while ($row = $result->fetch_assoc()) {
        array_push($mangViTri, new ViTri(
            $row['maViTri'],
            $row['tenViTri']
        ));
    }
    if (count($mangViTri) > 0) {
        echo json_encode($mangViTri, JSON_UNESCAPED_UNICODE);
    } else {
        echo "Fail" . mysqli_error($con);
    }
} else {
    echo "NULL";
}
