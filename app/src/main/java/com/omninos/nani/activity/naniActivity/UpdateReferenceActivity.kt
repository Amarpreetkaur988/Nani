package com.omninos.nani.activity.naniActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.omninos.nani.R
import com.omninos.nani.myViewModel.UpdateNaniStatusViewModel
import com.omninos.nani.utils.App
import com.omninos.nani.utils.CommonUtils
import com.omninos.nani.utils.ConstantData

class UpdateReferenceActivity : AppCompatActivity(), View.OnClickListener {

    private var referenceOneName: EditText? = null
    private var referenceOneNumber: EditText? = null
    private var referenceOneTest: EditText? = null
    private var referenceTwoName: EditText? = null
    private var referenceTwoNumber: EditText? = null
    private var referenceTwoTest: EditText? = null
    private var OneName = ""
    private var OneNumber = ""
    private var OneItem = ""
    private var TwoName = ""
    private var TwoNumber = ""
    private var TwoItem = ""
    private var submit: Button? = null
    private lateinit var viewModel: UpdateNaniStatusViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.BlueTheme)
        setContentView(R.layout.activity_update_reference)

        CommonUtils.CheckService(this@UpdateReferenceActivity)

        viewModel = ViewModelProviders.of(this).get(UpdateNaniStatusViewModel::class.java)

        initView()
        SetUp()

    }

    private fun initView() {
        referenceOneName = findViewById(R.id.referenceOneName)
        referenceOneNumber = findViewById(R.id.referenceOneNumber)
        referenceOneTest = findViewById(R.id.referenceOneTest)
        referenceTwoName = findViewById(R.id.referenceTwoName)
        referenceTwoNumber = findViewById(R.id.referenceTwoNumber)
        referenceTwoTest = findViewById(R.id.referenceTwoTest)
        submit = findViewById(R.id.submit)
    }

    private fun SetUp() {
        submit?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.getId()) {
            R.id.submit -> UpdateInfo()
        }
    }

    private fun UpdateInfo() {
        OneName = referenceOneName?.getText().toString()
        OneNumber = referenceOneNumber?.getText().toString()
        OneItem = referenceOneTest?.getText().toString()

        TwoName = referenceTwoName?.getText().toString()
        TwoNumber = referenceTwoNumber?.getText().toString()
        TwoItem = referenceTwoTest?.getText().toString()

        if (OneName.isNullOrEmpty() || OneNumber.isNullOrEmpty() || OneItem.isNullOrEmpty()) {
            CommonUtils.showSnackbarAlert(referenceOneName, "fill atleast one")
        } else {
            Update()
        }
    }

    private fun Update() {
        viewModel.UpdateBankDetail(this@UpdateReferenceActivity, App.getSingleton().naniAddress, App.getSingleton().bankName, App.getSingleton().idNumber, App.getSingleton().accountNumber, App.getSingleton().accountHolderName, App.getSingleton().branchName, App.getSingleton().branchCode, App.getSingleton().optionalPhone, App.getSingleton().specialities, OneName, OneNumber, OneItem, TwoName, TwoNumber, TwoItem, App.getSingleton().naniLat, App.getSingleton().naniLng, App.getAppPreference().userDetails.details.id).observe(this@UpdateReferenceActivity, Observer {
            if (it.success.equals("1")) {
                App.getAppPreference().saveUserDetails(it)
                App.getAppPreference().SaveString(ConstantData.USER_TYPE, "1")
                startActivity(Intent(this@UpdateReferenceActivity, NaniHomeActivity::class.java))
            } else {
                CommonUtils.showSnackbarAlert(submit, it.message)
            }
        })
    }


}
