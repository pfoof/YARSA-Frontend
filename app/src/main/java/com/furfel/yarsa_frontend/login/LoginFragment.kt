package com.furfel.yarsa_frontend.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.furfel.yarsa_frontend.R
import com.furfel.yarsa_frontend.responses.SpringError
import com.furfel.yarsa_frontend.interfaces.UserInterface
import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class LoginFragment : Fragment() {

    val scope = MainScope()
    val userInterface = Retrofit.Builder()
        .baseUrl("https://pabis.eu")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(UserInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private var username: EditText? = null
    private var password: EditText? = null
    private var signInButton: Button? = null
    private var loginError: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_login, container, false)

        layout.setOnClickListener {  }

        username = layout.findViewById(R.id.loginUsername)
        password = layout.findViewById(R.id.loginPassword)
        signInButton = layout.findViewById(R.id.signInButton)
        loginError = layout.findViewById(R.id.loginError)
        signInButton?.setOnClickListener {
            signIn()
        }

        return layout
    }

    private fun signIn() {
        loginError?.let { it.visibility = View.GONE }

        if(username != null && password != null) {

            when {
                username!!.text.isBlank() -> loginError?.let {
                    it.visibility = View.VISIBLE
                    it.text = "Username must not be blank!"
                }
                password!!.text.isEmpty() -> loginError?.let {
                    it.visibility = View.VISIBLE
                    it.text = "Password must not be blank!"
                }
                else -> {
                    username?.isEnabled = false
                    password?.isEnabled = false
                    signInButton?.isEnabled = false
                    scope.launch {
                        tryLogin(username!!.text.toString(), password!!.text.toString())
                    }
                }
            }
        }
    }

    private fun enableViews() {
        username?.isEnabled = true
        password?.isEnabled = true
        signInButton?.isEnabled = true
    }

    private fun <T>extractSessionKey(ex: Response<T>): String {
        return UUID.randomUUID().toString()
    }

    private suspend fun tryLogin(username: String, password: String) {
        withContext(Dispatchers.IO) {
            val call = userInterface.loginUser(username, password)
            val ex = call.execute()
            try {
                val springException = SpringError.fromResponse(ex)
                if(springException == null) {
                    extractSessionKey(ex)
                } else
                    withContext(Dispatchers.Main) { loginError(springException.message) }
            } catch (e: JsonIOException) {
                withContext(Dispatchers.Main) { loginError("Server error: I/O") }
            } catch (e: JsonSyntaxException) {
                withContext(Dispatchers.Main) { loginError("Server error: not a JSON format") }
            }

        }
    }

    private fun loginError(err: String) {
        username?.isEnabled = true
        password?.isEnabled = true
        signInButton?.isEnabled = true
        loginError?.let {
            it.visibility = View.VISIBLE
            it.text = err
        }
    }

}