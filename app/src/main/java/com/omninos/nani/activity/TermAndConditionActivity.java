package com.omninos.nani.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.omninos.nani.R;

public class TermAndConditionActivity extends AppCompatActivity {


    private WebView webWew;
    ProgressDialog pd;
    private RelativeLayout app_bar_edit_profile;
    private ImageView appbar_transparent_image1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getStringExtra("Type").equalsIgnoreCase("1")) {
            setTheme(R.style.BlueTheme);
        } else {
            //        lLayout.setBackgroundColor(Color.parseColor("#000000"));
        }
        setContentView(R.layout.activity_term_and_condition);

        initView();
        SetUp();
    }

    private void initView() {
        webWew = findViewById(R.id.webWew);
        appbar_transparent_image1 = findViewById(R.id.appbar_transparent_image1);
        app_bar_edit_profile = findViewById(R.id.app_bar_edit_profile);
        if (getIntent().getStringExtra("Type").equalsIgnoreCase("2")){
            app_bar_edit_profile.setBackgroundColor(Color.parseColor("#fd6038"));
        }
    }


    private void SetUp() {
        pd = new ProgressDialog(TermAndConditionActivity.this);
        pd.setMessage("Loading...");
        pd.show();
        WebSettings webSettings = webWew.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webWew.setWebViewClient(new MyWebViewClient());
        webWew.loadUrl("http://13.234.148.86/naniApplication/index.php/api/user/getTermsAndConditions");

        appbar_transparent_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            if (!pd.isShowing()) {
                pd.show();
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("on finish");
            if (pd.isShowing()) {
                pd.dismiss();
            }

        }
    }
}
