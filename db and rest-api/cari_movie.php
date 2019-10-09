<?php

include "koneksi.php";

	$movie = $_GET['movie'];
	
	$SQL = "SELECT * FROM tb_movie where judul LIKE'%$movie%' ORDER BY kode_movie ASC";
	$eksekusi = mysqli_query($con, $SQL);
	$result = array();
	
	while($row = mysqli_fetch_array($eksekusi)){
		array_push($result, array(
			"kode_movie"=>$row['kode_movie'],
			"judul"=>$row['judul']." (".$row['tahun'].")",
			"foto"=>$row['foto'],
			"trailer"=>$row['trailer']
		));
	}
	
	
	//pasrsing ke json
	echo json_encode(array('data_movie'=>$result));
	
	
	//tutup koneksi
	mysqli_close($con);
?> 