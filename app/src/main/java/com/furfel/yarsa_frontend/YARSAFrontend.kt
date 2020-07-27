package com.furfel.yarsa_frontend

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.furfel.yarsa_frontend.login.LoginFragment
import com.furfel.yarsa_frontend.register.RegisterActivity
import kotlinx.android.synthetic.main.welcome_layout.*

class YARSAFrontend : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_layout)

        signUp.setOnClickListener {
            if(it.isEnabled) {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }

        signIn.setOnClickListener {
            if(it.isEnabled) {
                loginFragment = LoginFragment().also {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.mainLayout, it)
                        .commit()
                }

            }
        }
    }

    var loginFragment: LoginFragment? = null
    override fun onBackPressed() {
        loginFragment?.let {
            if(supportFragmentManager.fragments.contains(it)) {
                supportFragmentManager.beginTransaction()
                    .remove(it)
                    .commit()
            }
            return
        }
        super.onBackPressed()

    }
}