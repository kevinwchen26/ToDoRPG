<?php

$response = array();
require_once 'db_connect.php';
$db = new DB_CONNECT();
 
$result = mysql_query("SELECT *FROM log_db") or die(mysql_error());
 
if (mysql_num_rows($result) > 0) {
    $response["log_db"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        $log_db = array();
        $log_db["user_name"] = $row["user_name"];
        $log_db["password"] = $row["password"];
        $log_db["profile_id"] = $row["profile_id"];
        array_push($response["log_db"], $log_db);
    }
    $response["success"] = 1;
} else {
    $response["success"] = 0;
    $response["message"] = "No Log Data found";
 }
echo json_encode($response);
?>	