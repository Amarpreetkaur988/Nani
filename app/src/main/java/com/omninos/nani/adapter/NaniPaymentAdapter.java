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
import com.omninos.nani.modelClass.NaniPaymentModelClass;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Manjinder Singh on 22 , November , 2019
 */
public class NaniPaymentAdapter extends RecyclerView.Adapter<NaniPaymentAdapter.MyViewHolder> {
    private Context context;
    private List<NaniPaymentModelClass.Detail> details;

    public NaniPaymentAdapter(Context context, List<NaniPaymentModelClass.Detail> details) {
        this.context = context;
        this.details = details;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nani_payment_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (!details.get(position).getImage().isEmpty()) {
            Glide.with(context).load(details.get(position).getImage()).into(holder.userImage);
        }
        holder.userName.setText(details.get(position).getName());
        holder.userAddress.setText(details.get(position).getLocation());
        holder.nameTitle.setText(details.get(position).getPostName());
        holder.price.setText("R " + details.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView userImage;
        private TextView userName, userAddress, nameTitle, price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.userImage);
            userName = itemView.findViewById(R.id.userName);
            userAddress = itemView.findViewById(R.id.userAddress);
            nameTitle = itemView.findViewById(R.id.nameTitle);
            price = itemView.findViewById(R.id.price);
        }
    }
}
