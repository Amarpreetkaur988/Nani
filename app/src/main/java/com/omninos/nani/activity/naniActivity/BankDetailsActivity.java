package com.omninos.nani.activity.naniActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.omninos.nani.R;
import com.omninos.nani.utils.App;
import com.omninos.nani.utils.CommonUtils;

public class BankDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button next;
    private EditText bankNameEditText, accountNumberEditText, accountHolderEditText, branchNameEditText, branchCodeEditText, phoneNumberEditText;
    private String bankName, accountNumber, accountHolder, branchName, branchCode, phoneNumber="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.BlueTheme);
        setContentView(R.layout.activity_bank_details);
        CommonUtils.CheckService(BankDetailsActivity.this);
        initIds();
        performActions();

    }

    private void initIds() {
        next = findViewById(R.id.next_button_bank_details);
        bankNameEditText = findViewById(R.id.bankNameEditText);
        accountNumberEditText = findViewById(R.id.accountNumberEditText);
        accountHolderEditText = findViewById(R.id.accountHolderEditText);
        branchNameEditText = findViewById(R.id.branchNameEditText);
        branchCodeEditText = findViewById(R.id.branchCodeEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
    }

    private void performActions() {
        next.setOnClickListener(this);
    }

    private void validate() {
        bankName = bankNameEditText.getText().toString().trim();
        accountNumber = accountNumberEditText.getText().toString().trim();
        accountHolder = accountHolderEditText.getText().toString().trim();
        branchName = branchNameEditText.getText().toString().trim();
        branchCode = branchCodeEditText.getText().toString().trim();
        phoneNumber = phoneNumberEditText.getText().toString().trim();

        if (bankName.isEmpty()) {
            CommonUtils.showSnackbarAlert(bankNameEditText, "Enter Your Bank Name");
        } else if (accountNumber.isEmpty()) {
            CommonUtils.showSnackbarAlert(bankNameEditText, "Enter Your Account Number");
        } else if (accountHolder.isEmpty()) {
            CommonUtils.showSnackbarAlert(bankNameEditText, "Enter Your Account Holder Name");
        } else if (branchName.isEmpty()) {
            CommonUtils.showSnackbarAlert(bankNameEditText, "Enter Your Branch Name");
        } else if (branchCode.isEmpty()) {
            CommonUtils.showSnackbarAlert(bankNameEditText, "Enter Your Branch Code");
//        }else if (phoneNumber.length() < 9) {
//            phoneNumberEditText.setError("enter phone number");
        } else {
            App.getSingleton().setBankName(bankName);
            App.getSingleton().setAccountNumber(accountNumber);
            App.getSingleton().setAccountHolderName(accountHolder);
            App.getSingleton().setBranchName(branchName);
            App.getSingleton().setBranchCode(branchCode);
            App.getSingleton().setOptionalPhone(phoneNumber);

            startActivity(new Intent(BankDetailsActivity.this, SpecialitiesActivity.class).putExtra("Type","2"));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_button_bank_details:
                validate();
                break;
        }
    }
}
