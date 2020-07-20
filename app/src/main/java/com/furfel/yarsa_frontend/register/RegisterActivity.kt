package com.furfel.yarsa_frontend.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.furfel.yarsa_frontend.R
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.util.concurrent.Executor

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    private fun registerUser() {

    }

    private fun captchaVerify() {
            SafetyNet.getClient(this).verifyWithRecaptcha(resources.getString(R.string.api_site_key))
                .addOnSuccessListener(this as Executor, OnSuccessListener { response ->
                    // Indicates communication with reCAPTCHA service was
                    // successful.
                    val userResponseToken = response.tokenResult
                    if (response.tokenResult?.isNotEmpty() == true) {
                        // Validate the user response token using the
                        // reCAPTCHA siteverify API.
                    }
                })
                .addOnFailureListener(this as Executor, OnFailureListener { e ->
                    if (e is ApiException) {
                        val error = CommonStatusCodes.getStatusCodeString(e.statusCode)
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                        Log.d("Captcha-Verify", "Error: $error")
                    } else {
                        val error = e.message
                        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                        Log.d("Captcha-Verify", "Error: $error")
                    }
                })

    }
}