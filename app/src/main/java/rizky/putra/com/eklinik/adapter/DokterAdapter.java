package rizky.putra.com.eklinik.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import rizky.putra.com.eklinik.OrderDokterActivity;
import rizky.putra.com.eklinik.R;
import rizky.putra.com.eklinik.model.Dokter;

public class DokterAdapter
        extends RecyclerView.Adapter<DokterAdapter.ViewHolder> implements Filterable {

    Context context;
    List<Dokter> mValues;
    List<Dokter> mValuesFiltered;

    public DokterAdapter(Context context, List<Dokter> items) {
        this.context = context;
        mValues = items;
        mValuesFiltered = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dokter_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Dokter dokter = mValuesFiltered.get(position);

        Picasso.get().load(dokter.getFoto()).into(holder.foto);
        holder.nip.setText("STR : " + dokter.getId());
        holder.spesialis.setText("Spesialis : " +spesialis(dokter.getSpesialis()));
        holder.nama.setText(dokter.getNama());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(context,OrderDokterActivity.class);
                x.putExtra("nip",dokter.getId());
                x.putExtra("nama",dokter.getNama());
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
                    List<Dokter> filteredList = new ArrayList<>();
                    for (Dokter row : mValues) {

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
                mValuesFiltered = (ArrayList<Dokter>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nama;
        TextView spesialis;
        TextView nip;
        ImageView foto;

        ViewHolder(View view) {
            super(view);
            nama = view.findViewById(R.id.txtDokter);
            spesialis = view.findViewById(R.id.txtSpesialis);
            nip = view.findViewById(R.id.txtNIP);
            foto = view.findViewById(R.id.imgDokter);
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