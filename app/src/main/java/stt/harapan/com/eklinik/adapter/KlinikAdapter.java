package stt.harapan.com.eklinik.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import stt.harapan.com.eklinik.KlinikOrderActivity;
import stt.harapan.com.eklinik.R;
import stt.harapan.com.eklinik.model.Dokter;
import stt.harapan.com.eklinik.model.Klinik;

public class KlinikAdapter
        extends RecyclerView.Adapter<KlinikAdapter.ViewHolder> implements Filterable {

    Context context;
    List<Klinik> mValues;
    List<Klinik> mValuesFiltered;

    public KlinikAdapter(Context context, List<Klinik> items) {
        this.context = context;
        mValues = items;
        mValuesFiltered = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.klinik_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Klinik klinik = mValuesFiltered.get(position);
        holder.alamat.setText(klinik.getAlamat());
        holder.klinik.setText(klinik.getNama());
        holder.hp.setText(klinik.getHp());
        holder.map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                context.startActivity(new Intent(context, MapActivity.class));
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:"+klinik.getLat()
                                +"," + klinik.getLng()+"?q="+klinik.getLat()
                                +"," + klinik.getLng()+" ("+klinik.getNama()+")"));
                intent.setComponent(new ComponentName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity"));
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(context, KlinikOrderActivity.class);
                x.putExtra("klinik",klinik);
                context.startActivity(x);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValuesFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String charString = charSequence.toString();
            if (charString.isEmpty()) {
                mValuesFiltered = mValues;
            } else {
                List<Klinik> filteredList = new ArrayList<>();
                for (Klinik row : mValues) {

                    // name match condition. this might differ depending on your requirement
                    // here we are looking for name or phone number match
                    if (row.getNama().toLowerCase().contains(charString.toLowerCase()) || row.getNama().contains(charSequence)) {
                        filteredList.add(row);
                    }
                }

                mValuesFiltered = filteredList;
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = mValuesFiltered;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mValuesFiltered = (ArrayList<Klinik>) filterResults.values;
            notifyDataSetChanged();
        }
    };
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView klinik;
        TextView alamat;
        TextView hp;
        ImageView map;

        ViewHolder(View view) {
            super(view);
            klinik = view.findViewById(R.id.txtDokter);
            alamat = view.findViewById(R.id.txtAlamat);
            hp = view.findViewById(R.id.txtHp);
            map = view.findViewById(R.id.imgMap);
        }

    }
}