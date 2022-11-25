<?php

require "connect.php";

$maDauSach = $_POST['maDauSach'];
$anhDauSach = $_POST['anhDauSach'];


if (strlen($maDauSach) > 0 && strlen($anhDauSach) > 0) {
	$query = "DELETE FROM dausach WHERE maDauSach='$maDauSach'";
	$data = mysqli_query($con, $query);
	if ($data) {
		unlink("images" . $anhDauSach);
		echo "Ok";
	} else {
		echo "Faild";
	}
} else {
	echo "NULL";
}
