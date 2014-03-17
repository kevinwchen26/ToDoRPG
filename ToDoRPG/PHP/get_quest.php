<?php

$response = array();
require_once 'db_connect.php';

if(isset($_POST['quest_id']){  
    $quest_id = $_POST['quest_id'];
}
$db = new DB_CONNECT();
$result = mysql_query("SELECT * FROM quest_db_test where quest_id = $quest_id") or die(mysql_error());
 
if (mysql_num_rows($result) > 0) {
    $response["quest_info"] = array();
 
    while ($row = mysql_fetch_array($result)) {
	if(isset($row["quest_location_lat"] && isset("quest_location_long")){
		$quest_db = array();
		$quest_db["quest_id"] = $row["quest_id"];
		$quest_db["quest_title"] = $row["quest_title"];
		$quest_db["quest_description"]=$row["quest_description"];
		$quest_db["quest_location_lat"]=$row["quest_location_lat"];
		$quest_db["quest_location_long"]=$row["quest_location_long"];
                $quest_db["creator_name"]=$row["creator_name"];
                $quest_db["quest_duration"]=$row["quest_duration"];
                $quest_db["milestone"]=$row["milestone"];
		array_push($response["quest_info"], $quest_db);
	}
    }
    $response["success"] = 1;
} else {
    $response["success"] = 0;
    $response["message"] = "No Quest Data found";
 }
}

$result = mysql_query("select character_name from personal_character_db as pc, user_quest_db as uq , log_db as l where l.profile_id = uq.profile_id and uq.quest_id = $quest_id" and pc.user_name=l.user_name) or die(mysql_error());
response['members'] = array()
while($row = mysql_fetch_array($result)){
response['members']= $row['profile_id'];
}

$result = mysql_query("select * from 

echo json_encode($response);
?>
[Enter code here]			