package stt.harapan.com.eklinik;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stt.harapan.com.eklinik.adapter.JadwalKlinikAdapter;
import stt.harapan.com.eklinik.adapter.JamAdapter;
import stt.harapan.com.eklinik.connection.ApiClient;
import stt.harapan.com.eklinik.model.Jadwal;
import stt.harapan.com.eklinik.model.Jam;
import stt.harapan.com.eklinik.model.JamList;
import stt.harapan.com.eklinik.model.Klinik;
import stt.harapan.com.eklinik.model.Result;

public class KlinikOrderActivity extends AppCompatActivity {

    RecyclerView rv_jadwal,rv_dialog;
    EditText spwaktu;
    Klinik klinik;
    String waktu,spesialis,dokter;
    Spinner sp_spesialis;
    JadwalKlinikAdapter adapter;
    List<Jadwal> list;
    TextView jamBuka;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    JamList jamList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klinik_order);

        spwaktu = findViewById(R.id.sp_waktu);
        sp_spesialis = findViewById(R.id.sp_spesialis);
        rv_jadwal = findViewById(R.id.rv_jadwal);
        jamBuka = findViewById(R.id.txtBuka);
        Calendar calendar = Calendar.getInstance();
        waktu = "";
        spesialis = "";
        dokter = "";

        list = new ArrayList<>();
        adapter = new JadwalKlinikAdapter(KlinikOrderActivity.this,list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(KlinikOrderActivity.this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_jadwal.getContext(),
                linearLayoutManager.getOrientation());
        rv_jadwal.addItemDecoration(dividerItemDecoration);
        rv_jadwal.setLayoutManager(linearLayoutManager);
        rv_jadwal.setAdapter(adapter);
        spwaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog mDate = new DatePickerDialog(KlinikOrderActivity.this, myDateListener,calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                mDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDate.show();
            }
        });

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent x = getIntent();

        klinik = x.getParcelableExtra("klinik");
        getSupportActionBar().setTitle("Jadwal " + klinik.getNama());

        requestData();

        Gson gson = new Gson();

        jamList = gson.fromJson(klinik.getJam_buka(),JamList.class);
        jamBuka.setText("Lihat Jadwal "+klinik.getNama());
        jamBuka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogForm();
            }
        });

        sp_spesialis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spesialis= String.valueOf(position);
                requestData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            spwaktu.setText(day + "/" + (month+1) + "/" + year);
            waktu = year+"-"+(month+1)+"-"+day;
            requestData();
        }
    };

    private void DialogForm() {
        dialog = new AlertDialog.Builder(KlinikOrderActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_jadwal, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Jadwal Klinik");

        rv_dialog    = dialogView.findViewById(R.id.rv_jadwal);

        JamAdapter adapter = new JamAdapter(KlinikOrderActivity.this,jamList.getData());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(KlinikOrderActivity.this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_dialog.getContext(),
                linearLayoutManager.getOrientation());
        rv_dialog.addItemDecoration(dividerItemDecoration);
        rv_dialog.setLayoutManager(linearLayoutManager);
        rv_dialog.setAdapter(adapter);


        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void requestData(){
        list.clear();
        ApiClient.getKlinikInterface().getJadwalByKlinik(String.valueOf(klinik.getId()),dokter,waktu,spesialis).enqueue(new Callback<Result<List<Jadwal>>>() {
            @Override
            public void onResponse(Call<Result<List<Jadwal>>> call, Response<Result<List<Jadwal>>> response) {
                Result<List<Jadwal>> result = response.body();
                if(!result.getError()) {
                    list.clear();
                    list.addAll(result.getData());
                    adapter.notifyDataSetChanged();
                    Log.d("tes", "panjang result" + result.getData().size());
                    Log.d("tes", "panjang list" + list.size());
                }
                else {
                    adapter.notifyDataSetChanged();
                    Toast.makeText(KlinikOrderActivity.this,"Jadwal Kosong",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result<List<Jadwal>>> call, Throwable t) {
                adapter.notifyDataSetChanged();
            }
        });
    }
}
