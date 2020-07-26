package com.furfel.yarsa_frontend.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.airbnb.paris.extensions.style
import com.furfel.yarsa_frontend.R
import com.furfel.yarsa_frontend.interfaces.UserInterface
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.invoke
import retrofit2.Retrofit
import java.util.concurrent.Executor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class RegisterActivity : AppCompatActivity() {

    private val retrofit = Retrofit.Builder().baseUrl("https://pabis.eu/").build()
    private val userInterface = retrofit.create(UserInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        signUpButton.setOnClickListener {
            if(it.isEnabled) {
                if(validateForm()) {
                    Toast.makeText(this, "Form is ok!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validEmail(): Boolean = email.text.matches( Regex(EMAIL_PATTERN) )
    private fun hasEmail(): Boolean = email.text.isNotBlank()
    private fun validPassword(): Boolean = password.text.length >= 6
    private fun hasPassword(): Boolean = password.text.isNotBlank()
    private fun validRePassword(): Boolean = repassword.text == password.text
    private fun hasRePassword(): Boolean = repassword.text.isNotBlank()
    private fun validUsername(): Boolean = username.text.length < 30 && username.text.matches(Regex(USERNAME_PATTERN))
    private fun hasUsername(): Boolean = username.text.isNotBlank()

    private var hasError: Boolean = true

    private fun resetForm() {
        listOf(emailError, passwordError, repasswordError, usernameError).forEach {
            it.visibility = View.GONE
        }

        listOf(password, email, repassword, username).forEach {
            it.style(R.style.registerEditText)
        }

        signUpButton.isEnabled = true

        hasError = false

    }

    private fun setError(source: EditText, errView: TextView, message: String) {
        source.style(R.style.registerEditText_Error)
        errView.visibility = View.VISIBLE
        errView.text = message
        hasError = true
    }

    private fun validateForm(): Boolean {

        resetForm()

        if(!hasUsername()) setError(username, usernameError, "Username must not be blank!")
        else if(!validUsername()) setError(username, usernameError, "Username is not valid!")

        if(!hasEmail()) setError(email, emailError, "E-mail must not be blank!")
        else if(!validEmail()) setError(email, emailError, "E-mail is not valid!")

        if(!hasPassword()) setError(password, passwordError, "Password must not be blank!")
        else if(!validPassword()) setError(password, passwordError, "Password is too short!")

        if(!hasRePassword()) setError(repassword, repasswordError, "Please, retype the password")
        else if(!validRePassword()) setError(repassword, repasswordError, "Passwords do not match!")

        return hasError
    }

    private suspend fun registerUser() {
        signUpButton.isEnabled = false
        val data = RegisterData(email.text.toString(), username.text.toString(), password.text.toString())
        val call = userInterface.registerUser(data.username, data.email, data.password)
    }

    companion object {
        const val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        const val USERNAME_PATTERN = "[a-zA-Z0-9_-]"
    }
}