<?php
 
$response = array();
require_once 'db_connect.php';
$db = new DB_CONNECT();
 
if (isset($_GET['status']) && isset($_GET['quest_id'])) {
    $status = $_GET['status'];
    $quest_id = $_GET['quest_id'];
    $result = mysql_query("UPDATE quest_db_test SET quest_status='$status' WHERE quest_id = $quest_id");
 
    if ($result) {
            $response["success"] = 1;
            $response["message"] = "Successful update";
    } else {
        $response["success"] = 0;
        $response["message"] = "Unable to update";
    }
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
}
echo json_encode($response);
?>	