<?php

require "connect.php";

$key = $_POST['key'];

$sql = "SELECT maSach AS last FROM sach ORDER BY maSach DESC LIMIT 1";
$data = mysqli_query($con, $sql);
$row = mysqli_fetch_assoc($data);
echo $row['last'];
