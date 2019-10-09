<?php

include "koneksi.php";

	$SQL = "SELECT COUNT(negara) as jumlah, negara FROM tb_movie GROUP BY negara ORDER BY negara ASC";
	$eksekusi = mysqli_query($con, $SQL);
	$result = array();
	
	while($row = mysqli_fetch_array($eksekusi)){
		array_push($result, array(
			"jumlah"=>$row['jumlah'],
			"negara"=>$row['negara']
		));
	}
	
	
	//pasrsing ke json
	echo json_encode(array('data_movie'=>$result));
	
	
	//tutup koneksi
	mysqli_close($con);
?> 