<?php
 
$response = array();

if (isset($_POST['quest_id'])) {
    $quest_id = $_POST['quest_id'];
     require_once 'db_connect.php';
    $db = new DB_CONNECT();
    $result = mysql_query("DELETE FROM quest_db_test WHERE quest_id= $quest_id");
 
    if (mysql_affected_rows() > 0) {
        $response["success"] = 1;
        $response["message"] = "Quest successfully deleted";      
    } else {
        $response["success"] = 0;
        $response["message"] = "No Quest found";
    }
} else {
    $response["success"] = 0;
    $response["message"] = "Required field is missing";
}

echo json_encode($response);
?>