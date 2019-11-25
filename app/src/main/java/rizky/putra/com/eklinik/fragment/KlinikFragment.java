package rizky.putra.com.eklinik.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rizky.putra.com.eklinik.R;
import rizky.putra.com.eklinik.adapter.KlinikAdapter;
import rizky.putra.com.eklinik.connection.ApiClient;
import rizky.putra.com.eklinik.model.Klinik;
import rizky.putra.com.eklinik.model.Result;

public class KlinikFragment extends Fragment implements SearchView.OnQueryTextListener {

    RecyclerView rv_klinik;
    KlinikAdapter adapter;
    List<Klinik> klinikList = new ArrayList<>();
    Context context;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_klinik, container, false);

        rv_klinik = view.findViewById(R.id.rv_klinik);

        adapter = new KlinikAdapter(context,klinikList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_klinik.getContext(),
                linearLayoutManager.getOrientation());
        rv_klinik.addItemDecoration(dividerItemDecoration);
        rv_klinik.setLayoutManager(linearLayoutManager);
        rv_klinik.setAdapter(adapter);

        ApiClient.getKlinikInterface().getAllKlinik().enqueue(new Callback<Result<List<Klinik>>>() {
            @Override
            public void onResponse(Call<Result<List<Klinik>>> call, Response<Result<List<Klinik>>> response) {
                Result<List<Klinik>> result = response.body();
                if(!result.getError()) {
                    klinikList.clear();
                    klinikList.addAll(result.getData());
                    adapter.notifyDataSetChanged();
                    Log.d("tes", "panjang result" + result.getData().size());
                }
                else {
                    Toast.makeText(context,"Jadwal Kosong",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result<List<Klinik>>> call, Throwable t) {

            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final android.support.v7.widget.SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.getFilter().filter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
}
