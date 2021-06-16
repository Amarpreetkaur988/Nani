package com.omninos.nani.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.omninos.nani.R;

public class ProductInfoActivity extends AppCompatActivity {

    private WebView webWew;
    private ImageView appbar_transparent_image1;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.BlueTheme);
        setContentView(R.layout.activity_product_info);

        initView();
        SetUp();
    }

    private void initView() {
        webWew = findViewById(R.id.webWew);
        appbar_transparent_image1 = findViewById(R.id.appbar_transparent_image1);
    }

    private void SetUp() {
        pd = new ProgressDialog(ProductInfoActivity.this);
        pd.setMessage("Loading...");
        pd.show();
        WebSettings webSettings = webWew.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webWew.setWebViewClient(new MyWebViewClient());
        webWew.loadUrl("http://13.234.148.86/naniApplication/index.php/api/user/getProductInfo");

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
