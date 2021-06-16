package com.omninos.nani.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omninos.nani.R;
import com.omninos.nani.adapter.FAQAdapter;
import com.omninos.nani.modelClass.FAQModel;
import com.omninos.nani.myViewModel.FaqViewModel;
import com.omninos.nani.utils.CommonUtils;

public class FAQActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private ImageView appbar_transparent_image1;
    FaqViewModel viewModel;
    private FAQAdapter adapter;
    private RelativeLayout app_bar_edit_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getStringExtra("Type").equalsIgnoreCase("1")) {
            setTheme(R.style.BlueTheme);
        }
        setContentView(R.layout.activity_faq);
        CommonUtils.CheckService(FAQActivity.this);
        viewModel = ViewModelProviders.of(this).get(FaqViewModel.class);

        initView();
        SetUp();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        appbar_transparent_image1 = findViewById(R.id.appbar_transparent_image1);
        app_bar_edit_profile=findViewById(R.id.app_bar_edit_profile);
        if (getIntent().getStringExtra("Type").equalsIgnoreCase("2")){
            app_bar_edit_profile.setBackgroundColor(Color.parseColor("#fd6038"));
        }
    }


    private void SetUp() {
        appbar_transparent_image1.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        getList();
    }

    private void getList() {
        viewModel.faqModelLiveData(FAQActivity.this).observe(FAQActivity.this, new Observer<FAQModel>() {
            @Override
            public void onChanged(FAQModel faqModel) {
                if (faqModel.getSuccess().equalsIgnoreCase("1")) {
                    adapter = new FAQAdapter(FAQActivity.this, faqModel.getDetails());
                    recyclerView.setAdapter(adapter);
                } else {
                    CommonUtils.showSnackbarAlert(recyclerView, faqModel.getMessage());
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appbar_transparent_image1:
                onBackPressed();
                break;
        }
    }
}
