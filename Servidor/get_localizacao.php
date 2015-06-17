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
 
// check for post data
if (isset($_GET["onibus"])) {
    $onibus = $_GET['onibus'];
 
    // get a product from products table
    $row = mysql_query("SELECT * FROM localizacao WHERE onibus = $onibus");
} else {
 
    $row = mysql_query("SELECT * FROM localizacao");

}

if (!empty($row)) {
    // check for empty result
    // user node
    $response["product"] = array();
    while($result = mysql_fetch_assoc($row)) {
        $product = array();
        $product["id"] = $result["id"];
        $product["localizacao"] = $result["localizacao"];
        $product["ultima_atualizacao"] = $result["ultima_atualizacao"];
	    $product["onibus"] = $result["onibus"];
	   
	  /*$bus = $result["onibus"];
	    $bus = mysql_query("SELECT * FROM onibus where id = '$bus'");
	    $bus = mysql_fetch_array($bus);
	    $product["linha"] = $bus['linha'];

	    $empresa = $bus["empresa"];
	    $empresa = mysql_query("SELECT * FROM empresa WHERE id = '$empresa'");
	    $empresa = mysql_fetch_array($empresa);
	    $product["empresa"] = $empresa['nome'];

	    $cidade = $empresa["cidade"];
	    $cidade = mysql_query("SELECT * FROM cidade WHERE id = '$cidade'");
	    $cidade = mysql_fetch_array($cidade);
	    $product["cidade"] = $cidade['nome'];
	    $product["estado"] = $cidade['estado'];*/

        // success
        $response["success"] = 1;

        if($product["onibus"] != 0 && $product["localizacao"] != 0) 
            array_push($response["product"], $product);
 
    }    
    if(!empty($result)) {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No product found";
 
        // echo no users JSON
        echo json_encode($response);
    } else {
        // echoing JSON response
        echo json_encode($response);
    }
}
 
?>
