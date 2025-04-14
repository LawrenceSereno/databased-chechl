<?php
$connect = new mysqli("localhost", "root", "", "testing_databased");

if ($connect->connect_error) {
    die("Connection failed: " . $connect->connect_error); // If the connection fails, this will show the error
}
// echo "Connected to database"; // Uncomment this line for debugging purposes
?>
