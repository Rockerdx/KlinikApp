package stt.harapan.com.eklinik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stt.harapan.com.eklinik.adapter.JadwalAdapter;
import stt.harapan.com.eklinik.adapter.JadwalOrderAdapter;
import stt.harapan.com.eklinik.connection.ApiClient;
import stt.harapan.com.eklinik.model.Jadwal;
import stt.harapan.com.eklinik.model.Result;

public class OrderDokterActivity extends AppCompatActivity {

    JadwalOrderAdapter adapter;
    RecyclerView rv_dokter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_dokter);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        rv_dokter = findViewById(R.id.rv_dokter);
        Intent x = getIntent();

        String id = x.getStringExtra("nip");
        String nama = x.getStringExtra("nama");
        getSupportActionBar().setTitle("Jadwal " +nama);


        ApiClient.getKlinikInterface().getAllJadwalToday(id,"all").enqueue(new Callback<Result<List<Jadwal>>>() {
            @Override
            public void onResponse(Call<Result<List<Jadwal>>> call, Response<Result<List<Jadwal>>> response) {
                Result<List<Jadwal>> result = response.body();
                if(!result.getError()) {
                    adapter = new JadwalOrderAdapter(OrderDokterActivity.this,result.getData());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderDokterActivity.this);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_dokter.getContext(),
                            linearLayoutManager.getOrientation());
                    rv_dokter.addItemDecoration(dividerItemDecoration);
                    rv_dokter.setLayoutManager(linearLayoutManager);
                    rv_dokter.setAdapter(adapter);
                    Log.d("tes", "panjang result" + result.getData().size());
                }
                else {
                    Toast.makeText(OrderDokterActivity.this,"Jadwal Kosong",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result<List<Jadwal>>> call, Throwable t) {

            }
        });

    }
}
