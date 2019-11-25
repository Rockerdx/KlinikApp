package stt.harapan.com.eklinik.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stt.harapan.com.eklinik.R;
import stt.harapan.com.eklinik.adapter.JadwalAdapter;
import stt.harapan.com.eklinik.connection.ApiClient;
import stt.harapan.com.eklinik.helper.SessionManager;
import stt.harapan.com.eklinik.model.Dokter;
import stt.harapan.com.eklinik.model.Jadwal;
import stt.harapan.com.eklinik.model.Result;

public class UpcomingDokter extends Fragment {


    RecyclerView rv_dokter;
    JadwalAdapter adapter;
    Context context;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming_dokter, container, false);
        rv_dokter = view.findViewById(R.id.rv_dokter);

        SessionManager sessionManager = new SessionManager(getActivity());

        Dokter dokter = (Dokter) sessionManager.getData(2);

        ApiClient.getKlinikInterface().getAllJadwalToday(dokter.getId(),"upcoming").enqueue(new Callback<Result<List<Jadwal>>>() {
            @Override
            public void onResponse(Call<Result<List<Jadwal>>> call, Response<Result<List<Jadwal>>> response) {
                Result<List<Jadwal>> result = response.body();
                if(!result.getError()) {
                    Log.d("tes", "panjang result" + result.getData().size());
                    adapter = new JadwalAdapter(context,result.getData());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_dokter.getContext(),
                            linearLayoutManager.getOrientation());
                    rv_dokter.addItemDecoration(dividerItemDecoration);
                    rv_dokter.setLayoutManager(linearLayoutManager);
                    rv_dokter.setAdapter(adapter);
                    Log.d("tes", "panjang result" + result.getData().size());
                }
                else {
                    Toast.makeText(context,"Jadwal Kosong",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result<List<Jadwal>>> call, Throwable t) {

            }
        });









        return view;
    }

}
