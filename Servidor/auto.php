<?php 

	// include db connect class
	require_once __DIR__ . '/config/db_connect.php';
	 
	// connecting to db
	$db = new DB_CONNECT();
	
	$id1 = 14;
	$id2 = 11;
	$id3 = 12;
	$id4 = 13;

	$onibus1 = 1;
	$onibus2 = 2;
	$onibus3 = 3;
	$onibus4 = 1;

	$localizacao1 = "-5.832992:-35.204438";
	$localizacao2 = "-5.836323:-35.204395";
	$localizacao3 = "-5.839951:-35.208472";
	$localizacao4 = "-5.841446:-35.211347";

	$retornado1 = mysql_query("REPLACE INTO localizacao(id, onibus, localizacao) VALUES('$id1', '$onibus1', '$localizacao1')");
	$retornado2 = mysql_query("REPLACE INTO localizacao(id, onibus, localizacao) VALUES('$id2', '$onibus2', '$localizacao2')");
	$retornado3 = mysql_query("REPLACE INTO localizacao(id, onibus, localizacao) VALUES('$id3', '$onibus3', '$localizacao3')");
	$retornado4 = mysql_query("REPLACE INTO localizacao(id, onibus, localizacao) VALUES('$id4', '$onibus4', '$localizacao4')");

	if($retornado1 && $retornado2 && $retornado3 && $retornado4) 
		echo "DONE";
	else
		echo "AN ERROR APPERS";

?>

