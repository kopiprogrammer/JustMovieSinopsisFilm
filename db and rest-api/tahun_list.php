<?php

include "koneksi.php";

	$SQL = "SELECT COUNT(tahun) as jumlah, tahun FROM tb_movie GROUP BY tahun ORDER BY tahun ASC";
	$eksekusi = mysqli_query($con, $SQL);
	$result = array();
	
	while($row = mysqli_fetch_array($eksekusi)){
		array_push($result, array(
			"jumlah"=>$row['jumlah'],
			"tahun"=>$row['tahun']
		));
	}
	
	
	//pasrsing ke json
	echo json_encode(array('data_movie'=>$result));
	
	
	//tutup koneksi
	mysqli_close($con);
?> 