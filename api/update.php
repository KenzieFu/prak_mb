<?php
  require_once('inc/open_connection.php');
  
  $id = $_POST['notesId'];
  $title = $_POST['title'];
  $content = $_POST['content'];
  $date = $_POST['date'];

  $query = "UPDATE notes SET 
  title = '$title',
  content = '$content',
  date = '$date'
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