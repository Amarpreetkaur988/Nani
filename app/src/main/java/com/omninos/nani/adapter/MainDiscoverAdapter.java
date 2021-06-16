package com.omninos.nani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omninos.nani.R;
import com.omninos.nani.modelClass.DiscoverModelClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manjinder Singh on 22 , November , 2019
 */
public class MainDiscoverAdapter extends RecyclerView.Adapter<MainDiscoverAdapter.MyViewHolder> {
    private Context context;
    private List<DiscoverModelClass.Detail> details;
    Choose choose;

    private List<String> data = new ArrayList<>();

    public MainDiscoverAdapter(Context context, List<DiscoverModelClass.Detail> details, Choose choose) {
        this.context = context;
        this.details = details;
        this.choose = choose;
    }

    public interface Choose {
        void Select(int position, int i);

        void SelectSub(int position, int i);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_discover_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.title.setText(details.get(position).getTitle());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.innerRecyclerView.setLayoutManager(linearLayoutManager);

        InnerDiscoverAdapter adapter = new InnerDiscoverAdapter(context, details.get(position).getPostList(), new InnerDiscoverAdapter.Select() {
            @Override
            public void Choose(int i) {
                choose.Select(position, i);
            }

            @Override
            public void Subscrib(int i) {
                choose.SelectSub(position, i);
            }
        });
        holder.innerRecyclerView.setAdapter(adapter);

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.contains(details.get(position).getTitle())) {
                    holder.innerRecyclerView.setVisibility(View.GONE);
                    data.remove(details.get(position).getTitle());
                } else {
                    holder.innerRecyclerView.setVisibility(View.VISIBLE);
                    data.add(details.get(position).getTitle());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private RecyclerView innerRecyclerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            innerRecyclerView = itemView.findViewById(R.id.innerRecyclerView);

        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
