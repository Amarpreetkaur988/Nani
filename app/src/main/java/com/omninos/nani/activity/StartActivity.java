package com.omninos.nani.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.omninos.nani.R;
import com.omninos.nani.activity.naniActivity.NaniLoginActivity;
import com.omninos.nani.activity.userActivity.BuyerLoginActivity;
import com.omninos.nani.adapter.GetStartedPagerAdapter;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager aboutPager;
    private LinearLayout dots;
    private Button nani_button, buyer_button;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        activity = StartActivity.this;
        initIds();
        setOnClicks();

        GetStartedPagerAdapter getStartedPagerAdapter = new GetStartedPagerAdapter(getSupportFragmentManager());
        aboutPager.setAdapter(getStartedPagerAdapter);
        addDotsIndicator(0);

        aboutPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                addDotsIndicator(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    private void setOnClicks() {
        nani_button.setOnClickListener(this);
        buyer_button.setOnClickListener(this);
    }

    private void initIds() {
        aboutPager = findViewById(R.id.get_started_pager);
        dots = findViewById(R.id.dots);
        nani_button = findViewById(R.id.nani_button_get_started);
        buyer_button = findViewById(R.id.buyer_button_get_started);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nani_button_get_started:
                startActivity(new Intent(StartActivity.this, NaniLoginActivity.class));
                break;

            case R.id.buyer_button_get_started:
                startActivity(new Intent(StartActivity.this, BuyerLoginActivity.class));
                break;
        }
    }

    private void addDotsIndicator(int position) {
        TextView[] tv_dots = new TextView[3];
        dots.removeAllViews();
        for (int i = 0; i < tv_dots.length; i++) {
            tv_dots[i] = new TextView(getApplicationContext());
            tv_dots[i].setText("•");
            tv_dots[i].setTextSize(40);
            tv_dots[i].setTextColor(getResources().getColor(R.color.app_gray));
            dots.addView(tv_dots[i]);
        }
        tv_dots[position].setTextColor(Color.parseColor("#fd6038"));
    }

}
