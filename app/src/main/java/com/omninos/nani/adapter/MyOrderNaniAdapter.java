package com.omninos.nani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.omninos.nani.R;
import com.omninos.nani.modelClass.NaniOrderModelClass;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyOrderNaniAdapter extends RecyclerView.Adapter<MyOrderNaniAdapter.Holder> {
    Context context;
    private List<NaniOrderModelClass.Detail> list;
    Select select;

    public interface Select {
        void ChooseStatus(int position, String status);
    }

    public MyOrderNaniAdapter(Context context, List<NaniOrderModelClass.Detail> list, Select select) {
        this.context = context;
        this.list = list;
        this.select = select;
    }

    @NonNull
    @Override
    public MyOrderNaniAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.nani_order_layout, viewGroup, false);
        return new MyOrderNaniAdapter.Holder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyOrderNaniAdapter.Holder holder, final int i) {

        holder.myOrderNaniSelections.setVisibility(View.GONE);
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.myOrderNaniSelections.setVisibility(View.VISIBLE);
                holder.courierBut.setClickable(false);
                holder.foodArrBut.setClickable(false);
                holder.foodPreparedBut.setClickable(true);
                select.ChooseStatus(i, "1");
            }
        });
        holder.foodPreparedBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.courierBut.setClickable(true);
                holder.foodPreparedBut.setBackgroundResource(R.drawable.orange_more_round_back);
                holder.foodPreparedBut.setTextColor(context.getResources().getColor(R.color.white));
                select.ChooseStatus(i, "2");
            }
        });
        holder.courierBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.foodArrBut.setClickable(true);
                holder.courierBut.setBackgroundResource(R.drawable.orange_more_round_back);
                holder.courierBut.setTextColor(context.getResources().getColor(R.color.white));
                select.ChooseStatus(i, "3");
            }
        });
        holder.foodArrBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.foodArrBut.setBackgroundResource(R.drawable.orange_more_round_back);
                holder.foodArrBut.setTextColor(context.getResources().getColor(R.color.white));
                holder.cancel.setText("Completed");
                holder.cancel.setTextColor(context.getResources().getColor(R.color.green));
                holder.myOrderNaniSelections.setVisibility(View.GONE);
                holder.cancel.setClickable(false);
                holder.confirm.setVisibility(View.GONE);
                select.ChooseStatus(i, "4");

            }
        });
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.myOrderNaniSelections.setVisibility(View.GONE);
                holder.confirm.setVisibility(View.GONE);
                holder.cancel.setText("Cancelled");
                select.ChooseStatus(i, "4");
            }
        });

        Glide.with(context).load(list.get(i).getPostImage()).into(holder.my_orders_nani_recycler_image);
        holder.my_orders_nani_recycler_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (!list.get(i).getUserImage().isEmpty()) {
            Glide.with(context).load(list.get(i).getUserImage()).into(holder.my_orders_nani_recycler_pp);
        }

        holder.my_orders_nani_recycler_name.setText(list.get(i).getUserName());
        holder.my_orders_nani_recycler_rating.setRating(5);
        holder.my_orders_buyer_recycler_nani_location.setText(list.get(i).getLocation());
        holder.my_orders_nani_recycler_day_text.setText(list.get(i).getCollectionDay());
        holder.my_orders_nani_recycler_dish_text.setText(list.get(i).getPostName());

        if (list.get(i).getStatus().equalsIgnoreCase("0")) {
            holder.myOrderNaniSelections.setVisibility(View.GONE);
        } else {
            holder.myOrderNaniSelections.setVisibility(View.VISIBLE);
        }

        if (list.get(i).getStatus().equalsIgnoreCase("1")){
            holder.confirmOrderBut.setClickable(false);
            holder.courierBut.setClickable(false);
            holder.foodArrBut.setClickable(false);
            holder.foodPreparedBut.setClickable(true);
            holder.confirm.setVisibility(View.INVISIBLE);
        }else if (list.get(i).getStatus().equalsIgnoreCase("2")){
            holder.confirmOrderBut.setClickable(false);
            holder.courierBut.setClickable(true);
            holder.foodArrBut.setClickable(false);
            holder.foodPreparedBut.setClickable(false);
            holder.confirm.setVisibility(View.INVISIBLE);
            holder.foodPreparedBut.setBackgroundResource(R.drawable.orange_more_round_back);
            holder.foodPreparedBut.setTextColor(context.getResources().getColor(R.color.white));
        }else if (list.get(i).getStatus().equalsIgnoreCase("3")){
            holder.confirmOrderBut.setClickable(false);
            holder.courierBut.setClickable(false);
            holder.foodArrBut.setClickable(true);
            holder.foodPreparedBut.setClickable(false);
            holder.confirm.setVisibility(View.INVISIBLE);

            holder.foodPreparedBut.setBackgroundResource(R.drawable.orange_more_round_back);
            holder.foodPreparedBut.setTextColor(context.getResources().getColor(R.color.white));

            holder.courierBut.setBackgroundResource(R.drawable.orange_more_round_back);
            holder.courierBut.setTextColor(context.getResources().getColor(R.color.white));
        }else if (list.get(i).getStatus().equalsIgnoreCase("4")){
            holder.foodArrBut.setBackgroundResource(R.drawable.orange_more_round_back);
            holder.foodArrBut.setTextColor(context.getResources().getColor(R.color.white));
            holder.cancel.setText("Completed");
            holder.cancel.setTextColor(context.getResources().getColor(R.color.green));
            holder.myOrderNaniSelections.setVisibility(View.GONE);
            holder.cancel.setClickable(false);
            holder.confirm.setVisibility(View.GONE);
        }else if (list.get(i).getStatus().equalsIgnoreCase("5")){

        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        private TextView confirm, cancel, my_orders_nani_recycler_name, my_orders_buyer_recycler_nani_location, my_orders_nani_recycler_day_text, my_orders_nani_recycler_dish_text;
        private Button confirmOrderBut, foodPreparedBut, courierBut, foodArrBut;
        private LinearLayout myOrderNaniSelections;

        private RoundedImageView my_orders_nani_recycler_image;
        private CircleImageView my_orders_nani_recycler_pp;
        private RatingBar my_orders_nani_recycler_rating;


        public Holder(@NonNull View itemView) {
            super(itemView);
            confirm = itemView.findViewById(R.id.my_order_nani_confirm);
            cancel = itemView.findViewById(R.id.my_order_nani_cancel);
            myOrderNaniSelections = itemView.findViewById(R.id.my_order_nani_selections);
            confirmOrderBut = itemView.findViewById(R.id.my_order_nani_confirm_order_but);
            foodPreparedBut = itemView.findViewById(R.id.my_order_nani_food_prepared);
            foodArrBut = itemView.findViewById(R.id.my_order_nani_food_arriving);
            courierBut = itemView.findViewById(R.id.my_order_nani_courier_on_way);

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