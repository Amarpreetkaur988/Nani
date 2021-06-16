package com.omninos.nani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omninos.nani.R;
import com.omninos.nani.modelClass.MyAllergiesModel;

import java.util.List;

/**
 * Created by Manjinder Singh on 26 , November , 2019
 */
public class AllergiesAdapter extends RecyclerView.Adapter<AllergiesAdapter.MyViewHolder> {

    private Context context;
    private List<MyAllergiesModel> details;
    Choose choose;

    public interface Choose {
        void Select(MyAllergiesModel myAllergiesModel,String id);
    }

    public AllergiesAdapter(Context context, List<MyAllergiesModel> details, Choose choose) {
        this.context = context;
        this.details = details;
        this.choose = choose;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allergies_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.title.setText(details.get(position).getName());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose.Select(details.get(position),details.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}
