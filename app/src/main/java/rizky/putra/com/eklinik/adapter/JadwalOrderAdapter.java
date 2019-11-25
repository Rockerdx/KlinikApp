package rizky.putra.com.eklinik.adapter;

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

import rizky.putra.com.eklinik.KonfirmasiActivity;
import rizky.putra.com.eklinik.R;
import rizky.putra.com.eklinik.model.Jadwal;

public class JadwalOrderAdapter
        extends RecyclerView.Adapter<JadwalOrderAdapter.ViewHolder> {

    Context context;
    List<Jadwal> mValues;

    public JadwalOrderAdapter(Context context, List<Jadwal> items) {
        this.context = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jadwal_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Jadwal jadwal = mValues.get(position);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(jadwal.getJadwal())*1000L);
        calendar.add(Calendar.HOUR,-5);
        holder.antrian.setText(jadwal.getMaks() +"");
        holder.tempat.setText(jadwal.getKlinik().getNama());
//        holder.jam.setText(jadwal.getJadwal());
        holder.jam.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" +calendar.get(Calendar.MINUTE));
        holder.tanggal.setText(calendar.get(Calendar.DATE)+ "/" + (calendar.get(Calendar.MONTH)+1));
        holder.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                context.startActivity(new Intent(context, MapActivity.class));
                Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("geo:"+jadwal.getKlinik().getLat()
                        +"," + jadwal.getKlinik().getLng()+"?q="+jadwal.getKlinik().getLat()
                        +"," + jadwal.getKlinik().getLng()+" ("+jadwal.getKlinik().getNama()+")"));
                intent.setComponent(new ComponentName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity"));
                context.startActivity(intent);
            }
        });
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
        TextView tempat;
        TextView tanggal;
        ImageView map;

        ViewHolder(View view) {
            super(view);
            antrian = view.findViewById(R.id.txtAntrian);
            jam = view.findViewById(R.id.txtJam);
            tempat = view.findViewById(R.id.txtDokter);
            tanggal = view.findViewById(R.id.txtTgl);
            map = view.findViewById(R.id.imgMap);
        }

    }
}