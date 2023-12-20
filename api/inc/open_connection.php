<?php

  $HOST = 'localhost';
  $USER = 'root';
  $PASS = '';
  $DB = 'prak_mb';

  $CON = mysqli_connect($HOST, $USER, $PASS, $DB);

  if (!$CON) {

    die("Connection Failed: ".mysqli_connect_error());
    
  }

?>