package stt.harapan.com.eklinik.connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import stt.harapan.com.eklinik.BuildConfig;
import stt.harapan.com.eklinik.model.Dokter;
import stt.harapan.com.eklinik.model.Jadwal;
import stt.harapan.com.eklinik.model.Klinik;
import stt.harapan.com.eklinik.model.Order;
import stt.harapan.com.eklinik.model.Pasien;
import stt.harapan.com.eklinik.model.Result;

/**
 * Created by donikhun on 11/01/18.
 */

public class ApiClient {

    private static final String server = "http://rockerdx.xyz/admin/api/ ";
    private static KlinikInterface klinikInterface;

    public static KlinikInterface getKlinikInterface() {
        if (klinikInterface == null) {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(2, TimeUnit.MINUTES);
            httpClientBuilder.readTimeout(2, TimeUnit.MINUTES);
            httpClientBuilder.writeTimeout(2, TimeUnit.MINUTES);

            if (BuildConfig.DEBUG) {
                // enable logging for debug builds
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClientBuilder.addInterceptor(loggingInterceptor);
            }
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(server)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .callFactory(httpClientBuilder.build())
                    .build();
            klinikInterface = retrofit.create(KlinikInterface.class);
        }
        return klinikInterface;
    }

    public interface KlinikInterface {

        @GET("jadwal.php")
        Call<Result<List<Jadwal>>> getAllJadwalToday(@Query("id") String nip,@Query("type") String type);

        @FormUrlEncoded
        @POST("login.php")
        Call<Result<Dokter>> loginWithEmailPass(@Field("email") String email,
                                                @Field("password") String password,
                                                @Field("type") String type);
        @FormUrlEncoded
        @POST("login.php")
        Call<Result<Pasien>> loginPasienWithEmailPass(@Field("email") String email,
                                                      @Field("password") String password,
                                                      @Field("type") String type);
        @FormUrlEncoded
        @POST("register.php")
        Call<Result<String>> registerDokter(@Field("nama") String nama,
                                            @Field("nip") String nip,
                                            @Field("hp") String hp,
                                            @Field("email") String email,
                                            @Field("spesialis") String spesialis,
                                            @Field("foto") String foto,
                                            @Field("password") String password,
                                            @Field("type") String type);
        @FormUrlEncoded
        @POST("register.php")
        Call<Result<String>> registerPasien(@Field("nama") String nama,
                                            @Field("email") String email,
                                            @Field("kelamin") String kelamin,
                                            @Field("umur") String umur,
                                            @Field("password") String password,
                                            @Field("type") String type);

        @GET("history.php")
        Call<Result<List<Order>>> getAllHistoryDokter(@Query("id") String nip, @Query("type") String type);

        @GET("klinik.php")
        Call<Result<List<Klinik>>> getAllKlinik();

        @GET("dokter.php")
        Call<Result<List<Dokter>>> getAllDokter();

        @GET("jadwal.php")
        Call<Result<List<Jadwal>>> getJadwalByKlinik(@Query("klinik") String id,@Query("dokter") String nip,@Query("waktu") String waktu,@Query("spesialis") String spesialis);

        @FormUrlEncoded
        @POST("order.php")
        Call<Result<String>> orderBooking(@Field("id") String id,
                                          @Field("id_jadwal") String id_jadwal,
                                          @Field("antrian") String antrian,
                                          @Field("nip") String nip);

        @Multipart
        @POST("upload.php")
        Call<Result<String>> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    }
}
