<?php
  require_once('inc/open_connection.php');
  $title =$_POST['title'];
  $content =$_POST['content'];
  $date = $_POST['date'];

  $query = "INSERT INTO notes (title, content, date)
            VALUES('$title','$content','$date')";
  
  $query_run = mysqli_query($CON,$query);

  if($query_run){
    echo json_encode([
      "error" => false,
      "message" => 'Notes has been successfully added!'
    ]);
  }else{echo json_encode([
    "error" => true,
    "message" => 'Notes failed to be added!'
  ]);
    
  }
  require_once('inc/close_connection.php')
  ?>
