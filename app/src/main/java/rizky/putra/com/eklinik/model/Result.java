package rizky.putra.com.eklinik.model;

public class Result<T> {
    private Boolean error;
    private T data;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
