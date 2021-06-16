package com.omninos.nani.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.omninos.nani.R;
import com.omninos.nani.myViewModel.PasswordViewModel;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;

import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView appbar_transparent_image1;
    private EditText OldPassword, NewPassword, ConfirmPassword;
    private String OldPass, newPass, ConfPass;
    private Button done;
    private PasswordViewModel viewModel;
    private RelativeLayout app_bar_edit_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getStringExtra("Type").equalsIgnoreCase("1")) {
            setTheme(R.style.BlueTheme);
        }
        setContentView(R.layout.activity_change_password);

        CommonUtils.CheckService(ChangePasswordActivity.this);

        viewModel = ViewModelProviders.of(this).get(PasswordViewModel.class);
        initView();
        SetUp();

    }

    private void initView() {
        appbar_transparent_image1 = findViewById(R.id.appbar_transparent_image1);
        OldPassword = findViewById(R.id.OldPassword);
        NewPassword = findViewById(R.id.NewPassword);
        ConfirmPassword = findViewById(R.id.ConfirmPassword);
        done = findViewById(R.id.done);

        app_bar_edit_profile = findViewById(R.id.app_bar_edit_profile);
    }

    private void SetUp() {
        appbar_transparent_image1.setOnClickListener(this);
        done.setOnClickListener(this);

        if (getIntent().getStringExtra("Type").equalsIgnoreCase("1")) {
            app_bar_edit_profile.setBackgroundColor(getResources().getColor(R.color.blue));
            done.setBackgroundColor(getResources().getColor(R.color.blue));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appbar_transparent_image1:
                onBackPressed();
                break;

            case R.id.done:
                UpdatePassword();
                break;
        }
    }

    private void UpdatePassword() {
        OldPass = OldPassword.getText().toString();
        newPass = NewPassword.getText().toString();
        ConfPass = ConfirmPassword.getText().toString();
        if (OldPass.isEmpty()) {
            CommonUtils.showSnackbarAlert(OldPassword, "enter old password");
        } else if (newPass.isEmpty()) {
            CommonUtils.showSnackbarAlert(NewPassword, "enter new password");
        } else if (ConfPass.isEmpty() || !ConfPass.equals(newPass)) {
            CommonUtils.showSnackbarAlert(ConfirmPassword, "password can't match with new password");
        } else {
            Submit();
        }
    }

    private void Submit() {
        viewModel.PasswordChange(ChangePasswordActivity.this, OldPass, newPass, App.getAppPreference().getUserDetails().getDetails().getId()).observe(ChangePasswordActivity.this, new Observer<Map>() {
            @Override
            public void onChanged(Map map) {
                if (map.get("success").toString().equalsIgnoreCase("1")) {
                    Toast.makeText(ChangePasswordActivity.this, map.get("message").toString(), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {
                    Toast.makeText(ChangePasswordActivity.this, map.get("message").toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
