package com.omninos.nani.activity.userActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.omninos.nani.R
import com.omninos.nani.myViewModel.ForgotPasswordViewModel

class BuyerForgotActivity : AppCompatActivity() {

    lateinit var back: ImageView
    lateinit var emailEditText: EditText
    lateinit var sign_in_button_login: Button
    lateinit var forgotPasswordViewModel: ForgotPasswordViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer_forgot)

        forgotPasswordViewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel::class.java)

        initView()
        SetUp()
    }

    private fun initView() {
        back = findViewById(R.id.back)
        emailEditText = findViewById(R.id.emailEditText)
        sign_in_button_login = findViewById(R.id.sign_in_button_login)

    }

    private fun SetUp() {
        sign_in_button_login.setOnClickListener {
            if (emailEditText.text.toString().isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailEditText.text.toString()).matches()) {
                Toast.makeText(this, "Enter valid email", Toast.LENGTH_LONG).show()
            } else {
                forgotPasswordViewModel.forgotData(this@BuyerForgotActivity, emailEditText.text.toString()).observe(this@BuyerForgotActivity, Observer {
                    if (it.get("success").toString().equals("1")) {
                        Toast.makeText(this@BuyerForgotActivity, "Password send to your Email", Toast.LENGTH_LONG).show()
                        onBackPressed()
                    } else {
                        Toast.makeText(this@BuyerForgotActivity, it.get("message").toString(), Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}
