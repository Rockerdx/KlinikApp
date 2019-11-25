package rizky.putra.com.eklinik.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rizky.putra.com.eklinik.R;
import rizky.putra.com.eklinik.adapter.DokterAdapter;
import rizky.putra.com.eklinik.connection.ApiClient;
import rizky.putra.com.eklinik.model.Dokter;
import rizky.putra.com.eklinik.model.Result;

public class DokterFragment extends Fragment implements SearchView.OnQueryTextListener {

    RecyclerView rv_dokter;
    DokterAdapter adapter;
    ArrayList<Dokter> dokterList = new ArrayList<>();
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
        View view = inflater.inflate(R.layout.fragment_dokter, container, false);

        rv_dokter = view.findViewById(R.id.rv_dokter);
        adapter = new DokterAdapter(context,dokterList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_dokter.getContext(),
                linearLayoutManager.getOrientation());
        rv_dokter.addItemDecoration(dividerItemDecoration);
        rv_dokter.setLayoutManager(linearLayoutManager);
        rv_dokter.setAdapter(adapter);

        ApiClient.getKlinikInterface().getAllDokter().enqueue(new Callback<Result<List<Dokter>>>() {
            @Override
            public void onResponse(Call<Result<List<Dokter>>> call, Response<Result<List<Dokter>>> response) {
                dokterList.clear();
                Result<List<Dokter>> result = response.body();
                dokterList.addAll(result.getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Result<List<Dokter>>> call, Throwable t) {

            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
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
