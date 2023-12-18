<?php
  require_once('inc/open_connection.php');
  
  $id = $_POST['notes_id'];
  $title = $_POST['title'];
  $content = $_POST['content'];
 

  $query = "UPDATE notes SET 
  title = '$title',
  content = '$content'
  
  WHERE id = '$id';";

  $query_run = mysqli_query($CON, $query);

  if ($query_run) {
        echo json_encode([
        "error" => false,
        "message" => 'Notes has been successfully updated!'
        ]);
    } else {
        echo json_encode([
        "error" => true,
        "message" => 'Notes failed to be updated!'
        ]);
    }

  require_once('inc/close_connection.php');
?>