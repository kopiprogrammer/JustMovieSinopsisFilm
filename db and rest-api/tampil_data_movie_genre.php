<?php

include "koneksi.php";
	
	$genre = $_GET['genre'];
	
	$SQL = "SELECT * FROM tb_movie WHERE genre LIKE'%$genre' OR negara LIKE'%$genre' OR tahun LIKE'%$genre' ORDER BY kode_movie ASC";
	$eksekusi = mysqli_query($con, $SQL);
	$result = array();
	
	while($row = mysqli_fetch_array($eksekusi)){
		array_push($result, array(
			"kode_movie"=>$row['kode_movie'],
			"judul"=>$row['judul']." (".$row['tahun'].")",
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
			"trailer"=>$row['trailer']
		));
	}
	
	
	//pasrsing ke json
	echo json_encode(array('data_movie'=>$result));
	
	
	//tutup koneksi
	mysqli_close($con);
?> 