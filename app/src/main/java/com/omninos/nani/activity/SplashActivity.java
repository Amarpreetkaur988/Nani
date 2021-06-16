package com.omninos.nani.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.omninos.nani.R;
import com.omninos.nani.activity.naniActivity.NaniHomeActivity;
import com.omninos.nani.activity.userActivity.BuyerHomeActivity;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.ConstantData;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();

    }

    private void init() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                    if (App.getAppPreference().GetString(ConstantData.TOKEN).equalsIgnoreCase("1")) {
                        if (App.getAppPreference().GetString(ConstantData.USER_TYPE).equalsIgnoreCase("1")) {
                            startActivity(new Intent(SplashActivity.this, NaniHomeActivity.class));
                            finishAffinity();
                        } else {
                            startActivity(new Intent(SplashActivity.this, BuyerHomeActivity.class).putExtra("Type", "0"));
                            finishAffinity();
                        }
                    } else {
                        startActivity(new Intent(SplashActivity.this, StartActivity.class));
                        finishAffinity();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }
}
