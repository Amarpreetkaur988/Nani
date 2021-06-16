package com.omninos.nani.activity.userActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.omninos.nani.R;
import com.omninos.nani.myViewModel.OrderViewModel;
import com.omninos.nani.utils.App;

import java.util.Map;

public class PayfastActivity extends AppCompatActivity {

    private WebView wab;
    private OrderViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payfast);

        viewModel = ViewModelProviders.of(this).get(OrderViewModel.class);

        initView();
        Setups();

    }

    private void Setups() {

    }

    private void initView() {

        wab = findViewById(R.id.webView);


        wab.getSettings().setJavaScriptEnabled(true);
        wab.setWebViewClient(new MyWebViewClient());
        wab.setWebChromeClient(new MyWebChromeClient());
        wab.loadUrl("http://13.234.148.86/naniApplication/index.php/api/user/payment?price=" + getIntent().getStringExtra("amount"));
//        wab.loadUrl("http://13.234.148.86/naniApplication/index.php/api/user/payment?price=10");


//        String js = "javascript:(function(){" +
//                "l=document.getElementsByClassName('pri_btn')[0];" +
//                "l.click();" +
//                "})()";
//
//        wab.evaluateJavascript(js, new ValueCallback<String>() {
//            @Override
//            public void onReceiveValue(String s) {
//                String result = s;
//            }
//        });
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            view.loadUrl("javascript:alert(functionThatReturnsSomething())");
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.equalsIgnoreCase("http://13.234.148.86/naniApplication/index.php/api/user/cancelPayment")) {
                Toast.makeText(PayfastActivity.this, "Payment Cancel", Toast.LENGTH_SHORT).show();
                finish();
            } else if (url.equalsIgnoreCase("http://13.234.148.86/naniApplication/index.php/api/user/ReturnPayment")) {
                Toast.makeText(PayfastActivity.this, "Success", Toast.LENGTH_SHORT).show();
                HitApi();
            }
            return false;
        }
    }

    private void HitApi() {
        viewModel.OrderBook(PayfastActivity.this, App.getSingleton().getOrderPostId(), App.getSingleton().getQuantityData(), App.getSingleton().getNaniAddress(), "", "", "", App.getSingleton().getNaniLat(), App.getSingleton().getNaniLng(), App.getSingleton().getLocationType(), getIntent().getStringExtra("amount"), App.getAppPreference().getUserDetails().getDetails().getId()).observe(PayfastActivity.this, new Observer<Map>() {
            @Override
            public void onChanged(Map map) {
                if (map.get("success").toString().equalsIgnoreCase("1")) {
                    Toast.makeText(PayfastActivity.this, map.get("message").toString(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PayfastActivity.this, BuyerHomeActivity.class).putExtra("Type","0"));
                    finishAffinity();
                } else {
                    Toast.makeText(PayfastActivity.this, map.get("message").toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d("LogTag", message);
            result.confirm();
            return true;
        }
    }

}
