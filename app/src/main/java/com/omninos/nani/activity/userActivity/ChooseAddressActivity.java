package com.omninos.nani.activity.userActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.omninos.nani.R;
import com.omninos.nani.activity.SearchPlaceActivity;
import com.omninos.nani.myViewModel.OrderViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChooseAddressActivity extends AppCompatActivity implements View.OnClickListener {

    TextView title;
    Button proceed_to_pay;
    ImageView back;
    ChooseAddressActivity activity;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private EditText addressData, pinCode, houseNumber, landMark;
    private RelativeLayout homeButton, workButton, otherButton;
    private CircleImageView other_img, work_img, home_img;
    private String addressName, postalCode, HouseNo, LandMark, LocationType = "Home", Latitude, Longitude, priceText;
    int sdk;
    private OrderViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_address);

        viewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        sdk = android.os.Build.VERSION.SDK_INT;
        initIds();
        setClicks();

    }


    private void initIds() {
        title = findViewById(R.id.title_app_bar_transparent);
        title.setText("Choose Address");
        title.setTextColor(Color.BLACK);
        back = findViewById(R.id.appbar_transparent_image1);
        proceed_to_pay = findViewById(R.id.pay_btn_chosse_aadress);
        activity = ChooseAddressActivity.this;

        addressData = findViewById(R.id.addressData);
        pinCode = findViewById(R.id.pinCode);

        homeButton = findViewById(R.id.homeButton);
        workButton = findViewById(R.id.workButton);
        otherButton = findViewById(R.id.otherButton);
        other_img = findViewById(R.id.other_img);
        work_img = findViewById(R.id.work_img);
        home_img = findViewById(R.id.home_img);

        houseNumber = findViewById(R.id.houseNumber);
        landMark = findViewById(R.id.landMark);
    }

    private void setClicks() {
        proceed_to_pay.setOnClickListener(this);
        back.setOnClickListener(this);
        addressData.setOnClickListener(this);

        homeButton.setOnClickListener(this);
        workButton.setOnClickListener(this);
        otherButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_btn_chosse_aadress:
                Checkvalidation();

                break;
            case R.id.appbar_transparent_image1:
                finish();
                break;

            case R.id.addressData:
                startActivity(new Intent(ChooseAddressActivity.this, SearchPlaceActivity.class));
                break;

            case R.id.homeButton:
                LocationType = "Home";
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    home_img.setBackgroundDrawable(ContextCompat.getDrawable(ChooseAddressActivity.this, R.drawable.shape));
                    home_img.setImageDrawable(getDrawable(R.drawable.profile_buyer_home));
                    work_img.setBackgroundDrawable(ContextCompat.getDrawable(ChooseAddressActivity.this, R.drawable.shape_dark));
                    work_img.setImageDrawable(getDrawable(R.drawable.ic_office_briefcase));
                    other_img.setBackgroundDrawable(ContextCompat.getDrawable(ChooseAddressActivity.this, R.drawable.shape_dark));
                    other_img.setImageDrawable(getDrawable(R.drawable.location));
                } else {
                    home_img.setBackground(ContextCompat.getDrawable(ChooseAddressActivity.this, R.drawable.shape));
                    home_img.setImageDrawable(getDrawable(R.drawable.profile_buyer_home));
                    work_img.setBackgroundDrawable(ContextCompat.getDrawable(ChooseAddressActivity.this, R.drawable.shape_dark));
                    work_img.setImageDrawable(getDrawable(R.drawable.ic_office_briefcase));
                    other_img.setBackgroundDrawable(ContextCompat.getDrawable(ChooseAddressActivity.this, R.drawable.shape_dark));
                    other_img.setImageDrawable(getDrawable(R.drawable.location));
                }
                break;

            case R.id.workButton:
                LocationType = "Work";
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    work_img.setBackgroundDrawable(ContextCompat.getDrawable(ChooseAddressActivity.this, R.drawable.shape));
                    work_img.setImageDrawable(getDrawable(R.drawable.office_briefcase));
                    home_img.setBackgroundDrawable(ContextCompat.getDrawable(ChooseAddressActivity.this, R.drawable.shape_dark));
                    home_img.setImageDrawable(getDrawable(R.drawable.grey_buyer_home));
                    other_img.setBackgroundDrawable(ContextCompat.getDrawable(ChooseAddressActivity.this, R.drawable.shape_dark));
                    other_img.setImageDrawable(getDrawable(R.drawable.location));
                } else {
                    work_img.setBackground(ContextCompat.getDrawable(ChooseAddressActivity.this, R.drawable.shape));
                    work_img.setImageDrawable(getDrawable(R.drawable.office_briefcase));
                    home_img.setBackgroundDrawable(ContextCompat.getDrawable(ChooseAddressActivity.this, R.drawable.shape_dark));
                    home_img.setImageDrawable(getDrawable(R.drawable.grey_buyer_home));
                    other_img.setBackgroundDrawable(ContextCompat.getDrawable(ChooseAddressActivity.this, R.drawable.shape_dark));
                    other_img.setImageDrawable(getDrawable(R.drawable.location));
                }
                break;

            case R.id.otherButton:
                LocationType = "Other";
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    other_img.setBackgroundDrawable(ContextCompat.getDrawable(ChooseAddressActivity.this, R.drawable.shape));
                    other_img.setImageDrawable(getDrawable(R.drawable.white_location));
                    work_img.setBackgroundDrawable(ContextCompat.getDrawable(ChooseAddressActivity.this, R.drawable.shape_dark));
                    work_img.setImageDrawable(getDrawable(R.drawable.ic_office_briefcase));
                    home_img.setBackgroundDrawable(ContextCompat.getDrawable(ChooseAddressActivity.this, R.drawable.shape_dark));
                    home_img.setImageDrawable(getDrawable(R.drawable.grey_buyer_home));
                } else {
                    other_img.setBackground(ContextCompat.getDrawable(ChooseAddressActivity.this, R.drawable.shape));
                    other_img.setImageDrawable(getDrawable(R.drawable.white_location));
                    work_img.setBackgroundDrawable(ContextCompat.getDrawable(ChooseAddressActivity.this, R.drawable.shape_dark));
                    work_img.setImageDrawable(getDrawable(R.drawable.ic_office_briefcase));
                    home_img.setBackgroundDrawable(ContextCompat.getDrawable(ChooseAddressActivity.this, R.drawable.shape_dark));
                    home_img.setImageDrawable(getDrawable(R.drawable.grey_buyer_home));
                }
                break;
        }
    }

    private void Checkvalidation() {
        addressName = addressData.getText().toString();
        postalCode = pinCode.getText().toString();
        HouseNo = houseNumber.getText().toString();
        LandMark = landMark.getText().toString();
        if (addressName.isEmpty()) {
            CommonUtils.showSnackbarAlert(addressData, "enter address");
//        } else if (postalCode.isEmpty()) {
//            CommonUtils.showSnackbarAlert(pinCode, "enter postal code");
//        } else if (HouseNo.isEmpty()) {
//            CommonUtils.showSnackbarAlert(houseNumber, "enter house/flat no");
//        } else if (LandMark.isEmpty()) {
//            CommonUtils.showSnackbarAlert(landMark, "enter landmark");
//        } else if (LocationType.isEmpty()) {
//            CommonUtils.showSnackbarAlert(landMark, "choose location type");
        } else {
            getDeliveryAmt();
        }
    }

    private void getDeliveryAmt() {
        viewModel.getCharges(ChooseAddressActivity.this, App.getSingleton().getOrderPostId(), App.getSingleton().getNaniLat(), App.getSingleton().getNaniLng()).observe(ChooseAddressActivity.this, new Observer<Map>() {
            @Override
            public void onChanged(Map map) {
                if (map.get("success").toString().equalsIgnoreCase("1")) {
                    getCart(map.get("totalAmount").toString());
                } else {
                    Toast.makeText(activity, map.get("message").toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        addressData.setText(App.getSingleton().getNaniAddress());
    }

    private void getCart(String delAmt) {
        LayoutInflater factory = LayoutInflater.from(activity);
        final View congDialogBox = factory.inflate(R.layout.cart_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        TextView spring_roll_cart_text = congDialogBox.findViewById(R.id.spring_roll_cart_text);
        TextView _15_00_text = congDialogBox.findViewById(R.id._15_00_text);
        TextView _15_00_text_total = congDialogBox.findViewById(R.id._15_00_text_total);
        TextView delivery_price_text = congDialogBox.findViewById(R.id.delivery_price_text);
        TextView total_price_text_cart = congDialogBox.findViewById(R.id.total_price_text_cart);
        TextView quantity = congDialogBox.findViewById(R.id.quantity_text_cart);
        CircleImageView food_image_cart = congDialogBox.findViewById(R.id.food_image_cart);

        Glide.with(ChooseAddressActivity.this).load(App.getSingleton().getFoodImg()).into(food_image_cart);

        spring_roll_cart_text.setText(App.getSingleton().getNameData());
        quantity.setText(App.getSingleton().getQuantityData());
        String fdata= String.valueOf(Double.valueOf(App.getSingleton().getQuantityData()) * Double.valueOf(App.getSingleton().getPriceData()));

        _15_00_text_total.setText("R" +String.format("%.2f", Double.parseDouble(fdata)));
        delivery_price_text.setText("R" + String.format("%.2f", Double.parseDouble(delAmt)));
        String data = String.valueOf((Double.valueOf(App.getSingleton().getQuantityData()) * Double.valueOf(App.getSingleton().getPriceData())) + Double.parseDouble(delAmt));
        String numberAsString = String.format("%.2f", Double.parseDouble(data));
        System.out.println("Data: " + numberAsString);
        total_price_text_cart.setText("R " + numberAsString);
        priceText = String.valueOf((Double.valueOf(App.getSingleton().getQuantityData()) * Double.valueOf(App.getSingleton().getPriceData())) + Double.parseDouble(delAmt));
        dialog.setView(congDialogBox);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button pay = congDialogBox.findViewById(R.id.pay_btn_cart_dialog);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BookingOrder();
                App.getSingleton().setLocationType(LocationType);
                App.getSingleton().setPrice(String.format("%.2f", Double.parseDouble(priceText)));
                startActivity(new Intent(ChooseAddressActivity.this, PayfastActivity.class).putExtra("amount", priceText));
            }
        });

        dialog.show();
    }
}
