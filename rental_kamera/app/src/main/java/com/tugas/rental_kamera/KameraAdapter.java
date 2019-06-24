package com.tugas.rental_kamera;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class KameraAdapter extends RecyclerView.Adapter<KameraAdapter.KameraHolder> {


    private ArrayList<Kamera> dataList;

    public KameraAdapter(ArrayList<Kamera> dataList) {
        this.dataList = dataList;
    }

    @Override
    public KameraHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_kamera, parent, false);
        return new KameraHolder(view);
    }

    @Override
    public void onBindViewHolder(KameraHolder holder, int position) {
        holder.nama.setText(dataList.get(position).getNamaKamera());
        holder.status.setText(dataList.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class KameraHolder extends RecyclerView.ViewHolder {
        TextView nama,status;

        public KameraHolder(View itemView) {
            super(itemView);
            nama = (TextView)itemView.findViewById(R.id.textView);
            status = (TextView)itemView.findViewById(R.id.textView2);
        }
    }
}
