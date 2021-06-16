package com.omninos.nani.activity.naniActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.omninos.nani.R;

public class NotifiedActivity extends AppCompatActivity {

    private Button okayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notified);


        initView();
        SetUp();
    }

    private void initView() {
        okayButton = findViewById(R.id.okayButton);
    }

    private void SetUp() {
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotifiedActivity.this, NaniOtpVerification.class));
                finishAffinity();
            }
        });
    }
}
