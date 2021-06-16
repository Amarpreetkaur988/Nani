package com.omninos.nani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.omninos.nani.R;

import java.util.List;

public class AddItemRecyclerAdapter extends RecyclerView.Adapter<AddItemRecyclerAdapter.Holder> {
    Context context;
    private List<String> images;
    CloseImage closeImage;


    public interface CloseImage {
        void Select(String url);
    }

    public AddItemRecyclerAdapter(Context context, List<String> images, CloseImage closeImage) {
        this.context = context;
        this.images = images;
        this.closeImage = closeImage;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_item_recyler_item, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        Glide.with(context).load("file://" + images.get(i)).into(holder.itemImages);
        holder.itemImages.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.crossItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeImage.Select(images.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private RoundedImageView itemImages;
        private ImageView crossItem;

        public Holder(@NonNull View itemView) {
            super(itemView);
            itemImages = itemView.findViewById(R.id.itemImages);
            crossItem = itemView.findViewById(R.id.crossItem);
        }
    }
}
