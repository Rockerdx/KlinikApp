package rizky.putra.com.eklinik.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rizky.putra.com.eklinik.R;
import rizky.putra.com.eklinik.model.Jam;

public class JamAdapter
        extends RecyclerView.Adapter<JamAdapter.ViewHolder> {

    Context context;
    List<Jam> mValues;

    public JamAdapter(Context context, List<Jam> items) {
        this.context = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialog_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Jam jadwal = mValues.get(position);

        holder.hari.setText(jadwal.getHari());
        holder.jam.setText(jadwal.getWaktu());


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView hari;
        TextView jam;


        ViewHolder(View view) {
            super(view);
            hari = view.findViewById(R.id.txtHari);
            jam = view.findViewById(R.id.txtJam);
        }

    }
}