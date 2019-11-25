package rizky.putra.com.eklinik.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Jam {

    @SerializedName("hari")
    @Expose
    private String hari;
    @SerializedName("waktu")
    @Expose
    private String waktu;

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

}