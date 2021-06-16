package com.omninos.nani.activity.userActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.omninos.nani.R;
import com.omninos.nani.modelClass.BuyerOrderModel;

public class OrderDetailsActivity extends AppCompatActivity {

    private TextView confirming_order_text, food_is_being_prepared_text, courier_is_on_the_way_text, food_delivered_text, order_no;
    private BuyerOrderModel.Detail detail;
    private View firstView, secondView, thirdView;
    private ImageView appbar_transparent_image1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        initView();
        SetUp();
    }

    private void initView() {
        confirming_order_text = findViewById(R.id.confirming_order_text);
        food_is_being_prepared_text = findViewById(R.id.food_is_being_prepared_text);
        courier_is_on_the_way_text = findViewById(R.id.courier_is_on_the_way_text);
        food_delivered_text = findViewById(R.id.food_delivered_text);
        order_no = findViewById(R.id.order_no);

        firstView = findViewById(R.id.firstView);
        secondView = findViewById(R.id.secondView);
        thirdView = findViewById(R.id.thirdView);
        appbar_transparent_image1 = findViewById(R.id.appbar_transparent_image1);

    }

    private void SetUp() {
        detail = (BuyerOrderModel.Detail) getIntent().getSerializableExtra("Details");

        order_no.setText("Order #ORD00" + detail.getId());
        if (detail.getStatus().equalsIgnoreCase("1")) {
            confirming_order_text.setTextColor(getResources().getColor(R.color.orange));
            confirming_order_text.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_circle), null, null, null);
        } else if (detail.getStatus().equalsIgnoreCase("2")) {
            firstView.setBackgroundColor(getResources().getColor(R.color.orange));
            confirming_order_text.setTextColor(getResources().getColor(R.color.orange));
            confirming_order_text.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_circle), null, null, null);
            food_is_being_prepared_text.setTextColor(getResources().getColor(R.color.orange));
            food_is_being_prepared_text.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_circle), null, null, null);
        } else if (detail.getStatus().equalsIgnoreCase("3")) {
            firstView.setBackgroundColor(getResources().getColor(R.color.orange));
            secondView.setBackgroundColor(getResources().getColor(R.color.orange));
            confirming_order_text.setTextColor(getResources().getColor(R.color.orange));
            confirming_order_text.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_circle), null, null, null);
            food_is_being_prepared_text.setTextColor(getResources().getColor(R.color.orange));
            food_is_being_prepared_text.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_circle), null, null, null);

            courier_is_on_the_way_text.setTextColor(getResources().getColor(R.color.orange));
            courier_is_on_the_way_text.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_circle), null, null, null);
        } else if (detail.getStatus().equalsIgnoreCase("4")) {
            firstView.setBackgroundColor(getResources().getColor(R.color.orange));
            secondView.setBackgroundColor(getResources().getColor(R.color.orange));
            thirdView.setBackgroundColor(getResources().getColor(R.color.orange));
            confirming_order_text.setTextColor(getResources().getColor(R.color.orange));
            confirming_order_text.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_circle), null, null, null);
            food_is_being_prepared_text.setTextColor(getResources().getColor(R.color.orange));
            food_is_being_prepared_text.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_circle), null, null, null);

            courier_is_on_the_way_text.setTextColor(getResources().getColor(R.color.orange));
            courier_is_on_the_way_text.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_circle), null, null, null);

            food_delivered_text.setTextColor(getResources().getColor(R.color.orange));
            food_delivered_text.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_circle), null, null, null);
        }

        appbar_transparent_image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
