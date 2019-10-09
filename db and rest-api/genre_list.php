<?php

include "koneksi.php";

	$SQL = "SELECT COUNT(genre) as jumlah, genre FROM tb_movie GROUP BY genre ORDER BY genre ASC";
	$eksekusi = mysqli_query($con, $SQL);
	$result = array();
	
	while($row = mysqli_fetch_array($eksekusi)){
		array_push($result, array(
			"jumlah"=>$row['jumlah'],
			"genre"=>$row['genre']
		));
	}
	
	
	//pasrsing ke json
	echo json_encode(array('data_movie'=>$result));
	
	
	//tutup koneksi
	mysqli_close($con);
?> 