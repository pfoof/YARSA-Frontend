package com.furfel.yarsa_frontend.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.airbnb.paris.extensions.style
import com.furfel.yarsa_frontend.R
import com.furfel.yarsa_frontend.responses.SpringError
import com.furfel.yarsa_frontend.interfaces.UserInterface
import com.furfel.yarsa_frontend.responses.Success
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {

    private val scope = MainScope()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://pabis.eu/").build()
    private val userInterface = retrofit.create(UserInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        signUpButton.setOnClickListener {
            if(it.isEnabled) {
                if(validateForm()) {
                    lockForm(true)
                    scope.launch {
                        registerUser()
                    }
                }
            }
        }
    }

    private fun validEmail(): Boolean = email.text.matches( Regex(EMAIL_PATTERN) )
    private fun hasEmail(): Boolean = email.text.isNotBlank()
    private fun validPassword(): Boolean = password.text.length >= 6
    private fun hasPassword(): Boolean = password.text.isNotBlank()
    private fun validRePassword(): Boolean = repassword.text.toString() == password.text.toString()
    private fun hasRePassword(): Boolean = repassword.text.isNotBlank()
    private fun validUsername(): Boolean = username.text.length < 30 && username.text.matches(Regex(USERNAME_PATTERN))
    private fun hasUsername(): Boolean = username.text.isNotBlank()

    private var hasError: Boolean = true

    private fun lockForm(lock: Boolean) {
        listOf(
            signUpButton, password, username,
            email, repassword
        ).forEach {
            it.isEnabled = lock
        }

    }

    private fun resetForm() {
        listOf(emailError, passwordError, repasswordError, usernameError, generalError).forEach {
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

    private fun setGeneralError(message: String) {
        generalError.visibility = View.VISIBLE
        generalError.text = message
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

        return !hasError
    }

    private suspend fun registerUser() {
        withContext(Dispatchers.IO) {
            val data = RegisterData(email.text.toString(), username.text.toString(), password.text.toString())
            val call = userInterface.registerUser(data.username, data.email, data.password)
            val ex = call.execute()
            withContext(Dispatchers.Main) {
                lockForm(false)
            }
            try {
                val springException = SpringError.fromResponse(ex)
                if(springException == null) {
                    val success = Success.fromResponse(ex)
                    withContext(Dispatchers.Main) {
                        if (success == null)
                            setGeneralError("Server error: no response!")
                        else {
                            Toast.makeText(
                                this@RegisterActivity,
                                "User registered! Please log in!",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }
                } else
                    withContext(Dispatchers.Main) { setGeneralError(springException.message) }
            } catch (e: JsonIOException) {
                withContext(Dispatchers.Main) { setGeneralError("Server error: I/O") }
            } catch (e: JsonSyntaxException) {
                withContext(Dispatchers.Main) { setGeneralError("Server error: not a JSON format") }
            }
        }
    }

    companion object {
        const val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        const val USERNAME_PATTERN = "[a-zA-Z0-9_-]+"
    }
}