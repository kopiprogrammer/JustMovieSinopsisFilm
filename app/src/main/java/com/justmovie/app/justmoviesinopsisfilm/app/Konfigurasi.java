package com.justmovie.app.justmoviesinopsisfilm.app;

public class Konfigurasi
{

    //google console id
    public static final String YOUTUBE_API_KEY = "change your key youtube api";

    //youtube video id
    public static final String YOUTUBE_ID = "change your key youtube ID";

    //web api
    public static final String URL = "http://api.kopiprogrammer.com/justmovie/";

    public static final String TAG_JSON_ARRAY="data_movie";

    //field
    public static final String TAG_KODE_MOVIE = "kode_movie";
    public static final String TAG_JUDUL = "judul";
    public static final String TAG_SINOPSIS = "sinopsis";
    public static final String TAG_GENRE = "genre";
    public static final String TAG_SUTRADARA = "sutradara";
    public static final String TAG_BINTANG_FILM = "bintang_film";
    public static final String TAG_PRODUKSI = "produksi";
    public static final String TAG_TGL_RILIS = "tgl_rilis";
    public static final String TAG_NEGARA = "negara";
    public static final String TAG_DURASI = "durasi";
    public static final String TAG_FOTO = "foto";
    public static final String TAG_TRAILER = "trailer";
    public static final String TAG_TAHUN = "tahun";

    //genre
    public static final String TAG_KODE_GENRE = "kode_genre";
    public static final String TAG_ICON = "icon";
    public static final String TAG_TOTAL = "total";
    public static final String TAG_JUMLAH = "jumlah";

    public static final String KUNCI_KODE_MOVIE = "kunci_kode_movie";
    public static final String KODE_MOVIE = "kode_movie";
    public static final String KUNCI_TRAILER = "trailer";
    public static final String KUNCI_GENRE = "genre";
    public static final String KUNCI_NEGARA = "negara";
    public static final String KUNCI_TAHUN = "tahun";

    public static final String URL_MOVIE_TERBARU = URL+"tampil_data_movie.php";
    public static final String URL_MOVIE_DETAIL = URL+"tampil_data_movie_detail.php?kode_movie=";
    public static final String URL_MOVIE_GENRE_LIST = URL+"genre_list.php";
    public static final String URL_MOVIE_GENRE_LIST_CARI = URL+"tampil_data_movie_genre.php?genre=";
    public static final String URL_MOVIE_TOP_RATING = URL+"tampil_data_movie_rank.php";
    public static final String URL_MOVIE_CARI = URL+"cari_movie.php?movie=";
    public static final String URL_MOVIE_NEGARA_LIST = URL+"negara_list.php";
    public static final String URL_MOVIE_TAHUN_LIST = URL+"tahun_list.php";



}
