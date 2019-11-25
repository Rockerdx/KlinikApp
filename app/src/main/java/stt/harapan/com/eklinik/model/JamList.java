package stt.harapan.com.eklinik.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JamList {

    @SerializedName("data")
    @Expose
    private List<Jam> data = null;

    public List<Jam> getData() {
        return data;
    }

    public void setData(List<Jam> data) {
        this.data = data;
    }

}