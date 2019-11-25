package stt.harapan.com.eklinik.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Jadwal implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("jadwal")
    @Expose
    private String jadwal;
    @SerializedName("maks")
    @Expose
    private Integer maks;
    @SerializedName("antrian")
    @Expose
    private Integer antrian;
    @SerializedName("dokter")
    @Expose
    private Dokter dokter;
    @SerializedName("klinik")
    @Expose
    private Klinik klinik;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJadwal() {
        return jadwal;
    }

    public void setJadwal(String jadwal) {
        this.jadwal = jadwal;
    }

    public Integer getMaks() {
        return maks;
    }

    public void setMaks(Integer maks) {
        this.maks = maks;
    }

    public Integer getAntrian() {
        return antrian;
    }

    public void setAntrian(Integer antrian) {
        this.antrian = antrian;
    }

    public Dokter getDokter() {
        return dokter;
    }

    public void setDokter(Dokter dokter) {
        this.dokter = dokter;
    }

    public Klinik getKlinik() {
        return klinik;
    }

    public void setKlinik(Klinik klinik) {
        this.klinik = klinik;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.jadwal);
        dest.writeValue(this.maks);
        dest.writeValue(this.antrian);
        dest.writeParcelable(this.dokter, flags);
        dest.writeParcelable(this.klinik, flags);
    }

    public Jadwal() {
    }

    protected Jadwal(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.jadwal = in.readString();
        this.maks = (Integer) in.readValue(Integer.class.getClassLoader());
        this.antrian = (Integer) in.readValue(Integer.class.getClassLoader());
        this.dokter = in.readParcelable(Dokter.class.getClassLoader());
        this.klinik = in.readParcelable(Klinik.class.getClassLoader());
    }

    public static final Parcelable.Creator<Jadwal> CREATOR = new Parcelable.Creator<Jadwal>() {
        @Override
        public Jadwal createFromParcel(Parcel source) {
            return new Jadwal(source);
        }

        @Override
        public Jadwal[] newArray(int size) {
            return new Jadwal[size];
        }
    };
}
