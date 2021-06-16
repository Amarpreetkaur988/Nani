package com.omninos.nani.activity.naniActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.omninos.nani.R
import com.omninos.nani.activity.SearchPlaceActivity
import com.omninos.nani.utils.App
import com.omninos.nani.utils.CommonUtils

class UpdateBankDetailActivity : AppCompatActivity(), View.OnClickListener {

    private var next: Button? = null
    private var bankNameEditText: EditText? = null
    private var accountNumberEditText: EditText? = null
    private var accountHolderEditText: EditText? = null
    private var branchNameEditText: EditText? = null
    private var branchCodeEditText: EditText? = null
    private var phoneNumberEditText: EditText? = null
    private var bankName: String? = null
    private var accountNumber: String? = null
    private var accountHolder: String? = null
    private var branchName: String? = null
    private var branchCode: String? = null
    private var phoneNumber = ""
    private var addressEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.BlueTheme)
        setContentView(R.layout.activity_update_bank_detail)

        CommonUtils.CheckService(this)

        initIds()
        performActions()

    }

    private fun initIds() {
        next = findViewById(R.id.next_button_bank_details)
        bankNameEditText = findViewById(R.id.bankNameEditText)
        accountNumberEditText = findViewById(R.id.accountNumberEditText)
        accountHolderEditText = findViewById(R.id.accountHolderEditText)
        branchNameEditText = findViewById(R.id.branchNameEditText)
        branchCodeEditText = findViewById(R.id.branchCodeEditText)
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText)
        addressEditText = findViewById(R.id.addressEditText)
    }

    private fun performActions() {
        next?.setOnClickListener(this)
        addressEditText?.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.getId()) {
            R.id.next_button_bank_details -> validate()

            R.id.addressEditText-> GetAddress();
        }
    }

    private fun GetAddress() {
        startActivity(Intent(this@UpdateBankDetailActivity, SearchPlaceActivity::class.java))
    }

    private fun validate() {
        bankName = bankNameEditText?.getText().toString().trim { it <= ' ' }
        accountNumber = accountNumberEditText?.getText().toString().trim { it <= ' ' }
        accountHolder = accountHolderEditText?.getText().toString().trim { it <= ' ' }
        branchName = branchNameEditText?.getText().toString().trim { it <= ' ' }
        branchCode = branchCodeEditText?.getText().toString().trim { it <= ' ' }
        phoneNumber = phoneNumberEditText?.getText().toString().trim { it <= ' ' }

        if (addressEditText?.text.isNullOrEmpty()) {
            CommonUtils.showSnackbarAlert(bankNameEditText, "enter address")
        } else if (bankName.isNullOrEmpty()) {
            CommonUtils.showSnackbarAlert(bankNameEditText, "enter bank name")
        } else if (accountNumber.isNullOrEmpty()) {
            CommonUtils.showSnackbarAlert(bankNameEditText, "Enter Your Account Number")
        } else if (accountHolder.isNullOrEmpty()) {
            CommonUtils.showSnackbarAlert(bankNameEditText, "Enter Your Account Holder Name")
        } else if (branchName.isNullOrEmpty()) {
            CommonUtils.showSnackbarAlert(bankNameEditText, "enter branch name")
        } else if (branchCode.isNullOrEmpty()) {
            CommonUtils.showSnackbarAlert(bankNameEditText, "enter branch code")
        } else {
            App.getSingleton().bankName = bankName
            App.getSingleton().accountNumber = accountNumber
            App.getSingleton().accountHolderName = accountHolder
            App.getSingleton().branchName = branchName
            App.getSingleton().branchCode = branchCode
            App.getSingleton().optionalPhone = phoneNumber
            startActivity(Intent(this@UpdateBankDetailActivity, SpecialitiesActivity::class.java).putExtra("Type","1"))
        }
    }


    override fun onResume() {
        super.onResume()
        if (App.getSingleton().naniAddress != null) {
            addressEditText?.setText(App.getSingleton().naniAddress)
        }
    }
}
