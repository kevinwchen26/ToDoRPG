<?php
$response = array(); // for JSON response
 
if(isset($_POST['quest_title']) && isset($_POST['quest_description']) && isset($_POST['quest_difficulty'])
      && isset($_POST['creator_name']) && isset($_POST['quest_duration']) && isset($_POST['quest_milestone'])
      && isset($_POST['quest_location_lat']) && isset($_POST['quest_location_long']) ) {
    $quest_title = $_POST['quest_title'];
    $quest_description = $_POST['quest_description'];
    $quest_difficulty = $_POST['quest_difficulty'];
    $creator_name = $_POST['creator_name'];
    $quest_duration = $_POST['quest_duration'];
    $quest_milestone = $_POST['quest_milestone'];
    $quest_location_lat = $_POST['quest_location_lat'];
    $quest_location_long = $_POST['quest_location_long'];

    require_once 'db_connect.php';
    $db = new DB_CONNECT();
 
    $result = mysql_query("INSERT INTO quest_db_test(quest_title, quest_description, quest_difficulty, creator_name, 
         quest_duration, quest_milestone, quest_location_lat, quest_location_long) VALUES('$quest_title','$quest_description', 
         '$quest_difficulty', '$creator_name', '$quest_duration', '$quest_milestone', '$quest_location_lat', '$quest_location_long')");
 
    if($result) {
        $response["success"] = 1;
        $response["message"] = "Quest information inserted to database";
    } else {
        $response["success"] = 0;
        $response["message"] = "falied to insert Quest information to database";
    }
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing. Try again";
}
 echo json_encode($response);
?>	