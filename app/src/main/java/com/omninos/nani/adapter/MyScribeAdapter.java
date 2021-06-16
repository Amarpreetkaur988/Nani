package com.omninos.nani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.omninos.nani.R;
import com.omninos.nani.modelClass.GetNaniPostModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Manjinder Singh on 22 , November , 2019
 */
public class MyScribeAdapter extends RecyclerView.Adapter<MyScribeAdapter.MyViewHolder> {

    private Context context;
    private List<GetNaniPostModel.Detail> getDetails;
    Choose choose;

    public interface Choose {
        void Select(int position);
    }

    public MyScribeAdapter(Context context, List<GetNaniPostModel.Detail> getDetails, Choose choose) {
        this.context = context;
        this.getDetails = getDetails;
        this.choose = choose;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_scribe_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        if (!getDetails.get(position).getNaniImage().isEmpty()) {
            Glide.with(context).load(getDetails.get(position).getNaniImage()).into(holder.naniImage);
        }
        holder.naniName.setText(getDetails.get(position).getNaniNane());
        holder.naniImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose.Select(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView naniImage;
        private TextView naniName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            naniImage = itemView.findViewById(R.id.naniImage);
            naniName = itemView.findViewById(R.id.naniName);
        }
    }
}
