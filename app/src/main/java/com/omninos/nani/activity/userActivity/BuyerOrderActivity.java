package com.omninos.nani.activity.userActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omninos.nani.R;
import com.omninos.nani.adapter.BuyerOrderAdapter;
import com.omninos.nani.modelClass.BuyerOrderModel;
import com.omninos.nani.myViewModel.OrderViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;

public class BuyerOrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BuyerOrderAdapter adapter;
    private OrderViewModel viewModel;
    private ImageView appbar_transparent_image1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_order);

        CommonUtils.CheckService(BuyerOrderActivity.this);
        viewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        initView();
        SetUp();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        appbar_transparent_image1 = findViewById(R.id.appbar_transparent_image1);
    }

    private void SetUp() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        appbar_transparent_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getList();
    }

    private void getList() {
        viewModel.getOrdersData(BuyerOrderActivity.this, App.getAppPreference().getUserDetails().getDetails().getId()).observe(BuyerOrderActivity.this, new Observer<BuyerOrderModel>() {
            @Override
            public void onChanged(final BuyerOrderModel buyerOrderModel) {
                if (buyerOrderModel.getSuccess().equalsIgnoreCase("1")) {
                    adapter = new BuyerOrderAdapter(BuyerOrderActivity.this, buyerOrderModel.getDetails(), new BuyerOrderAdapter.Choose() {
                        @Override
                        public void Select(int position) {
                            startActivity(new Intent(BuyerOrderActivity.this,OrderDetailsActivity.class).putExtra("Details",buyerOrderModel.getDetails().get(position)));
                        }
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    CommonUtils.showSnackbarAlert(recyclerView, buyerOrderModel.getMessage());
                }
            }
        });
    }
}
