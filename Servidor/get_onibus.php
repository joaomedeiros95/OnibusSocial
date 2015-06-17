<?php
 
/*
 * Following code will get single product details
 * A product is identified by product id (pid)
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/config/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
$row = mysql_query("SELECT * FROM onibus");

// user node
$response["product"] = array();
if (!empty($row)) {
    // check for empty result
    while($result = mysql_fetch_assoc($row)) {
        $product = array();
        $product["id"] = $result["id"];
        $product["linha"] = $result["linha"];
	    // $product["itinerario"] = $result["itinerario"];

	    $empresa = $result["empresa"];
	    $empresa = mysql_query("SELECT * FROM empresa where id = '$empresa'");
	    $empresa = mysql_fetch_array($empresa);
	    $product["id_empresa"] = $empresa["id"];
	    $product["empresa"] = $empresa["nome"];

	    $cidade = $empresa["cidade"];
	    $cidade = mysql_query("SELECT * FROM cidade WHERE id = '$cidade'");
	    $cidade = mysql_fetch_array($cidade);
	    $product["cidade"] = $cidade["nome"];
	    $product["estado"] = $cidade["estado"];

        // success
        $response["success"] = 1;
 
        array_push($response["product"], $product);
    } 
    if(!empty($result)) {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No product found";
 
        // echo no users JSON
        echo json_encode($response);
    } else {
        echo json_encode($response);  
    } 
}
 
?>
