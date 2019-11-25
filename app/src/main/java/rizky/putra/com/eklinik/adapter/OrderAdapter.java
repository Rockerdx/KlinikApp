package rizky.putra.com.eklinik.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import rizky.putra.com.eklinik.R;
import rizky.putra.com.eklinik.model.Order;

public class OrderAdapter
        extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    Context context;
    List<Order> mValues;

    public OrderAdapter(Context context, List<Order> items) {
        this.context = context;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Order order = mValues.get(position);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(order.getWaktu()*1000L);
        calendar.add(Calendar.HOUR,-5);
        holder.tempat.setText(order.getKlinik().getNama());
        String jam = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        if(jam.length()==1)
            jam="0"+jam;
        String menit = String.valueOf(calendar.get(Calendar.MINUTE));
        if(menit.length()==1){
            menit="0"+menit;
        }
        holder.jam.setText(jam + ":" +menit);
        holder.tanggal.setText(calendar.get(Calendar.DATE)+ "/" + (calendar.get(Calendar.MONTH)+1));
        if(order.getStatus()==1) {
            holder.status.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_check_circle_green_24dp));
            holder.stat.setText("Selesai");
        }
        else {
            holder.status.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_indeterminate_check_box_gray_24dp));
            holder.stat.setText("Pending");
        }
        holder.pasien.setText(order.getPasien());
        holder.no.setText(order.getNo()+"");
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView jam;
        TextView tempat;
        TextView tanggal;
        TextView pasien;
        TextView no;
        ImageView status;
        TextView stat;

        ViewHolder(View view) {
            super(view);
            jam = view.findViewById(R.id.txtJam);
            tempat = view.findViewById(R.id.txtDokter);
            tanggal = view.findViewById(R.id.txtTgl);
            status = view.findViewById(R.id.imgStatus);
            pasien = view.findViewById(R.id.txtPasien);
            stat = view.findViewById(R.id.txtStatus);
            no = view.findViewById(R.id.txtNo);
        }

    }
}