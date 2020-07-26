package com.furfel.yarsa_frontend

import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.furfel.yarsa_frontend.register.RegisterActivity
import kotlinx.android.synthetic.main.welcome_layout.*

class YARSAFrontend : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.welcome_layout)

        signUp.setOnClickListener {
            if(it.isEnabled) {
                val intent = Intent(this, RegisterActivity.javaClass)
                startActivity(intent)
            }
        }

        signIn.setOnClickListener {
            if(it.isEnabled) {

            }
        }
    }
}