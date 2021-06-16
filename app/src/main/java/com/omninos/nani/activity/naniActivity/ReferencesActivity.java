package com.omninos.nani.activity.naniActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.iid.FirebaseInstanceId;
import com.omninos.nani.R;
import com.omninos.nani.modelClass.LoginRegisterModelClass;
import com.omninos.nani.myViewModel.LoginRegisterViewModel;
import com.omninos.nani.myViewModel.SocialLoginViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;
import com.omninos.nani.utils.ConstantData;

public class ReferencesActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText referenceOneName, referenceOneNumber, referenceOneTest, referenceTwoName, referenceTwoNumber, referenceTwoTest;
    private String OneName = "", OneNumber = "", OneItem = "", TwoName = "", TwoNumber = "", TwoItem = "";
    private Button submit;

    private LoginRegisterViewModel viewModel;
    private SocialLoginViewModel socialLoginViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.BlueTheme);
        setContentView(R.layout.activity_references);

        CommonUtils.CheckService(ReferencesActivity.this);

        viewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel.class);
        socialLoginViewModel = ViewModelProviders.of(this).get(SocialLoginViewModel.class);

        initView();
        SetUp();
    }

    private void initView() {
        referenceOneName = findViewById(R.id.referenceOneName);
        referenceOneNumber = findViewById(R.id.referenceOneNumber);
        referenceOneTest = findViewById(R.id.referenceOneTest);
        referenceTwoName = findViewById(R.id.referenceTwoName);
        referenceTwoNumber = findViewById(R.id.referenceTwoNumber);
        referenceTwoTest = findViewById(R.id.referenceTwoTest);
        submit = findViewById(R.id.submit);
    }

    private void SetUp() {
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                if (App.getSingleton().getSocialId() != null) {
                    SubmitSocial();
                } else {
                    SubmitData();
                }
                break;
        }
    }

    private void SubmitSocial() {
        OneName = referenceOneName.getText().toString();
        OneNumber = referenceOneNumber.getText().toString();
        OneItem = referenceOneTest.getText().toString();

        TwoName = referenceTwoName.getText().toString();
        TwoNumber = referenceTwoNumber.getText().toString();
        TwoItem = referenceTwoTest.getText().toString();

        if (OneName.isEmpty() || OneNumber.isEmpty() || OneItem.isEmpty()) {
            CommonUtils.showSnackbarAlert(referenceOneName, "fill atleast one");
        } else {
            socialLoginViewModel.RegisterSocial(ReferencesActivity.this, App.getSingleton().getName(), App.getSingleton().getEmail(), App.getSingleton().getPhone(), App.getSingleton().getSocialId(), App.getSingleton().getNaniAddress(),
                    App.getSingleton().getBankName(), App.getSingleton().getAccountNumber(), App.getSingleton().getAccountHolderName(), App.getSingleton().getBranchName(), App.getSingleton().getBranchCode(), App.getSingleton().getOptionalPhone(),
                    App.getSingleton().getSpecialities(), OneName, OneNumber, OneItem, TwoName, TwoNumber, TwoItem, "android", FirebaseInstanceId.getInstance().getToken(), App.getSingleton().getLogintype(),App.getSingleton().getIdNumber()).observe(ReferencesActivity.this, new Observer<LoginRegisterModelClass>() {
                @Override
                public void onChanged(LoginRegisterModelClass loginRegisterModelClass) {
                    if (loginRegisterModelClass.getSuccess().equalsIgnoreCase("1")) {
                        Toast.makeText(ReferencesActivity.this, loginRegisterModelClass.getDetails().getOtp(), Toast.LENGTH_SHORT).show();
                        App.getAppPreference().SaveString(ConstantData.USERID, loginRegisterModelClass.getDetails().getId());
                        App.getSingleton().setPhone(loginRegisterModelClass.getDetails().getPhone());
                        startActivity(new Intent(ReferencesActivity.this, NotifiedActivity.class));
                        finishAffinity();
                    } else {
                        CommonUtils.showSnackbarAlert(referenceTwoName, loginRegisterModelClass.getMessage());

                    }

                }
            });
        }
    }

    private void SubmitData() {
        OneName = referenceOneName.getText().toString();
        OneNumber = referenceOneNumber.getText().toString();
        OneItem = referenceOneTest.getText().toString();

        TwoName = referenceTwoName.getText().toString();
        TwoNumber = referenceTwoNumber.getText().toString();
        TwoItem = referenceTwoTest.getText().toString();

        if (OneName.isEmpty() || OneNumber.isEmpty() || OneItem.isEmpty()) {
            CommonUtils.showSnackbarAlert(referenceOneName, "fill atleast one");
        } else {
            viewModel.Register(ReferencesActivity.this, App.getSingleton().getName(), App.getSingleton().getEmail(), App.getSingleton().getPhone(), App.getSingleton().getPassword(),
                    App.getSingleton().getBankName(), App.getSingleton().getAccountNumber(), App.getSingleton().getAccountHolderName(), App.getSingleton().getBranchName(), App.getSingleton().getBranchCode(), App.getSingleton().getOptionalPhone(),
                    App.getSingleton().getSpecialities(), OneName, OneNumber, OneItem, TwoName, TwoNumber, TwoItem, App.getSingleton().getNaniLng(), App.getSingleton().getNaniLat(), App.getSingleton().getNaniAddress(), "android", FirebaseInstanceId.getInstance().getToken(), "1",App.getSingleton().getIdNumber()).observe(ReferencesActivity.this, new Observer<LoginRegisterModelClass>() {
                @Override
                public void onChanged(LoginRegisterModelClass loginRegisterModelClass) {
                    if (loginRegisterModelClass.getSuccess().equalsIgnoreCase("1")) {
                        Toast.makeText(ReferencesActivity.this, loginRegisterModelClass.getDetails().getOtp(), Toast.LENGTH_SHORT).show();
                        App.getAppPreference().SaveString(ConstantData.USERID, loginRegisterModelClass.getDetails().getId());
                        App.getSingleton().setPhone(loginRegisterModelClass.getDetails().getPhone());
                        startActivity(new Intent(ReferencesActivity.this, NotifiedActivity.class));
                        finishAffinity();
                    } else {
                        CommonUtils.showSnackbarAlert(referenceTwoName, loginRegisterModelClass.getMessage());
                    }
                }
            });
        }
    }
}
