<?php
 
$response = array();
require_once 'db_connect.php';
$db = new DB_CONNECT();
 
if (isset($_GET['quest_member']) && isset($_GET['quest_id'])) {
    $member = $_GET['quest_member'];
    $quest_id = $_GET['quest_id'];
    $result = mysql_query("UPDATE quest_db_test SET quest_member='$member' WHERE quest_id = $quest_id");
 
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