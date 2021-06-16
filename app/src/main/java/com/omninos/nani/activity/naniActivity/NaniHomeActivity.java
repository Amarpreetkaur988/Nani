package com.omninos.nani.activity.naniActivity;

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

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.omninos.nani.R;
import com.omninos.nani.activity.ProductInfoActivity;
import com.omninos.nani.activity.userActivity.BuyerHomeActivity;
import com.omninos.nani.fragments.naniFragments.AddItemFragment;
import com.omninos.nani.fragments.naniFragments.NaniOrderFragment;
import com.omninos.nani.fragments.naniFragments.NaniPostFragment;
import com.omninos.nani.fragments.naniFragments.NaniProfileFragment;
import com.omninos.nani.fragments.naniFragments.NaniSettingFragment;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;
import com.omninos.nani.utils.ConstantData;

import de.hdodenhof.circleimageview.CircleImageView;

public class NaniHomeActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mainContent;
    private DrawerLayout drawerLayout_nani;
    private ImageView appbar_large_image1;

    private RelativeLayout home_navigator_nani, discover_navigator_nani, profile_navigator_nani, setting_navigator_nani;
    private ImageView home_img_nani, discover_img_nani, profile_img_nani, setting_img_nani, notification, appbar_large_image3;
    private View home_bottom_view_nani, discover_bottom_view_nani, profile_bottom_view_nani, setting_bottom_view_nani;
    private RoundedImageView add_item_home_nani_activity;
    private TextView home_text_home_nani, discover_text_home_nani, profile_text_home_nani, setting_text_home_nani, title_app_bar_large, nav_name;


    private CircleImageView nav_pic;

    private TextView home, my_orders, payment, switch_sides;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.BlueTheme);
        setContentView(R.layout.activity_nani_home);
        CommonUtils.CheckService(NaniHomeActivity.this);
        initView();
        SetUp();
    }

    private void initView() {
        mainContent = findViewById(R.id.mainContent);
        drawerLayout_nani = findViewById(R.id.drawerLayout_nani);
        appbar_large_image1 = findViewById(R.id.appbar_large_image1);

        title_app_bar_large = findViewById(R.id.title_app_bar_large);
        notification = findViewById(R.id.appbar_large_image2);

        //navigation
        home_navigator_nani = findViewById(R.id.home_navigator_nani);
        discover_navigator_nani = findViewById(R.id.discover_navigator_nani);
        profile_navigator_nani = findViewById(R.id.profile_navigator_nani);
        setting_navigator_nani = findViewById(R.id.setting_navigator_nani);

        home_img_nani = findViewById(R.id.home_img_nani);
        discover_img_nani = findViewById(R.id.discover_img_nani);
        profile_img_nani = findViewById(R.id.profile_img_nani);
        setting_img_nani = findViewById(R.id.setting_img_nani);

        add_item_home_nani_activity = findViewById(R.id.add_item_home_nani_activity);

        home_text_home_nani = findViewById(R.id.home_text_home_nani);
        discover_text_home_nani = findViewById(R.id.discover_text_home_nani);
        profile_text_home_nani = findViewById(R.id.profile_text_home_nani);
        setting_text_home_nani = findViewById(R.id.setting_text_home_nani);

        home_bottom_view_nani = findViewById(R.id.home_bottom_view_nani);
        discover_bottom_view_nani = findViewById(R.id.discover_bottom_view_nani);
        profile_bottom_view_nani = findViewById(R.id.profile_bottom_view_nani);
        setting_bottom_view_nani = findViewById(R.id.setting_bottom_view_nani);

        nav_pic = findViewById(R.id.nav_pic);
        nav_name = findViewById(R.id.nav_name);


        home = findViewById(R.id.home);
        my_orders = findViewById(R.id.my_orders);
        payment = findViewById(R.id.payment);
        switch_sides = findViewById(R.id.switch_sides);

        appbar_large_image3 = findViewById(R.id.appbar_large_image3);
    }

    private void SetUp() {
        appbar_large_image1.setOnClickListener(this);
        home_navigator_nani.setOnClickListener(this);
        discover_navigator_nani.setOnClickListener(this);
        profile_navigator_nani.setOnClickListener(this);
        setting_navigator_nani.setOnClickListener(this);
        add_item_home_nani_activity.setOnClickListener(this);
        add_item_home_nani_activity.setOnClickListener(this);

        home.setOnClickListener(this);
        my_orders.setOnClickListener(this);
        payment.setOnClickListener(this);
        switch_sides.setOnClickListener(this);
        appbar_large_image3.setOnClickListener(this);
        setNavigationDrawer();

        SelectFragment(new NaniPostFragment());

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!App.getAppPreference().getUserDetails().getDetails().getImage().isEmpty()) {
            Glide.with(this).load(App.getAppPreference().getUserDetails().getDetails().getImage()).into(nav_pic);
        }
        nav_name.setText(App.getAppPreference().getUserDetails().getDetails().getName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.home:
                drawerLayout_nani.closeDrawer(Gravity.START);
                HomeMove();
                break;

            case R.id.my_orders:
                drawerLayout_nani.closeDrawer(Gravity.START);
                OrderMove();
                break;

            case R.id.payment:
                startActivity(new Intent(NaniHomeActivity.this, NaniPaymentActivity.class));

                break;
            case R.id.switch_sides:
                App.getAppPreference().SaveString(ConstantData.USER_TYPE, "2");
                startActivity(new Intent(NaniHomeActivity.this, BuyerHomeActivity.class).putExtra("Type","0"));
                finishAffinity();
                break;
            case R.id.appbar_large_image1:
                drawerLayout_nani.openDrawer(Gravity.START);
                break;

            case R.id.home_navigator_nani:
                HomeMove();
                break;

            case R.id.discover_navigator_nani:
                OrderMove();
                break;

            case R.id.appbar_large_image3:
                startActivity(new Intent(NaniHomeActivity.this, ProductInfoActivity.class));
                break;

            case R.id.profile_navigator_nani:
                home_bottom_view_nani.setVisibility(View.GONE);
                discover_bottom_view_nani.setVisibility(View.GONE);
                profile_bottom_view_nani.setVisibility(View.GONE);
                setting_bottom_view_nani.setVisibility(View.GONE);
                home_img_nani.setImageResource(R.drawable.post_unselect);
                discover_img_nani.setImageResource(R.drawable.order_unselected);
                profile_img_nani.setImageResource(R.drawable.profile_blue_selected);
                setting_img_nani.setImageResource(R.drawable.setting_unselected);
                home_text_home_nani.setTextColor(Color.parseColor("#AFBFD8"));
                discover_text_home_nani.setTextColor(Color.parseColor("#AFBFD8"));
                profile_text_home_nani.setTextColor(Color.parseColor("#18ACD7"));
                setting_text_home_nani.setTextColor(Color.parseColor("#AFBFD8"));
                home_navigator_nani.setBackgroundColor(Color.parseColor("#FFFFFF"));
                discover_navigator_nani.setBackgroundColor(Color.parseColor("#FFFFFF"));
                profile_navigator_nani.setBackgroundColor(Color.parseColor("#E3EFF2"));
                setting_navigator_nani.setBackgroundColor(Color.parseColor("#FFFFFF"));
                title_app_bar_large.setText("Profile");
                notification.setVisibility(View.GONE);
                appbar_large_image3.setVisibility(View.GONE);
                SelectFragment(new NaniProfileFragment());
                break;

            case R.id.setting_navigator_nani:
                home_bottom_view_nani.setVisibility(View.GONE);
                discover_bottom_view_nani.setVisibility(View.GONE);
                profile_bottom_view_nani.setVisibility(View.GONE);
                setting_bottom_view_nani.setVisibility(View.GONE);
                home_img_nani.setImageResource(R.drawable.post_unselect);
                discover_img_nani.setImageResource(R.drawable.order_unselected);
                profile_img_nani.setImageResource(R.drawable.profile_unselected);
                setting_img_nani.setImageResource(R.drawable.setting_blue_selected);
                home_text_home_nani.setTextColor(Color.parseColor("#AFBFD8"));
                discover_text_home_nani.setTextColor(Color.parseColor("#AFBFD8"));
                profile_text_home_nani.setTextColor(Color.parseColor("#AFBFD8"));
                setting_text_home_nani.setTextColor(Color.parseColor("#18ACD7"));
                home_navigator_nani.setBackgroundColor(Color.parseColor("#FFFFFF"));
                discover_navigator_nani.setBackgroundColor(Color.parseColor("#FFFFFF"));
                profile_navigator_nani.setBackgroundColor(Color.parseColor("#FFFFFF"));
                setting_navigator_nani.setBackgroundColor(Color.parseColor("#E3EFF2"));
                title_app_bar_large.setText("Settings");
                notification.setVisibility(View.GONE);
                appbar_large_image3.setVisibility(View.GONE);
                SelectFragment(new NaniSettingFragment());
                break;

            case R.id.add_item_home_nani_activity:
                home_bottom_view_nani.setVisibility(View.GONE);
                discover_bottom_view_nani.setVisibility(View.GONE);
                profile_bottom_view_nani.setVisibility(View.GONE);
                setting_bottom_view_nani.setVisibility(View.GONE);
                home_img_nani.setImageResource(R.drawable.post_unselect);
                discover_img_nani.setImageResource(R.drawable.order_unselected);
                profile_img_nani.setImageResource(R.drawable.profile_unselected);
                setting_img_nani.setImageResource(R.drawable.setting_unselected);
                home_text_home_nani.setTextColor(Color.parseColor("#AFBFD8"));
                discover_text_home_nani.setTextColor(Color.parseColor("#AFBFD8"));
                profile_text_home_nani.setTextColor(Color.parseColor("#AFBFD8"));
                setting_text_home_nani.setTextColor(Color.parseColor("#AFBFD8"));
                home_navigator_nani.setBackgroundColor(Color.parseColor("#FFFFFF"));
                discover_navigator_nani.setBackgroundColor(Color.parseColor("#FFFFFF"));
                profile_navigator_nani.setBackgroundColor(Color.parseColor("#FFFFFF"));
                setting_navigator_nani.setBackgroundColor(Color.parseColor("#FFFFFF"));
                title_app_bar_large.setText("Add Item");
                notification.setVisibility(View.GONE);
                appbar_large_image3.setVisibility(View.VISIBLE);
                SelectFragment(new AddItemFragment());
                break;

        }
    }

    private void OrderMove() {
        drawerLayout_nani.closeDrawer(Gravity.START);
        home_bottom_view_nani.setVisibility(View.GONE);
        discover_bottom_view_nani.setVisibility(View.GONE);
        profile_bottom_view_nani.setVisibility(View.GONE);
        setting_bottom_view_nani.setVisibility(View.GONE);
        home_img_nani.setImageResource(R.drawable.post_unselect);
        discover_img_nani.setImageResource(R.drawable.blue_order);
        profile_img_nani.setImageResource(R.drawable.profile_unselected);
        setting_img_nani.setImageResource(R.drawable.setting_unselected);
        home_text_home_nani.setTextColor(Color.parseColor("#AFBFD8"));
        discover_text_home_nani.setTextColor(Color.parseColor("#18ACD7"));
        profile_text_home_nani.setTextColor(Color.parseColor("#AFBFD8"));
        setting_text_home_nani.setTextColor(Color.parseColor("#AFBFD8"));
        home_navigator_nani.setBackgroundColor(Color.parseColor("#FFFFFF"));
        discover_navigator_nani.setBackgroundColor(Color.parseColor("#E3EFF2"));
        profile_navigator_nani.setBackgroundColor(Color.parseColor("#FFFFFF"));
        setting_navigator_nani.setBackgroundColor(Color.parseColor("#FFFFFF"));
        title_app_bar_large.setText("My Order");
        notification.setVisibility(View.GONE);

        appbar_large_image3.setVisibility(View.GONE);
        SelectFragment(new NaniOrderFragment());
    }

    private void HomeMove() {
        home_bottom_view_nani.setVisibility(View.GONE);
        discover_bottom_view_nani.setVisibility(View.GONE);
        profile_bottom_view_nani.setVisibility(View.GONE);
        setting_bottom_view_nani.setVisibility(View.GONE);
        home_img_nani.setImageResource(R.drawable.ic_post);
        discover_img_nani.setImageResource(R.drawable.order_unselected);
        profile_img_nani.setImageResource(R.drawable.profile_unselected);
        setting_img_nani.setImageResource(R.drawable.setting_unselected);
        home_text_home_nani.setTextColor(Color.parseColor("#18ACD7"));
        discover_text_home_nani.setTextColor(Color.parseColor("#AFBFD8"));
        profile_text_home_nani.setTextColor(Color.parseColor("#AFBFD8"));
        setting_text_home_nani.setTextColor(Color.parseColor("#AFBFD8"));
        home_navigator_nani.setBackgroundColor(Color.parseColor("#E3EFF2"));
        discover_navigator_nani.setBackgroundColor(Color.parseColor("#FFFFFF"));
        profile_navigator_nani.setBackgroundColor(Color.parseColor("#FFFFFF"));
        setting_navigator_nani.setBackgroundColor(Color.parseColor("#FFFFFF"));
        title_app_bar_large.setText("My Post");
        notification.setVisibility(View.GONE);

        appbar_large_image3.setVisibility(View.GONE);
        SelectFragment(new NaniPostFragment());
    }

    private void setNavigationDrawer() {
        drawerLayout_nani.setScrimColor(Color.TRANSPARENT);
        drawerLayout_nani.addDrawerListener(new DrawerLayout.DrawerListener() {
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

    private void SelectFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_home_nani_activity, fragment);
        transaction.commit();
    }

}
