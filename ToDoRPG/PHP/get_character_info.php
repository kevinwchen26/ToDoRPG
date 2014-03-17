<?php
 
$response = array();
require_once 'db_connect.php';
$db = new DB_CONNECT();
 
if (isset($_GET['user_name'])) {
    $user_name = $_GET['user_name'];
    $result = mysql_query("SELECT * FROM personal_character_db WHERE user_name = '$user_name'");
 
    if (!empty($result)) {
        if (mysql_num_rows($result) > 0) {
            $result = mysql_fetch_array($result);
            $character_info = array();

            $character_info['character_name'] = $result['character_name'];
            $character_info['str'] = $result['str'];
            $character_info['con'] = $result['con'];
            $character_info['dex'] = $result['dex'];
            $character_info['_int'] = $result['_int'];
            $character_info['wis'] = $result['wis'];
            $character_info['cha'] = $result['cha'];
            $character_info['level'] = $result['level'];
            $character_info['class'] = $result['class'];
            
            $response["success"] = 1;
            $response['character'] = array();
            array_push($response['character'], $character_info);
        } else {
            
            $response["success"] = 0;
            $response["message"] = "No Character Info found";
        }
    } else {
        $response["success"] = 0;
        $response["message"] = "No Character Info found";
    }
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
}
echo json_encode($response);
?>