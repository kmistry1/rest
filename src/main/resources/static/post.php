
<?php

if( !empty( $_POST ) ){

$json = json_encode( $_POST );

$username = $_POST['username'];
$password = $_POST['password'];


$data = '{
	"username": $username,
	"password": $password
}';

$character = json_decode($data);
echo $character->name;

}





