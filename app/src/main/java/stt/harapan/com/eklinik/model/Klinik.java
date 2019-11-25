package stt.harapan.com.eklinik.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Klinik implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("hp")
    @Expose
    private String hp;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("jam_buka")
    @Expose
    private String jam_buka;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJam_buka() {
        return jam_buka;
    }

    public void setJam_buka(String jam_buka) {
        this.jam_buka = jam_buka;
    }

    public Klinik() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.alamat);
        dest.writeString(this.hp);
        dest.writeString(this.email);
        dest.writeString(this.lat);
        dest.writeString(this.lng);
        dest.writeString(this.jam_buka);
    }

    protected Klinik(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nama = in.readString();
        this.alamat = in.readString();
        this.hp = in.readString();
        this.email = in.readString();
        this.lat = in.readString();
        this.lng = in.readString();
        this.jam_buka = in.readString();
    }

    public static final Creator<Klinik> CREATOR = new Creator<Klinik>() {
        @Override
        public Klinik createFromParcel(Parcel source) {
            return new Klinik(source);
        }

        @Override
        public Klinik[] newArray(int size) {
            return new Klinik[size];
        }
    };
}