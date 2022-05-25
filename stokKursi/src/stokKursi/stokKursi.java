
package stokKursi;

public class stokKursi {
    private int id;
    private String nama_kursi;
    private String warna;
    private String tanggal;
    private String harga;

    public stokKursi(int id, String nama_kursi, String warna, String tanggal, String harga) {
        this.id = id;
        this.nama_kursi = nama_kursi;
        this.warna = warna;
        this.tanggal = tanggal;
        this.harga = harga;
    }

    public int getId() {
        return id;
    }

    public String getNama_kursi() {
        return nama_kursi;
    }

    public String getWarna() {
        return warna;
    }

    public String getTanggal() {
        return tanggal;
    } 

    public String getHarga() {
        return harga;
    }
    
}
