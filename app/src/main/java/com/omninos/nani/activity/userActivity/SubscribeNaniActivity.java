package com.omninos.nani.activity.userActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.omninos.nani.R;
import com.omninos.nani.adapter.SpecialityAdapter;
import com.omninos.nani.modelClass.DiscoverModelClass;
import com.omninos.nani.modelClass.GetNaniPostModel;
import com.omninos.nani.myViewModel.SubscribeViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SubscribeNaniActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView appbar_transparent_image1;
    private CircleImageView naniImage;
    private TextView naniName;
    private RecyclerView recyclerView;
    private SpecialityAdapter adapter;
    private GetNaniPostModel.Detail detail;
    private DiscoverModelClass.PostList postList;
    private Button submitButton;
    private String subData, Naniid;
    private SubscribeViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_nani);

        viewModel = ViewModelProviders.of(this).get(SubscribeViewModel.class);

        initView();
        SetUp();
    }

    private void initView() {
        appbar_transparent_image1 = findViewById(R.id.appbar_transparent_image1);
        naniImage = findViewById(R.id.naniImage);
        naniName = findViewById(R.id.naniName);
        recyclerView = findViewById(R.id.recyclerView);
        submitButton = findViewById(R.id.submitButton);
    }

    private void SetUp() {
        appbar_transparent_image1.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        if (getIntent().getStringExtra("Type").equalsIgnoreCase("1")) {
            detail = (GetNaniPostModel.Detail) getIntent().getSerializableExtra("Details");
            Naniid = detail.getNaniId();
            if (!detail.getNaniImage().isEmpty()) {
                Glide.with(SubscribeNaniActivity.this).load(detail.getNaniImage()).into(naniImage);
            }
            naniName.setText(detail.getNaniNane());

            getList();

            submitButton.setOnClickListener(this);
//            if (detail.getSubscriptionStatus().equalsIgnoreCase("0")) {
//                submitButton.setText("Subscribe");
//                subData = "1";
//            } else {
            submitButton.setText("Unsubscribe");
            subData = "0";
//            }
        } else {
            postList = (DiscoverModelClass.PostList) getIntent().getSerializableExtra("Details");
            Naniid = postList.getNaniId();
            if (!postList.getNaniImage().isEmpty()) {
                Glide.with(SubscribeNaniActivity.this).load(postList.getNaniImage()).into(naniImage);
            }
            naniName.setText(postList.getNaniNane());

            getList1();

            submitButton.setOnClickListener(this);
            if (postList.getSubscriptionStatus().equalsIgnoreCase("0")) {
                submitButton.setText("Subscribe");
                subData = "1";
            } else {
                submitButton.setText("Unsubscribe");
                subData = "0";
            }
        }
    }

    private void getList1() {
        List<String> list = new ArrayList<String>(Arrays.asList(postList.getSpecialities().split(",")));
        adapter = new SpecialityAdapter(SubscribeNaniActivity.this, list);
        recyclerView.setAdapter(adapter);
    }

    private void getList() {
        List<String> list = new ArrayList<String>(Arrays.asList(detail.getSpecialities().split(",")));
        adapter = new SpecialityAdapter(SubscribeNaniActivity.this, list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appbar_transparent_image1:
                onBackPressed();
                break;
            case R.id.submitButton:
                Submit();
                break;
        }
    }

    private void Submit() {
        viewModel.NaniSubscribe(SubscribeNaniActivity.this, Naniid, subData, App.getAppPreference().getUserDetails().getDetails().getId()).observe(SubscribeNaniActivity.this, new Observer<Map>() {
            @Override
            public void onChanged(Map map) {
                if (map.get("success").toString().equalsIgnoreCase("1")) {
                    Toast.makeText(SubscribeNaniActivity.this, map.get("message").toString(), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    CommonUtils.showSnackbarAlert(submitButton, map.get("message").toString());
                }
            }
        });
    }
}
