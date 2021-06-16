package com.omninos.nani.activity.userActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.iid.FirebaseInstanceId;
import com.hbb20.CountryCodePicker;
import com.omninos.nani.R;
import com.omninos.nani.modelClass.LoginRegisterModelClass;
import com.omninos.nani.myViewModel.LoginRegisterViewModel;
import com.omninos.nani.myViewModel.SocialLoginViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;
import com.omninos.nani.utils.ConstantData;

public class BuyerRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout BuyerLoginButton;
    private Button sign_up_button_signup;

    private CountryCodePicker ccp;

    private EditText nameEditText, emailEditText, numberEditText, passwordEditText, confirmPasswordEditText;
    private String name, email, password, confirmPass, countryCode, number;

    private LoginRegisterViewModel viewModel;
    private SocialLoginViewModel socialLoginViewModel;
    private CardView password_card_signup, confirm_password_card_signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_register);

        viewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel.class);
        socialLoginViewModel = ViewModelProviders.of(this).get(SocialLoginViewModel.class);

        CommonUtils.CheckService(BuyerRegisterActivity.this);
        initView();
        SetUp();
        checksocialidstatus();
    }

    private void checksocialidstatus() {
        if (App.getSingleton().getSocialId() != null) {
            password_card_signup.setVisibility(View.GONE);
            confirm_password_card_signup.setVisibility(View.GONE);
            emailEditText.setText(getIntent().getStringExtra("email"));
            nameEditText.setText(getIntent().getStringExtra("name"));
            //imagepath = getIntent().getStringExtra("image");

        } else {
            password_card_signup.setVisibility(View.VISIBLE);
            confirm_password_card_signup.setVisibility(View.VISIBLE);
        }
    }


    private void initView() {
        BuyerLoginButton = findViewById(R.id.BuyerLoginButton);
        sign_up_button_signup = findViewById(R.id.sign_up_button_signup);

        ccp = findViewById(R.id.ccp);
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        numberEditText = findViewById(R.id.numberEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);

        password_card_signup = findViewById(R.id.password_card_signup);
        confirm_password_card_signup = findViewById(R.id.confirm_password_card_signup);
    }

    private void SetUp() {
        BuyerLoginButton.setOnClickListener(this);
        sign_up_button_signup.setOnClickListener(this);

        countryCode = ccp.getSelectedCountryCodeWithPlus();


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
            case R.id.BuyerLoginButton:
                onBackPressed();
                break;
            case R.id.sign_up_button_signup:
                if (App.getSingleton().getSocialId() != null) {
                    SocialLoginValidation();
                } else {
                    Register();
                }
                break;
        }
    }

    private void SocialLoginValidation() {
        name = nameEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        number = numberEditText.getText().toString().trim();
        if (name.isEmpty()) {
            CommonUtils.showSnackbarAlert(nameEditText, "enter name");
        } else if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            CommonUtils.showSnackbarAlert(emailEditText, "enter email");
        } else if (number.isEmpty()) {
            CommonUtils.showSnackbarAlert(numberEditText, "enter phone number");
        } else {
            sociallogin();
        }

    }

    private void sociallogin() {
        socialLoginViewModel.RegisterSocial(BuyerRegisterActivity.this, name, email, countryCode + number, App.getSingleton().getSocialId(), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "android", FirebaseInstanceId.getInstance().getToken(), App.getSingleton().getLogintype(),"").observe(BuyerRegisterActivity.this, new Observer<LoginRegisterModelClass>() {
            @Override
            public void onChanged(LoginRegisterModelClass loginRegisterModelClass) {
                if (loginRegisterModelClass.getSuccess().equalsIgnoreCase("1")) {
                    Toast.makeText(BuyerRegisterActivity.this, loginRegisterModelClass.getDetails().getOtp(), Toast.LENGTH_SHORT).show();
                    App.getAppPreference().SaveString(ConstantData.USERID, loginRegisterModelClass.getDetails().getId());
                    App.getSingleton().setPhone(loginRegisterModelClass.getDetails().getPhone());
                    startActivity(new Intent(BuyerRegisterActivity.this, BuyerOtpVerification.class));
                    finishAffinity();
                } else {
                    CommonUtils.showSnackbarAlert(sign_up_button_signup, loginRegisterModelClass.getMessage());
                }
            }
        });
    }

    private void Register() {
        name = nameEditText.getText().toString();
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        number = numberEditText.getText().toString();
        confirmPass = confirmPasswordEditText.getText().toString();
        if (name.isEmpty()) {
            CommonUtils.showSnackbarAlert(nameEditText, "enter name");
        } else if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            CommonUtils.showSnackbarAlert(nameEditText, "enter email");
        } else if (number.isEmpty()) {
            CommonUtils.showSnackbarAlert(nameEditText, "enter number");
        } else if (password.isEmpty() || password.length() < 5) {
            CommonUtils.showSnackbarAlert(nameEditText, "enter minimum 6 character password");
        } else if (confirmPass.isEmpty() || !password.equals(confirmPass)) {
            CommonUtils.showSnackbarAlert(nameEditText, "password mismatch");
        } else {
            viewModel.Register(BuyerRegisterActivity.this, name, email, countryCode + number, password, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "android", FirebaseInstanceId.getInstance().getToken(), "2","").observe(BuyerRegisterActivity.this, new Observer<LoginRegisterModelClass>() {
                @Override
                public void onChanged(LoginRegisterModelClass loginRegisterModelClass) {
                    if (loginRegisterModelClass.getSuccess().equalsIgnoreCase("1")) {
                        Toast.makeText(BuyerRegisterActivity.this, loginRegisterModelClass.getDetails().getOtp(), Toast.LENGTH_SHORT).show();
                        App.getAppPreference().SaveString(ConstantData.USERID, loginRegisterModelClass.getDetails().getId());
                        App.getSingleton().setPhone(loginRegisterModelClass.getDetails().getPhone());
                        startActivity(new Intent(BuyerRegisterActivity.this, BuyerOtpVerification.class));
                        finishAffinity();
                    } else {
                        CommonUtils.showSnackbarAlert(sign_up_button_signup, loginRegisterModelClass.getMessage());
                    }
                }
            });
        }
    }
}
