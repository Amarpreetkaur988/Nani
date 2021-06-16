package com.omninos.nani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.omninos.nani.R;
import com.omninos.nani.modelClass.BuyerOrderModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Manjinder Singh on 25 , November , 2019
 */
public class BuyerOrderAdapter extends RecyclerView.Adapter<BuyerOrderAdapter.MyViewHolder> {

    Context context;
    private List<BuyerOrderModel.Detail> list;
    Choose choose;



    public interface Choose{
        void Select(int position);
    }

    public BuyerOrderAdapter(Context context, List<BuyerOrderModel.Detail> list, Choose choose) {
        this.context = context;
        this.list = list;
        this.choose = choose;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nani_order_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int i) {

        holder.confirmOrderBut.setClickable(false);
        holder.courierBut.setClickable(false);
        holder.foodArrBut.setClickable(false);
        holder.foodPreparedBut.setClickable(false);
        holder.confirm.setVisibility(View.GONE);


        holder.my_orders_nani_recycler_name.setText(list.get(i).getUserName());
        holder.my_orders_nani_recycler_rating.setRating(5);
        holder.my_orders_buyer_recycler_nani_location.setText(list.get(i).getLocation());
        holder.my_orders_nani_recycler_day_text.setText(list.get(i).getCollectionDay());
        holder.my_orders_nani_recycler_dish_text.setText(list.get(i).getPostName());

        Glide.with(context).load(list.get(i).getPostImage()).into(holder.my_orders_nani_recycler_image);
        holder.my_orders_nani_recycler_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        if (list.get(i).getStatus().equalsIgnoreCase("0")) {
//            holder.myOrderNaniSelections.setVisibility(View.GONE);
//            holder.confirm.setText("Pending");
//            holder.confirm.setVisibility(View.VISIBLE);
//        } else {
//            holder.myOrderNaniSelections.setVisibility(View.VISIBLE);
//        }
//
//        if (list.get(i).getStatus().equalsIgnoreCase("1")) {
//
//        } else if (list.get(i).getStatus().equalsIgnoreCase("2")) {
//
//            holder.foodPreparedBut.setBackgroundResource(R.drawable.orange_more_round_back);
//            holder.foodPreparedBut.setTextColor(context.getResources().getColor(R.color.white));
//        } else if (list.get(i).getStatus().equalsIgnoreCase("3")) {
//
//            holder.foodPreparedBut.setBackgroundResource(R.drawable.orange_more_round_back);
//            holder.foodPreparedBut.setTextColor(context.getResources().getColor(R.color.white));
//
//            holder.courierBut.setBackgroundResource(R.drawable.orange_more_round_back);
//            holder.courierBut.setTextColor(context.getResources().getColor(R.color.white));
//        } else if (list.get(i).getStatus().equalsIgnoreCase("4")) {
//            holder.foodArrBut.setBackgroundResource(R.drawable.orange_more_round_back);
//            holder.foodArrBut.setTextColor(context.getResources().getColor(R.color.white));
//            holder.cancel.setText("Completed");
//            holder.cancel.setTextColor(context.getResources().getColor(R.color.green));
//            holder.myOrderNaniSelections.setVisibility(View.GONE);
//            holder.cancel.setClickable(false);
//            holder.confirm.setVisibility(View.GONE);
//        } else if (list.get(i).getStatus().equalsIgnoreCase("5")) {
//
//        }
        holder.myOrderNaniSelections.setVisibility(View.GONE);
        holder.cancel.setVisibility(View.GONE);
        holder.my_order_nani_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose.Select(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView confirm, cancel, my_orders_nani_recycler_name, my_orders_buyer_recycler_nani_location, my_orders_nani_recycler_day_text, my_orders_nani_recycler_dish_text;
        private Button confirmOrderBut, foodPreparedBut, courierBut, foodArrBut;
        private LinearLayout myOrderNaniSelections;

        private RoundedImageView my_orders_nani_recycler_image;
        private CircleImageView my_orders_nani_recycler_pp;
        private RatingBar my_orders_nani_recycler_rating;
        private RelativeLayout my_order_nani_info;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            confirm = itemView.findViewById(R.id.my_order_nani_confirm);
            cancel = itemView.findViewById(R.id.my_order_nani_cancel);
            myOrderNaniSelections = itemView.findViewById(R.id.my_order_nani_selections);
            confirmOrderBut = itemView.findViewById(R.id.my_order_nani_confirm_order_but);
            foodPreparedBut = itemView.findViewById(R.id.my_order_nani_food_prepared);
            foodArrBut = itemView.findViewById(R.id.my_order_nani_food_arriving);
            courierBut = itemView.findViewById(R.id.my_order_nani_courier_on_way);
            my_order_nani_info=itemView.findViewById(R.id.my_order_nani_info);

            my_orders_nani_recycler_image = itemView.findViewById(R.id.my_orders_nani_recycler_image);
            my_orders_nani_recycler_pp = itemView.findViewById(R.id.my_orders_nani_recycler_pp);
            my_orders_nani_recycler_name = itemView.findViewById(R.id.my_orders_nani_recycler_name);
            my_orders_nani_recycler_rating = itemView.findViewById(R.id.my_orders_nani_recycler_rating);
            my_orders_buyer_recycler_nani_location = itemView.findViewById(R.id.my_orders_buyer_recycler_nani_location);
            my_orders_nani_recycler_day_text = itemView.findViewById(R.id.my_orders_nani_recycler_day_text);
            my_orders_nani_recycler_dish_text = itemView.findViewById(R.id.my_orders_nani_recycler_dish_text);
        }
    }
}
