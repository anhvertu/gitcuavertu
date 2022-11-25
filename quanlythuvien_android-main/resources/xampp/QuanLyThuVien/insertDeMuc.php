<?php

require "connect.php";

$ten = $_POST['ten'];
$loaiDeMuc = $_POST['loaiDeMuc'];

if (strlen($ten) > 0 && strlen($loaiDeMuc) > 0) {

    switch ($loaiDeMuc) {
        case "0":
            $query = "INSERT INTO theloai VALUES ('null','$ten')";
            break;
        case "1":
            $query = "INSERT INTO tacgia VALUES ('null','$ten')";
            break;
        case "2":
            $query = "INSERT INTO nhaxuatban VALUES ('null','$ten')";
            break;

        case "3":
            $query = "INSERT INTO vitri VALUES ('null','$ten')";
            break;
        default:
    }

    $data = mysqli_query($con, $query);
    if ($data) {
        echo "Succsess";
    } else {
        echo "Faild";
    }
} else {
    echo "NULL";
}
