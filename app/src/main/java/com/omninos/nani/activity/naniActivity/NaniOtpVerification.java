package com.omninos.nani.activity.naniActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.omninos.nani.R;
import com.omninos.nani.modelClass.LoginRegisterModelClass;
import com.omninos.nani.myViewModel.LoginRegisterViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;
import com.omninos.nani.utils.ConstantData;

public class NaniOtpVerification extends AppCompatActivity implements View.OnClickListener {

    private EditText otp1, otp2, otp3, otp4;
    private Button verify_otp, verify_otp_popup;
    private String otp = "";
    private RelativeLayout layout;
    private Activity activity;
    private String userType = "", id, token;
    private LoginRegisterViewModel viewModel;
    private TextView numberTextView, resendOtp;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.BlueTheme);
        setContentView(R.layout.activity_nani_otp_verification);
        CommonUtils.CheckService(NaniOtpVerification.this);
        activity = NaniOtpVerification.this;
        viewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel.class);


        id = App.getAppPreference().GetString(ConstantData.USERID);
        initIds();
        setClicks();
        setTextWatcher();
    }

    private void initIds() {
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        verify_otp = findViewById(R.id.verify_button_otp);
        layout = findViewById(R.id.otp_layout);
        numberTextView = findViewById(R.id.numberTextView);
        resendOtp = findViewById(R.id.resendOtp);
        verify_otp_popup = findViewById(R.id.verify_otp_popup);
    }

    private void setClicks() {
        verify_otp.setOnClickListener(this);
        resendOtp.setOnClickListener(this);
        number = App.getSingleton().getPhone();
        numberTextView.setText(number);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.verify_button_otp:
                matchVerificationToken();
                break;

            case R.id.resendOtp:
                resendVerificationToken();
                break;
        }
    }

    private void resendVerificationToken() {
        viewModel.ResendVerificationToken(activity, id).observe(NaniOtpVerification.this, new Observer<LoginRegisterModelClass>() {
            @Override
            public void onChanged(@Nullable LoginRegisterModelClass registerModelClass) {
                if (registerModelClass.getSuccess().equalsIgnoreCase("1")) {
                    Toast.makeText(activity, registerModelClass.getDetails().getOtp(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, registerModelClass.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void matchVerificationToken() {
        otp = otp1.getText().toString() + otp2.getText().toString() + otp3.getText().toString() + otp4.getText().toString();

        if (otp.length() == 4) {
            viewModel.MatchVerification(activity, id, otp).observe(NaniOtpVerification.this, new Observer<LoginRegisterModelClass>() {
                @Override
                public void onChanged(@Nullable LoginRegisterModelClass registerModelClass) {
                    if (registerModelClass.getSuccess().equalsIgnoreCase("1")) {
                        App.getAppPreference().saveUserDetails(registerModelClass);
                        App.getAppPreference().SaveString(ConstantData.TOKEN, "1");
                        App.getAppPreference().SaveString(ConstantData.USER_TYPE,"1");
                        verify1();
                    } else {
                        CommonUtils.showSnackbarAlert(verify_otp, registerModelClass.getMessage());
                    }
                }
            });
        } else {
            Toast.makeText(this, "Enter Otp", Toast.LENGTH_SHORT).show();
        }
    }


    private void verify1() {
        final Dialog dialog = new Dialog(NaniOtpVerification.this);
        dialog.setContentView(R.layout.otp_verification_popup);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button button = dialog.findViewById(R.id.verify_otp_popup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NaniOtpVerification.this, NaniHomeActivity.class));
                finishAffinity();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void setTextWatcher() {
        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    otp2.requestFocus();
                } else if (s.length() == 0) {

                }
            }
        });

        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    otp3.requestFocus();
                } else if (s.length() == 0) {
                    otp1.requestFocus();
                }
            }
        });

        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    otp4.requestFocus();
                } else if (s.length() == 0) {
                    otp2.requestFocus();
                }
            }
        });

        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                } else if (s.length() == 0) {
                    otp3.requestFocus();
                }
            }
        });
    }
}
