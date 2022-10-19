 package com.ramadoni.spartpartkomputer.server;

public class BaseURL {

    public static String baseUrl = "http://192.168.1.10:500/";

    public static String login = baseUrl + "user/login";
    public static String register = baseUrl + "user/registrasi";

    //spartpart
    public static  String dataSpartpart = baseUrl + "Komputer/dataspartpart";
    public static  String editDataSpartpart = baseUrl + "Komputer/ubah/";
    public static  String hapusData = baseUrl + "Komputer/hapus/";
    public static  String InputSpartpart = baseUrl + "Komputer/input/";


    public static String order = baseUrl + "order/insert";
    public static String dataOrder = baseUrl + "order/lihatTransaksi/";
    public static String dataOrderbyname = baseUrl + "order/dataTransaksi/";
}

