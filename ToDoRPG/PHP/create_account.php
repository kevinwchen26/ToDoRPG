<?php
$response = array(); // for JSON response

if(isset($_POST['user_name']) && isset($_POST['password'])) {
    $user_name = $_POST['user_name'];
    $password = $_POST['password'];
    
    require_once 'db_connect.php';
    $db = new DB_CONNECT();
   
    $result = mysql_query("INSERT INTO log_db(user_name, password) VALUES('$user_name','$password')");

    if($result) {
        $response["success"] = 1;
        $response["message"] = "Account information inserted to database";
    } else {
        $response["success"] = 0;
        $response["message"] = "falied to insert account information to database";
    }
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing. Try again";
}
 echo json_encode($response);
?>	