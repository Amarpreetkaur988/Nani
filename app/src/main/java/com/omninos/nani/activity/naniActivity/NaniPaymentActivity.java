package com.omninos.nani.activity.naniActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omninos.nani.R;
import com.omninos.nani.adapter.NaniPaymentAdapter;
import com.omninos.nani.modelClass.NaniPaymentModelClass;
import com.omninos.nani.myViewModel.NaniPaymentViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;

public class NaniPaymentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NaniPaymentAdapter adapter;
    private NaniPaymentViewModel viewModel;
    private TextView totalPayment;
    private ImageView appbar_transparent_image1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.BlueTheme);
        setContentView(R.layout.activity_nani_payment);

        viewModel = ViewModelProviders.of(this).get(NaniPaymentViewModel.class);

        initView();
        SetUp();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        totalPayment = findViewById(R.id.totalPayment);
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
        viewModel.naniPaymentModelClassLiveData(NaniPaymentActivity.this, App.getAppPreference().getUserDetails().getDetails().getId()).observe(NaniPaymentActivity.this, new Observer<NaniPaymentModelClass>() {
            @Override
            public void onChanged(NaniPaymentModelClass naniPaymentModelClass) {
                if (naniPaymentModelClass.getSuccess().equalsIgnoreCase("1")) {
                    totalPayment.setText("R " + String.valueOf(naniPaymentModelClass.getTotalAmount()));
                    adapter = new NaniPaymentAdapter(NaniPaymentActivity.this, naniPaymentModelClass.getDetails());
                    recyclerView.setAdapter(adapter);
                } else {
                    CommonUtils.showSnackbarAlert(recyclerView, naniPaymentModelClass.getMessage());
                }
            }
        });
    }
}
