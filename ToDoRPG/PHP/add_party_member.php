<?php

$response = array();
require_once 'db_connect.php';
$db = new DB_CONNECT();

if (isset($_POST['quest_id']) && isset($_POST['profile_id'])) {
	$return = array();

	$profile_id = $_POST['profile_id'];
	$quest_id = $_POST['quest_id'];
	$result = mysql_query("select * from user_quest_db where profile_id = $profile_id and quest_id = $quest_id");
	if (mysql_num_rows($result) > 0) {
		$return["success"] = "You are already a member of this quest";
		$return["result"]=mysql_fetch_row($result);
	} else {

		$result = mysql_query("insert into user_quest_db (quest_id,profile_id) values ('$quest_id','$profile_id')") or die(mysql_error());
		if ($result > 0) {
			$return["success"] = "Quest Joined";
		}
	}
	echo json_encode($return);
}
?>
