package com.omninos.nani.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.omninos.nani.R;
import com.omninos.nani.services.MyService;
import com.omninos.nani.utils.CommonUtils;
import com.omninos.nani.utils.ConstantData;

public class NoInternetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        startService(new Intent(getBaseContext(), MyService.class));
        final Button tryAgainButton = findViewById(R.id.tryAgainButton);
//
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConstantData.EnableInternet.equalsIgnoreCase("0")) {
                    ConstantData.EnableInternet = "1";
                    finish();
                } else {
                    CommonUtils.showSnackbarAlert(tryAgainButton, "Please connect to internet");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(NoInternetActivity.this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        }, new IntentFilter("update"));
    }

    @Override
    public void onBackPressed() {

    }
}
