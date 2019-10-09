<?php
	include "koneksi.php";
	
	$sqlcarikode = mysqli_query($con, "SELECT max(kode_movie) as maxKode FROM tb_movie") or die(mysql_error());
	$datacari = mysqli_fetch_array($sqlcarikode);
	$kodeotomatis = $datacari['maxKode']+1;

if($_SERVER['REQUEST_METHOD']=='POST'){
	
	$kode_movie 	= $kodeotomatis;
	$judul			= $_POST['judul'];
	$sinopsis		= $_POST['sinopsis'];
	$genre			= $_POST['genre'];
	$sutradara		= $_POST['sutradara'];
	$bintang_film 	= $_POST['bintang_film'];
	$produksi		= $_POST['produksi'];
	$tgl_rilis		= $_POST['tgl_rilis'];
	$negara			= $_POST['negara'];
	$durasi			= $_POST['durasi'];
	$tahun			= $_POST['tahun'];
	$foto			= $_POST['foto'];
	$trailer		= $_POST['trailer'];
	$rank			= "0";
	$status_rank 	= "0";
	
	
	$SQL = "INSERT INTO `tb_movie` (`kode_movie`, `judul`, `sinopsis`, `genre`, `sutradara`, `bintang_film`, `produksi`, `tgl_rilis`, `negara`, `durasi`, `tahun`, `foto`, `trailer`, `rank`, `status_rank`) VALUES ('$kode_movie', '$judul', '$sinopsis', '$genre', '$sutradara', '$bintang_film', '$produksi', '$tgl_rilis', '$negara', '$durasi', '$tahun', '$foto', '$trailer', '$rank', '$status_rank');";
	
	
    	if(mysqli_query($con, $SQL)){
			header('Location: http://api.kopiprogrammer.com/justmovie');
			
		    }
		    else{
    		echo"Gagal Input Data";
		    }

}
?>