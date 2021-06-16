package com.omninos.nani.activity.userActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.omninos.nani.R;
import com.omninos.nani.activity.naniActivity.NaniHomeActivity;
import com.omninos.nani.activity.naniActivity.UpdateBankDetailActivity;
import com.omninos.nani.fragments.buyerFragments.BuyerDiscoverFragment;
import com.omninos.nani.fragments.buyerFragments.BuyerHomeFragment;
import com.omninos.nani.fragments.buyerFragments.BuyerProfileFragment;
import com.omninos.nani.fragments.buyerFragments.BuyerSettingsFragment;
import com.omninos.nani.modelClass.LoginRegisterModelClass;
import com.omninos.nani.myViewModel.UpdateNaniStatusViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;
import com.omninos.nani.utils.ConstantData;

import de.hdodenhof.circleimageview.CircleImageView;

public class BuyerHomeActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout data, mainContent;
    private DrawerLayout buyer_drawer;

    private RelativeLayout home_navigator_buyer, discover_navigator_buyer, profile_navigator_buyer, setting_navigator_buyer;
    private View home_bottom_view_buyer, discover_bottom_view_buyer, profile_bottom_view_buyer, setting_bottom_view_buyer;
    private ImageView home_img_buyer, discover_img_buyer, profile_img_buyer, setting_img_buyer, notification;
    private TextView home_text_home_buyer, discover_text_home_buyer, profile_text_home_buyer, setting_text_home_buyer, title_app_bar_large, nav_name;
    private ImageView appbar_large_image1;


    private CircleImageView nav_pic;

    private TextView home, my_orders, switch_sides;
    private UpdateNaniStatusViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_home);

        CommonUtils.CheckService(BuyerHomeActivity.this);
        viewModel = ViewModelProviders.of(this).get(UpdateNaniStatusViewModel.class);

        initView();
        Setup();

    }

    private void initView() {
        home = findViewById(R.id.home);
        my_orders = findViewById(R.id.my_orders);
        switch_sides = findViewById(R.id.switch_sides);

        data = findViewById(R.id.data);
        buyer_drawer = findViewById(R.id.buyer_drawer);
        mainContent = findViewById(R.id.mainContent);
        appbar_large_image1 = findViewById(R.id.appbar_large_image1);

        title_app_bar_large = findViewById(R.id.title_app_bar_large);
        notification = findViewById(R.id.appbar_large_image2);

        //bottom Navigation
        home_navigator_buyer = findViewById(R.id.home_navigator_buyer);
        discover_navigator_buyer = findViewById(R.id.discover_navigator_buyer);
        profile_navigator_buyer = findViewById(R.id.profile_navigator_buyer);
        setting_navigator_buyer = findViewById(R.id.setting_navigator_buyer);

        home_bottom_view_buyer = findViewById(R.id.home_bottom_view_buyer);
        discover_bottom_view_buyer = findViewById(R.id.discover_bottom_view_buyer);
        profile_bottom_view_buyer = findViewById(R.id.profile_bottom_view_buyer);
        setting_bottom_view_buyer = findViewById(R.id.setting_bottom_view_buyer);

        home_img_buyer = findViewById(R.id.home_img_buyer);
        discover_img_buyer = findViewById(R.id.discover_img_buyer);
        profile_img_buyer = findViewById(R.id.profile_img_buyer);
        setting_img_buyer = findViewById(R.id.setting_img_buyer);

        home_text_home_buyer = findViewById(R.id.home_text_home_buyer);
        discover_text_home_buyer = findViewById(R.id.discover_text_home_buyer);
        profile_text_home_buyer = findViewById(R.id.profile_text_home_buyer);
        setting_text_home_buyer = findViewById(R.id.setting_text_home_buyer);

        nav_pic = findViewById(R.id.nav_pic);
        nav_name = findViewById(R.id.nav_name);
    }

    private void Setup() {
        home.setOnClickListener(this);
        my_orders.setOnClickListener(this);
        home_navigator_buyer.setOnClickListener(this);
        discover_navigator_buyer.setOnClickListener(this);
        profile_navigator_buyer.setOnClickListener(this);
        setting_navigator_buyer.setOnClickListener(this);
        appbar_large_image1.setOnClickListener(this);
        switch_sides.setOnClickListener(this);
        data.setBackgroundColor(getResources().getColor(R.color.orange));

        if (getIntent().getStringExtra("Type").equalsIgnoreCase("0")) {
            homeFrag();
            SelectFragment(new BuyerHomeFragment());
        } else {
            discoverFrag();
            SelectFragment(new BuyerDiscoverFragment());
        }
        setNavigationDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!App.getAppPreference().getUserDetails().getDetails().getImage().isEmpty()) {
            Glide.with(this).load(App.getAppPreference().getUserDetails().getDetails().getImage()).into(nav_pic);
        }
        nav_name.setText(App.getAppPreference().getUserDetails().getDetails().getName());

    }

    private void setNavigationDrawer() {
        buyer_drawer.setScrimColor(Color.TRANSPARENT);
        buyer_drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {
                mainContent.setX(view.getWidth() * v);
            }

            @Override
            public void onDrawerOpened(@NonNull View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_navigator_buyer:
                homeFrag();
                SelectFragment(new BuyerHomeFragment());
                break;

            case R.id.discover_navigator_buyer:
                discoverFrag();
                SelectFragment(new BuyerDiscoverFragment());
                break;

            case R.id.profile_navigator_buyer:
                profileFrag();
                SelectFragment(new BuyerProfileFragment());
                break;

            case R.id.setting_navigator_buyer:
                settingFrag();
                SelectFragment(new BuyerSettingsFragment());
                break;
            case R.id.appbar_large_image1:
                buyer_drawer.openDrawer(Gravity.START);
                break;

            case R.id.home:
                buyer_drawer.closeDrawer(Gravity.START);
                break;

            case R.id.my_orders:
                startActivity(new Intent(BuyerHomeActivity.this, BuyerOrderActivity.class));
                break;

            case R.id.switch_sides:
                Check();
                break;
        }
    }

    private void Check() {
        viewModel.checkNani(BuyerHomeActivity.this, App.getAppPreference().getUserDetails().getDetails().getId()).observe(BuyerHomeActivity.this, new Observer<LoginRegisterModelClass>() {
            @Override
            public void onChanged(LoginRegisterModelClass loginRegisterModelClass) {
                if (loginRegisterModelClass.getSuccess().equalsIgnoreCase("1")) {
                    App.getAppPreference().SaveString(ConstantData.USER_TYPE, "1");
                    startActivity(new Intent(BuyerHomeActivity.this, NaniHomeActivity.class));
                    finishAffinity();
                } else {
                    startActivity(new Intent(BuyerHomeActivity.this, UpdateBankDetailActivity.class));
                }
            }
        });
    }

    private void settingFrag() {
        home_bottom_view_buyer.setVisibility(View.GONE);
        discover_bottom_view_buyer.setVisibility(View.GONE);
        profile_bottom_view_buyer.setVisibility(View.GONE);
        setting_bottom_view_buyer.setVisibility(View.VISIBLE);
        home_img_buyer.setImageResource(R.drawable.home_unselected);
        discover_img_buyer.setImageResource(R.drawable.discover_unselected);
        profile_img_buyer.setImageResource(R.drawable.profile_unselected);
        setting_img_buyer.setImageResource(R.drawable.setting_selected);
        home_text_home_buyer.setTextColor(Color.parseColor("#AFBFD8"));
        discover_text_home_buyer.setTextColor(Color.parseColor("#AFBFD8"));
        profile_text_home_buyer.setTextColor(Color.parseColor("#AFBFD8"));
        setting_text_home_buyer.setTextColor(Color.parseColor("#fd6038"));
        setting_navigator_buyer.setBackgroundColor(Color.parseColor("#E3EFF2"));
        home_navigator_buyer.setBackgroundColor(Color.parseColor("#FFFFFF"));
        profile_navigator_buyer.setBackgroundColor(Color.parseColor("#FFFFFF"));
        discover_navigator_buyer.setBackgroundColor(Color.parseColor("#FFFFFF"));
        title_app_bar_large.setText("Settings");
        notification.setVisibility(View.GONE);
    }

    private void profileFrag() {
        home_bottom_view_buyer.setVisibility(View.GONE);
        discover_bottom_view_buyer.setVisibility(View.GONE);
        profile_bottom_view_buyer.setVisibility(View.VISIBLE);
        setting_bottom_view_buyer.setVisibility(View.GONE);
        home_img_buyer.setImageResource(R.drawable.home_unselected);
        discover_img_buyer.setImageResource(R.drawable.discover_unselected);
        profile_img_buyer.setImageResource(R.drawable.profile_selected);
        setting_img_buyer.setImageResource(R.drawable.setting_unselected);
        home_text_home_buyer.setTextColor(Color.parseColor("#AFBFD8"));
        discover_text_home_buyer.setTextColor(Color.parseColor("#AFBFD8"));
        profile_text_home_buyer.setTextColor(Color.parseColor("#fd6038"));
        setting_text_home_buyer.setTextColor(Color.parseColor("#AFBFD8"));
        profile_navigator_buyer.setBackgroundColor(Color.parseColor("#E3EFF2"));
        home_navigator_buyer.setBackgroundColor(Color.parseColor("#FFFFFF"));
        discover_navigator_buyer.setBackgroundColor(Color.parseColor("#FFFFFF"));
        setting_navigator_buyer.setBackgroundColor(Color.parseColor("#FFFFFF"));
        title_app_bar_large.setText("Profile");
        notification.setVisibility(View.GONE);
    }

    private void discoverFrag() {
        home_bottom_view_buyer.setVisibility(View.GONE);
        discover_bottom_view_buyer.setVisibility(View.VISIBLE);
        profile_bottom_view_buyer.setVisibility(View.GONE);
        setting_bottom_view_buyer.setVisibility(View.GONE);
        home_img_buyer.setImageResource(R.drawable.home_unselected);
        discover_img_buyer.setImageResource(R.drawable.discover_selected);
        profile_img_buyer.setImageResource(R.drawable.profile_unselected);
        setting_img_buyer.setImageResource(R.drawable.setting_unselected);
        home_text_home_buyer.setTextColor(Color.parseColor("#AFBFD8"));
        discover_text_home_buyer.setTextColor(Color.parseColor("#fd6038"));
        profile_text_home_buyer.setTextColor(Color.parseColor("#AFBFD8"));
        setting_text_home_buyer.setTextColor(Color.parseColor("#AFBFD8"));
        discover_navigator_buyer.setBackgroundColor(Color.parseColor("#E3EFF2"));
        home_navigator_buyer.setBackgroundColor(Color.parseColor("#FFFFFF"));
        profile_navigator_buyer.setBackgroundColor(Color.parseColor("#FFFFFF"));
        setting_navigator_buyer.setBackgroundColor(Color.parseColor("#FFFFFF"));
        title_app_bar_large.setText("Discover");
        notification.setVisibility(View.GONE);
    }

    private void homeFrag() {
        home_bottom_view_buyer.setVisibility(View.VISIBLE);
        discover_bottom_view_buyer.setVisibility(View.GONE);
        profile_bottom_view_buyer.setVisibility(View.GONE);
        setting_bottom_view_buyer.setVisibility(View.GONE);
        home_img_buyer.setImageResource(R.drawable.home_selected);
        discover_img_buyer.setImageResource(R.drawable.discover_unselected);
        profile_img_buyer.setImageResource(R.drawable.profile_unselected);
        setting_img_buyer.setImageResource(R.drawable.setting_unselected);
        home_text_home_buyer.setTextColor(Color.parseColor("#fd6038"));
        discover_text_home_buyer.setTextColor(Color.parseColor("#AFBFD8"));
        profile_text_home_buyer.setTextColor(Color.parseColor("#AFBFD8"));
        setting_text_home_buyer.setTextColor(Color.parseColor("#AFBFD8"));
        home_navigator_buyer.setBackgroundColor(Color.parseColor("#E3EFF2"));
        discover_navigator_buyer.setBackgroundColor(Color.parseColor("#FFFFFF"));
        profile_navigator_buyer.setBackgroundColor(Color.parseColor("#FFFFFF"));
        setting_navigator_buyer.setBackgroundColor(Color.parseColor("#FFFFFF"));
        title_app_bar_large.setText("Home");
        notification.setVisibility(View.GONE);
    }

    private void SelectFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_home_buyer_activity, fragment);
        transaction.commit();
    }
}
