package com.omninos.nani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omninos.nani.R;
import com.omninos.nani.modelClass.FAQModel;

import java.util.List;

/**
 * Created by Manjinder Singh on 22 , November , 2019
 */
public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.MyViewHolder> {

    private Context context;
    private List<FAQModel.Detail> list;

    public FAQAdapter(Context context, List<FAQModel.Detail> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Question.setText(list.get(position).getQuestion());
        holder.Answer.setText(list.get(position).getAnswer());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView Question, Answer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Question = itemView.findViewById(R.id.Question);
            Answer = itemView.findViewById(R.id.Answer);
        }
    }
}
