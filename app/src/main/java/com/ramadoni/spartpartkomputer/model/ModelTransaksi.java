package com.ramadoni.spartpartkomputer.model;

public class ModelTransaksi {

    String _id, username, namaSpartpart, spartpartLaptop, Harga, Jumlah, Total;

    //id
    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }

    //USERNAME
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    //NAMASPARTPART
    public String getNamaSpartpart() { return namaSpartpart; }
    public void setNamaSpartpart(String namaSpartpart) { this.namaSpartpart = namaSpartpart; }

    //NAMALAPTOP
    public String getSpartpartLaptop() { return spartpartLaptop; }
    public void setSpartpartLaptop(String spartpartLaptop) { this.spartpartLaptop = spartpartLaptop; }

    //HARGA
    public String getHarga() { return Harga; }
    public void setHarga(String harga) { Harga = harga; }

    //JUMLAH
    public String getJumlah() { return Jumlah; }
    public void setJumlah(String jumlah) { Jumlah = jumlah; }


    //TOTAL
    public String getTotal() { return Total; }
    public void setTotal(String total) { Total = total; }
}

