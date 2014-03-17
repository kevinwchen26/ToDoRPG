<?php
$response = array(); // for JSON response
 
if(isset($_POST['user_name']) && isset($_POST['character_name']) && isset($_POST['str'])
             && isset($_POST['con'])&& isset($_POST['dex'])&& isset($_POST['_int'])&& isset($_POST['wis'])
             && isset($_POST['cha']) && isset($_POST['level']) &&isset($_POST['class'])) {
    $user_name = $_POST['user_name'];
    $character_name = $_POST['character_name'];
    $str = $_POST['str'];
    $con = $_POST['con'];
    $dex = $_POST['dex'];
    $_int = $_POST['_int'];
    $wis = $_POST['wis'];
    $cha = $_POST['cha'];
    $level = $_POST['level'];
    $class = $_POST['class'];
 
    require_once 'db_connect.php';
    $db = new DB_CONNECT();
     
    $result = mysql_query("INSERT INTO personal_character_db (user_name, character_name, str, con, dex, _int, wis, cha, level, class)
         VALUES ('$user_name', '$character_name', '$str', '$con', '$dex', '$_int', '$wis', '$cha', '$level', '$class')");

    if($result) {
        $response["success"] = 1;
        $response["message"] = "Character information inserted to database";
    } else {
        $response["success"] = 0;
        $response["message"] = "failed to insert character information to database";
    }
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing. Try again";
}
 echo json_encode($response);
?>		