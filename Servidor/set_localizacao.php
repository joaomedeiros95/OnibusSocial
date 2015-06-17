<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */

function distance($lat1, $lon1, $lat2, $lon2, $unit) {

  $theta = $lon1 - $lon2;
  $dist = sin(deg2rad($lat1)) * sin(deg2rad($lat2)) +  cos(deg2rad($lat1)) * cos(deg2rad($lat2)) * cos(deg2rad($theta));
  $dist = acos($dist);
  $dist = rad2deg($dist);
  $miles = $dist * 60 * 1.1515;
  $unit = strtoupper($unit);

  if ($unit == "K") {
    return ($miles * 1.609344);
  } else if ($unit == "M") {
      return ($miles * 1.609344 * 1000);
    } else {
        return $miles;
      }
}

// Retorna um boolean caso a distância entre dois ônibus da mesma linha seja menor que 10 metros
function isMesmoOnibus($lat1, $lon1, $lat2, $lon2) {
    if(distance($lat1, $lon1, $lat2, $lon2, "M") < 10.0) {
        echo "Retornou true";
        return true;
    } else {
        echo "Retornou false";
        return false;
    }
}
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['id']) && isset($_POST['onibus']) && isset($_POST['localizacao'])) {
 
    $onibus = $_POST['onibus'];
    $localizacao = $_POST['localizacao'];
    $id = $_POST['id'];
 
    // include db connect class
    require_once __DIR__ . '/config/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();

    // get a product from products table
    $row = mysql_query("SELECT * FROM localizacao WHERE onibus = $onibus");

    // verificando se não já existe uma outra informação desse ônibus, caso exista o programa faz uma média
    // de todas as localizações e envia de volta para o banco somente uma coluna
    $resultado = false;
    if (!empty($row)) {
        while($result = mysql_fetch_assoc($row)) {
            $primeiro = split(':', $result["localizacao"]);
            $segundo = split(':', $localizacao);

            $lat1 = $primeiro[0];
            $lon1 = $primeiro[1];

            $lat2 = $segundo[0];
            $lon2 = $segundo[1];
            $resultado = isMesmoOnibus($lat1, $lon1, $lat2, $lon2);

            if($resultado) {
                $idSubstituir = $result["id"];
                $mediaLat = ($lat1 + $lat2) / 2;
                $mediaLong = ($lon1 + $lon2) / 2;
                $mediaLocalizacao = $mediaLat . ":" . $mediaLong;
            }
        }
    }
    // mysql inserting a new row
    if(!$resultado) {
        echo "Não fez a média";
        $retornado = mysql_query("REPLACE INTO localizacao(id, onibus, localizacao) VALUES('$id', '$onibus', '$localizacao')");
    } else {
        echo "Fez a média";
        $retornado = mysql_query("REPLACE INTO localizacao(id, onibus, localizacao) VALUES('$idSubstituir', '$onibus', '$mediaLocalizacao'");
    }
 
    // check if row inserted or not
    if ($retornado) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Product successfully created.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
 
        // echoing JSON response
        echo json_encode($response);
    } 
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}
?>
