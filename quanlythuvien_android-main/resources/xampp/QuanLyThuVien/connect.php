<?php

$hostname = "localhost";
$usename = "root";
$password = "";
$databasename = "quanlythuvien";

$con = mysqli_connect($hostname, $usename, $password, $databasename);
mysqli_query($con, "SET NAMES 'UTF8'");

