package com.omninos.nani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omninos.nani.R;

import java.util.List;

public class SpecialitiesListRecyclerAdapter extends RecyclerView.Adapter<SpecialitiesListRecyclerAdapter.Holder> {
    Context context;
    List<String> list;
    private RemoveItem removeItem;

    public interface RemoveItem{
        void Remove(int position);
    }

    public SpecialitiesListRecyclerAdapter(Context context, List<String> list, RemoveItem removeItem) {
        this.context = context;
        this.list = list;
        this.removeItem = removeItem;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.specialities_list_recycler_item,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.items.setText(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView cancel;
        TextView items;

        public Holder(@NonNull View itemView) {
            super(itemView);

            cancel = itemView.findViewById(R.id.cancel);
            items = itemView.findViewById(R.id.items);

            cancel.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.cancel:
                    removeItem.Remove(getLayoutPosition());
                    break;
            }
        }
    }
}
