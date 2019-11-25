package rizky.putra.com.eklinik.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dokter implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("hp")
    @Expose
    private String hp;
    @SerializedName("spesialis")
    @Expose
    private Integer spesialis;
    @SerializedName("foto")
    @Expose
    private String foto;

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public Integer getSpesialis() {
        return spesialis;
    }

    public void setSpesialis(Integer spesialis) {
        this.spesialis = spesialis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nama);
        dest.writeString(this.email);
        dest.writeString(this.hp);
        dest.writeValue(this.spesialis);
        dest.writeString(this.foto);
    }

    public Dokter() {
    }

    protected Dokter(Parcel in) {
        this.id = in.readString();
        this.nama = in.readString();
        this.email = in.readString();
        this.hp = in.readString();
        this.spesialis = (Integer) in.readValue(Integer.class.getClassLoader());
        this.foto = in.readString();
    }

    public static final Parcelable.Creator<Dokter> CREATOR = new Parcelable.Creator<Dokter>() {
        @Override
        public Dokter createFromParcel(Parcel source) {
            return new Dokter(source);
        }

        @Override
        public Dokter[] newArray(int size) {
            return new Dokter[size];
        }
    };
}
