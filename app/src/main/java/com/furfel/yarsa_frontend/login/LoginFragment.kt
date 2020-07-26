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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class LoginFragment : Fragment() {

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
                }
            }
        }
    }

    private suspend fun tryLogin() {

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