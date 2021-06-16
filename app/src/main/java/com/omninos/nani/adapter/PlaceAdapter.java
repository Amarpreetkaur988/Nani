package com.omninos.nani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omninos.nani.R;

import java.util.List;


public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyViewHolder> {

    Context context;
    List<String> list;
    SelectPlace selectPlace;


    public interface SelectPlace {
        void Choose(int position);
    }

    public PlaceAdapter(Context context, List<String> list, SelectPlace selectPlace) {
        this.context = context;
        this.list = list;
        this.selectPlace = selectPlace;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_searchplaye_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvaddresslist.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvaddresslist;
        private RelativeLayout selectPlace1;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvaddresslist = itemView.findViewById(R.id.tvaddresslist);
            selectPlace1 = itemView.findViewById(R.id.selectPlace);
            selectPlace1.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.selectPlace:
                    selectPlace.Choose(getLayoutPosition());
                    break;
            }
        }
    }
}
