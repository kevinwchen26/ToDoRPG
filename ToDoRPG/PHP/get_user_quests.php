<?php
 
$response = array();
require_once 'db_connect.php';
$db = new DB_CONNECT();
 
if (isset($_GET['userName'])) {
    $userName = $_GET['userName'];
    $result = mysql_query("SELECT * FROM quest_db_test WHERE creator_name = '$userName' ORDER BY quest_id DESC");
 
    if (!empty($result)) {
        if (mysql_num_rows($result) > 0) {
            $response["success"] = 1;
            $response['rows'] = array();
            while ($row = mysql_fetch_assoc($result)) {
                array_push($response['rows'], $row);
            }
        } else {

            $response["success"] = 0;
            $response["message"] = "No quest found";
        }
    } else {
        $response["success"] = 0;
        $response["message"] = "Query result null";
    }
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) 'userName' is missing";
}
echo json_encode($response);
?>