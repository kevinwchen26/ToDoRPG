<?php

$response = array();
require_once 'db_connect.php';
$db = new DB_CONNECT();
 
$result = mysql_query("SELECT *FROM quest_db_test ORDER BY quest_id DESC") or die(mysql_error());
 
if (mysql_num_rows($result) > 0) {
    $response["rows"] = array();
 
    while ($row = mysql_fetch_assoc($result)) {
	array_push($response["rows"], $row);
    }
    $response["success"] = 1;
} else {
    $response["success"] = 0;
    $response["message"] = "No Quest Data found";
 }
echo json_encode($response);
?>
