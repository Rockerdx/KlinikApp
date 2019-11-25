package stt.harapan.com.eklinik.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import stt.harapan.com.eklinik.KonfirmasiActivity;
import stt.harapan.com.eklinik.R;
import stt.harapan.com.eklinik.model.Jadwal;

public class JadwalKlinikAdapter
        extends RecyclerView.Adapter<JadwalKlinikAdapter.ViewHolder> {

    Context context;
    List<Jadwal> mValues;

    public JadwalKlinikAdapter(Context context, List<Jadwal> items) {
        this.context = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jadwal_klinik_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Jadwal jadwal = mValues.get(position);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(jadwal.getJadwal())*1000L);
        calendar.add(Calendar.HOUR,-5);
        holder.antrian.setText((jadwal.getMaks()-jadwal.getAntrian()) +"");
        holder.dokter.setText(jadwal.getDokter().getNama());
//        holder.jam.setText(jadwal.getJadwal());
        String jam = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        if(jam.length()==1)
            jam="0"+jam;
        String menit = String.valueOf(calendar.get(Calendar.MINUTE));
        if(menit.length()==1){
            menit="0"+menit;
        }
        holder.jam.setText(jam + ":" +menit);
        holder.tanggal.setText(calendar.get(Calendar.DATE)+ "/" + (calendar.get(Calendar.MONTH)+1));
        holder.spesialis.setText("Spesialis " + spesialis(jadwal.getDokter().getSpesialis()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(context, KonfirmasiActivity.class);
                x.putExtra("jadwal",jadwal);
                context.startActivity(x);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView antrian;
        TextView jam;
        TextView dokter;
        TextView tanggal;
        TextView spesialis;
        ImageView map;

        ViewHolder(View view) {
            super(view);
            antrian = view.findViewById(R.id.txtAntrian);
            jam = view.findViewById(R.id.txtJam);
            dokter = view.findViewById(R.id.txtDokter);
            tanggal = view.findViewById(R.id.txtTgl);
            spesialis = view.findViewById(R.id.txtSpesialis);
            map = view.findViewById(R.id.imgMap);
        }

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
}