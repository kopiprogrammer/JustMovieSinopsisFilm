<?php

include "koneksi.php";

	$SQL = "SELECT * FROM tb_movie";
	$eksekusi = mysqli_query($con, $SQL);
	$result = array();
	
	while($row = mysqli_fetch_array($eksekusi)){
		array_push($result, array(
			"kode_movie"=>$row['kode_movie'],
			"judul"=>$row['judul'],
			"sinopsis"=>$row['sinopsis'],
			"genre"=>$row['genre'],
			"sutradara"=>$row['sutradara'],
			"bintang_film"=>$row['bintang_film'],
			"produksi"=>$row['produksi'],
			"tgl_rilis"=>$row['tgl_rilis'],
			"negara"=>$row['negara'],
			"durasi"=>$row['durasi'],
			"tahun"=>$row['tahun'],
			"foto"=>$row['foto'],
			"trailer"=>$row['trailer'],
			"rank"=>$row['rank'],
			"status_rank"=>$row['status_rank']
		));
	}
	
	
	//pasrsing ke json
	echo json_encode(array('data_movie'=>$result));
	
	
	//tutup koneksi
	mysqli_close($con);
?> 