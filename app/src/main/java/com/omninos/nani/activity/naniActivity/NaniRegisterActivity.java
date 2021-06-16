package com.omninos.nani.activity.naniActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hbb20.CountryCodePicker;
import com.omninos.nani.R;
import com.omninos.nani.activity.SearchPlaceActivity;
import com.omninos.nani.myViewModel.LoginRegisterViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;

import java.util.Map;

public class NaniRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout NaniLoginButton;
    private Button sign_up_button_signup;
    private CountryCodePicker ccp;
    private EditText nameEditText, emailEditText, addressEditText, numberEditText, passwordEditText, confirmPasswordEditText, idNumberEditText;
    private String name, emai, addesss, number, password, confirmPass, countryCode, IdNumber;

    private LoginRegisterViewModel viewModel;
    private CardView password_card_signup, confirm_password_card_signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.BlueTheme);
        setContentView(R.layout.activity_nani_register);
        CommonUtils.CheckService(NaniRegisterActivity.this);
        viewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel.class);

        initView();
        SetUp();

        checkSocialData();
    }

    private void checkSocialData() {
        if (App.getSingleton().getSocialId() != null) {
            password_card_signup.setVisibility(View.GONE);
            confirm_password_card_signup.setVisibility(View.GONE);
            nameEditText.setText(App.getSingleton().getName());
            emailEditText.setText(App.getSingleton().getEmail());
        } else {
            password_card_signup.setVisibility(View.VISIBLE);
            confirm_password_card_signup.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        NaniLoginButton = findViewById(R.id.NaniLoginButton);
        sign_up_button_signup = findViewById(R.id.sign_up_button_signup);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        addressEditText = findViewById(R.id.addressEditText);
        numberEditText = findViewById(R.id.numberEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        ccp = findViewById(R.id.ccp);
        password_card_signup = findViewById(R.id.password_card_signup);
        confirm_password_card_signup = findViewById(R.id.confirm_password_card_signup);
        idNumberEditText = findViewById(R.id.idNumberEditText);


        countryCode = ccp.getSelectedCountryCodeWithPlus();
    }

    private void SetUp() {
        NaniLoginButton.setOnClickListener(this);
        sign_up_button_signup.setOnClickListener(this);
        addressEditText.setOnClickListener(this);

        ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countryCode = ccp.getSelectedCountryCodeWithPlus();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.NaniLoginButton:
                onBackPressed();
                break;
            case R.id.sign_up_button_signup:
                if (App.getSingleton() != null) {
                    Socialvalid();
                } else {
                    Validate();
                }

//                startActivity(new Intent(NaniRegisterActivity.this, NaniOtpVerification.class));
                break;
            case R.id.addressEditText:
                startActivity(new Intent(NaniRegisterActivity.this, SearchPlaceActivity.class));
                break;
        }
    }

    private void Socialvalid() {
        name = nameEditText.getText().toString();
        emai = emailEditText.getText().toString();
        addesss = addressEditText.getText().toString();
        number = numberEditText.getText().toString();
        IdNumber = idNumberEditText.getText().toString();

        if (name.isEmpty()) {
            CommonUtils.showSnackbarAlert(sign_up_button_signup, "enter name");
        } else if (emai.isEmpty()) {
            CommonUtils.showSnackbarAlert(sign_up_button_signup, "enter email");
        } else if (IdNumber.isEmpty()) {
            CommonUtils.showSnackbarAlert(sign_up_button_signup, "enter Id Number");
        } else if (addesss.isEmpty()) {
            CommonUtils.showSnackbarAlert(sign_up_button_signup, "enter address");
        } else if (number.isEmpty()) {
            CommonUtils.showSnackbarAlert(sign_up_button_signup, "enter mobile number");
        } else {
            CheckEmailAndPhoneNumber();
        }

    }

    private void Validate() {
        name = nameEditText.getText().toString();
        emai = emailEditText.getText().toString();
        IdNumber = idNumberEditText.getText().toString();
        addesss = addressEditText.getText().toString();
        number = numberEditText.getText().toString();
        password = passwordEditText.getText().toString();
        confirmPass = confirmPasswordEditText.getText().toString();
        if (name.isEmpty()) {
            CommonUtils.showSnackbarAlert(sign_up_button_signup, "enter name");
        } else if (emai.isEmpty()) {
            CommonUtils.showSnackbarAlert(sign_up_button_signup, "enter email");
        } else if (IdNumber.isEmpty()) {
            CommonUtils.showSnackbarAlert(sign_up_button_signup, "enter Id Number");
        } else if (addesss.isEmpty()) {
            CommonUtils.showSnackbarAlert(sign_up_button_signup, "enter address");
        } else if (number.isEmpty()) {
            CommonUtils.showSnackbarAlert(sign_up_button_signup, "enter mobile number");
        } else if (password.isEmpty() || password.length() < 5) {
            CommonUtils.showSnackbarAlert(sign_up_button_signup, "enter minimum 6 character password");
        } else if (confirmPass.isEmpty() || !password.equals(confirmPass)) {
            CommonUtils.showSnackbarAlert(sign_up_button_signup, "password mismatch");
        } else {
            CheckEmailAndPhoneNumber();
        }
    }

    private void CheckEmailAndPhoneNumber() {
        viewModel.NumberAndEmail(NaniRegisterActivity.this, number, emai).observe(NaniRegisterActivity.this, new Observer<Map>() {
            @Override
            public void onChanged(@Nullable Map map) {
                if (map.get("success").equals("1")) {
                    App.getSingleton().setName(name);
                    App.getSingleton().setEmail(emai);
                    App.getSingleton().setPhone(countryCode + number);
                    App.getSingleton().setPassword(password);
                    App.getSingleton().setIdNumber(IdNumber);
                    startActivity(new Intent(NaniRegisterActivity.this, BankDetailsActivity.class));
                } else {
                    CommonUtils.showSnackbarAlert(nameEditText, map.get("message").toString());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (App.getSingleton().getNaniAddress() != null) {
            addressEditText.setText(App.getSingleton().getNaniAddress());
        }
    }
}
