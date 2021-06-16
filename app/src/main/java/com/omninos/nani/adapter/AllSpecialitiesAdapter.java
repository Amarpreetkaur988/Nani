package com.omninos.nani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omninos.nani.R;
import com.omninos.nani.modelClass.SpecialitiesListClass;

import java.util.List;

public class AllSpecialitiesAdapter extends RecyclerView.Adapter<AllSpecialitiesAdapter.MyViewHolder> {
    Context context;
//    private ArrayList<String> items_list;
    private List<SpecialitiesListClass.Detail> SpecialitiesdetailList;
    private SelectItems selectItems;

    public interface SelectItems{
        void Select(String position, String ids);
    }

    public AllSpecialitiesAdapter(Context context, List<SpecialitiesListClass.Detail> specialitiesdetailList, SelectItems selectItems) {
        this.context = context;
        SpecialitiesdetailList = specialitiesdetailList;
        this.selectItems = selectItems;
    }

    @NonNull
    @Override
    public AllSpecialitiesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.all_specialities_items,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllSpecialitiesAdapter.MyViewHolder myViewHolder, int i) {
//        myViewHolder.items.setText(items_list.get(i));

        final SpecialitiesListClass.Detail specialitiesListClass = SpecialitiesdetailList.get(i);
        myViewHolder.items.setText(specialitiesListClass.getTitle());
        myViewHolder.items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItems.Select(specialitiesListClass.getTitle(), specialitiesListClass.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return SpecialitiesdetailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView items;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            items = itemView.findViewById(R.id.items);


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.items:
//                    selectItems.Select();
                    break;
            }
        }
    }

    public void filterList(List<SpecialitiesListClass.Detail> filterdNames) {
        this.SpecialitiesdetailList = filterdNames;
        notifyDataSetChanged();
    }
}
