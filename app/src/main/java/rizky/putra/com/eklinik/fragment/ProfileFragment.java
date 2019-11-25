package rizky.putra.com.eklinik.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rizky.putra.com.eklinik.R;
import rizky.putra.com.eklinik.WelcomeActivity;
import rizky.putra.com.eklinik.adapter.OrderAdapter;
import rizky.putra.com.eklinik.connection.ApiClient;
import rizky.putra.com.eklinik.helper.SessionManager;
import rizky.putra.com.eklinik.model.Dokter;
import rizky.putra.com.eklinik.model.Order;
import rizky.putra.com.eklinik.model.Pasien;
import rizky.putra.com.eklinik.model.Result;

public class ProfileFragment extends Fragment {


    RecyclerView rv_history;
    public ProfileFragment() {
        // Required empty public constructor
    }

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView foto = view.findViewById(R.id.imgProfile);
        TextView nama = view.findViewById(R.id.tztNama);
        TextView email = view.findViewById(R.id.txtEmail);
        TextView spesialis = view.findViewById(R.id.txtSpesialis);
        TextView hp = view.findViewById(R.id.txtHp);
        rv_history = view.findViewById(R.id.rv_history);

        SessionManager sessionManager = new SessionManager(getActivity());

        Log.d("Tes","isi string" + sessionManager.getTypeUser());
        if(sessionManager.getTypeUser().equals("dokter")) {
            Dokter dokter = (Dokter) sessionManager.getData(2);
            nama.setText(dokter.getNama());
            email.setText(dokter.getEmail());
            spesialis.setText(spesialis(dokter.getSpesialis()));
            hp.setText(dokter.getHp());
            Picasso.get().load(dokter.getFoto()).into(foto);

            ApiClient.getKlinikInterface().getAllHistoryDokter(dokter.getId(),"dokter").enqueue(new Callback<Result<List<Order>>>() {
                @Override
                public void onResponse(Call<Result<List<Order>>> call, Response<Result<List<Order>>> response) {
                    Result<List<Order>> result = response.body();
                    if(!result.getError()) {
                        List<Order> orders = result.getData();
                        OrderAdapter adapter = new OrderAdapter(getActivity(),orders);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_history.getContext(),
                                linearLayoutManager.getOrientation());
                        rv_history.addItemDecoration(dividerItemDecoration);
                        rv_history.setLayoutManager(linearLayoutManager);
                        rv_history.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<Result<List<Order>>> call, Throwable t) {

                }
            });

        }
        else {
            Pasien pas = (Pasien) sessionManager.getData(1);
            nama.setText(pas.getNama());
            email.setText(pas.getEmail());
            spesialis.setText(pas.getUmur() + " Tahun");
            String x;
            if(pas.getKelamin().equals("1")){
                x = "Pria";
                Picasso.get().load("https://image.shutterstock.com/z/stock-vector-male-avatar-profile-picture-use-for-social-website-vector-193292033.jpg").into(foto);
            }
            else {
                x="Wanita";
                Picasso.get().load("https://image.shutterstock.com/z/stock-vector-female-profile-avatar-icon-dark-grey-on-white-background-use-for-social-network-vector-193292231.jpg").into(foto);
            }
            hp.setText(x);

            ApiClient.getKlinikInterface().getAllHistoryDokter(String.valueOf(pas.getId()),"pasien").enqueue(new Callback<Result<List<Order>>>() {
                @Override
                public void onResponse(Call<Result<List<Order>>> call, Response<Result<List<Order>>> response) {
                    Result<List<Order>> result = response.body();
                    if(!result.getError()) {
                        List<Order> orders = result.getData();
                        OrderAdapter adapter = new OrderAdapter(context,orders);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv_history.getContext(),
                                linearLayoutManager.getOrientation());
                        rv_history.addItemDecoration(dividerItemDecoration);
                        rv_history.setLayoutManager(linearLayoutManager);
                        rv_history.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<Result<List<Order>>> call, Throwable t) {
                    Toast.makeText(context,"Belum ada history",Toast.LENGTH_SHORT).show();
                }
            });

        }


        return view;
    }

    private String spesialis(int x){
        String spe = "";
        switch (x){
            case 1:
                spe="Umum";
                break;
            case 2:
                spe="Gigi";
                break;
            case 3:
                spe="Kulit & Kelamin";
                break;
            case 4:
                spe="Mata";
                break;
        }
        return spe;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_about, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.logout) {
            SessionManager sessionManager = new SessionManager(getActivity());
            sessionManager.setLogin(false);
            startActivity(new Intent(getActivity(), WelcomeActivity.class));
            getActivity().finish();
        }


        return super.onOptionsItemSelected(item);
    }
}
