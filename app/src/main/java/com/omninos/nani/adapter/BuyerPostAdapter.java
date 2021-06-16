package com.omninos.nani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.omninos.nani.R;
import com.omninos.nani.modelClass.GetNaniPostModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Manjinder Singh on 22 , November , 2019
 */
public class BuyerPostAdapter extends RecyclerView.Adapter<BuyerPostAdapter.MyViewHolder> {

    private Context context;
    private List<GetNaniPostModel.Detail> list;
    private Choose choose;


    public BuyerPostAdapter(Context context, List<GetNaniPostModel.Detail> list, Choose choose) {
        this.context = context;
        this.list = list;
        this.choose = choose;
    }

    public interface Choose {
        void Select(int position);

        void Subscribe(int position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buyer_post_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        if (!list.get(position).getNaniImage().isEmpty()) {
            Glide.with(context).load(list.get(position).getNaniImage()).into(holder.dp_vertical_feed_item);
        }
        holder.name__vertical_feed_item.setText(list.get(position).getNaniNane());
        holder.address.setText(list.get(position).getNaniAddress());
        holder.rating_bar_vertical_feed_item.setRating(Float.parseFloat(list.get(position).getRating()));
        Glide.with(context).load(list.get(position).getPostImage().get(0).getImage()).into(holder.food_image_vertical_feed_item);
        holder.food_image_vertical_feed_item.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.donuts_text_vertical_feed_item.setText(list.get(position).getName());
        holder.nice_good_vertical_feed_item.setText(list.get(position).getDescription());
        holder.price.setText("R " + list.get(position).getPrice());

        holder.food_image_vertical_feed_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose.Select(position);
            }
        });

        holder.dp_vertical_feed_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose.Subscribe(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView dp_vertical_feed_item;
        private TextView name__vertical_feed_item, address, donuts_text_vertical_feed_item, nice_good_vertical_feed_item, price;
        private RatingBar rating_bar_vertical_feed_item;
        private RoundedImageView food_image_vertical_feed_item;
        private CardView vertical_feed_card_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dp_vertical_feed_item = itemView.findViewById(R.id.dp_vertical_feed_item);
            name__vertical_feed_item = itemView.findViewById(R.id.name__vertical_feed_item);
            address = itemView.findViewById(R.id.address);
            food_image_vertical_feed_item = itemView.findViewById(R.id.food_image_vertical_feed_item);
            rating_bar_vertical_feed_item = itemView.findViewById(R.id.rating_bar_vertical_feed_item);
            donuts_text_vertical_feed_item = itemView.findViewById(R.id.donuts_text_vertical_feed_item);
            nice_good_vertical_feed_item = itemView.findViewById(R.id.nice_good_vertical_feed_item);
            price = itemView.findViewById(R.id.today_text_vertical_feed_item);
            vertical_feed_card_item = itemView.findViewById(R.id.vertical_feed_card_item);
        }
    }
}
