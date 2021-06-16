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
import com.omninos.nani.modelClass.GetNaniPostModel;

import java.util.List;

/**
 * Created by Manjinder Singh on 21 , November , 2019
 */
public class FoodImageAdapter extends RecyclerView.Adapter<FoodImageAdapter.MyViewHolder> {

    private Context context;
    private List<GetNaniPostModel.PostImage> postImages;
    Choose choose;

    public interface Choose {
        public void Select(String path);
    }

    public FoodImageAdapter(Context context, List<GetNaniPostModel.PostImage> postImages, Choose choose) {
        this.context = context;
        this.postImages = postImages;
        this.choose = choose;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_image_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Glide.with(context).load(postImages.get(position).getImage()).into(holder.allImages);
        holder.allImages.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.allImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose.Select(postImages.get(position).getImage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return postImages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView allImages;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            allImages = itemView.findViewById(R.id.allImages);
        }
    }
}
