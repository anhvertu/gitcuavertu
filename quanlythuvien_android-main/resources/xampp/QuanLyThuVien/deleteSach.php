<?php

require "connect.php";

$maSach = $_POST['maSach'];

if (strlen($maSach) > 0) {
	$query = "DELETE FROM sach WHERE maSach='$maSach'";
	$data = mysqli_query($con, $query);
	if ($data) {
		echo "Ok";
	} else {
		echo "Faild";
	}
} else {
	echo "NULL";
}
