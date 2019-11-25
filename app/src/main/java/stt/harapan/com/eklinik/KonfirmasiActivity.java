package stt.harapan.com.eklinik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stt.harapan.com.eklinik.connection.ApiClient;
import stt.harapan.com.eklinik.helper.SessionManager;
import stt.harapan.com.eklinik.model.Jadwal;
import stt.harapan.com.eklinik.model.Pasien;
import stt.harapan.com.eklinik.model.Result;

public class KonfirmasiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi);

        TextView tgl = findViewById(R.id.txtTgl);
        TextView jam = findViewById(R.id.txtJam);
        TextView nama = findViewById(R.id.txtPasien);
        TextView klinik = findViewById(R.id.txtDokter);
        TextView max = findViewById(R.id.txtAntrian);
        TextView antrian = findViewById(R.id.txtAntrianUser);
        ActionProcessButton pesan = findViewById(R.id.btnPesan);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Konfirmasi Pemesanan Anda");

        Intent x = getIntent();

        Jadwal jadwal = x.getParcelableExtra("jadwal");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(jadwal.getJadwal())*1000L);
        calendar.add(Calendar.HOUR,-5);
        jam.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" +calendar.get(Calendar.MINUTE));
        tgl.setText(calendar.get(Calendar.DATE)+ "/" + calendar.get(Calendar.MONTH));
        nama.setText(jadwal.getDokter().getNama());
        klinik.setText(jadwal.getKlinik().getNama());
        max.setText(""+jadwal.getMaks());
        antrian.setText(""+(jadwal.getAntrian()+1));

        SessionManager sessionManager = new SessionManager(this);
        Pasien pasien = (Pasien) sessionManager.getData(1);

        pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiClient.getKlinikInterface().orderBooking(String.valueOf(pasien.getId()),
                        String.valueOf(jadwal.getId()),
                        String.valueOf(jadwal.getAntrian()+1),
                        jadwal.getDokter().getId()).enqueue(new Callback<Result<String>>() {
                    @Override
                    public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                        Result<String> result = response.body();
                        if(!result.getError()){
                            Toast.makeText(KonfirmasiActivity.this,"Pemesanan Berhasil",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(KonfirmasiActivity.this,PasienMainActivity.class));
                            KonfirmasiActivity.this.finish();

                        }
                    }

                    @Override
                    public void onFailure(Call<Result<String>> call, Throwable t) {

                    }
                });
            }
        });

    }
}
