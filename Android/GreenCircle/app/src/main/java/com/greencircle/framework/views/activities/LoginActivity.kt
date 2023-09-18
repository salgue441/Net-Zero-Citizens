package com.greencircle.framework.views.activities

import ViewModelFactory
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.greencircle.R
import com.greencircle.databinding.ActivityLoginBinding
import com.greencircle.framework.viewmodel.LoginViewModel
import com.greencircle.framework.views.MainActivity
import com.greencircle.utils.AuthUtils

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val authUtils = AuthUtils()
    private val viewModel: LoginViewModel by viewModels {
        ViewModelFactory(applicationContext)
    }

    private val registerCompanyActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Handle the result as needed, e.g., update UI or perform actions
            } else if (result.resultCode == Activity.RESULT_CANCELED) {
                // Handle the case where the user canceled the registration
            }
        }
    private val registerUserActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Handle the result as needed, e.g., update UI or perform actions
            } else if (result.resultCode == Activity.RESULT_CANCELED) {
                // Handle the case where the user canceled the registration
            }
        }
    private val googleSignInActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                Log.d("GoogleSignIn", "data: $data")
                if (data != null && result.resultCode == Activity.RESULT_OK) {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    try {
                        val account = task.getResult(ApiException::class.java)
                        Log.d("Test", "${account.idToken}")
                        viewModel.googleLogin(account.idToken!!)
                        navigateToHome()
                    } catch (e: ApiException) {
                        Toast.makeText(
                            applicationContext, "Something went wrong", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else if (result.resultCode == Activity.RESULT_CANCELED) {
                // Handle the case where the user canceled the operation
            }
        }
    // obtener los argumentos del account y mandarlos a un nuevo fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Listener Methods
        registerCompanyOnClickListener()
        registerUserOnClickListener()

        // Google Login
        authUtils.googleLoginListener(binding, this, googleSignInActivityResult)
    }

    // On Click Listener Method
    private fun registerCompanyOnClickListener() {
        val registerCompanyButton = binding.root.findViewById<View>(R.id.login_register_company)
        registerCompanyButton.setOnClickListener {
            navigateToRegisterCompany()
        }
    }

    private fun registerUserOnClickListener() {
        val registerUserButton = binding.root.findViewById<View>(R.id.login_register_user)
        registerUserButton.setOnClickListener {
            navigateToRegisterUser()
        }
    }

    // Navigation Methods
    private fun navigateToHome() {
        var intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToRegisterCompany() {
        var intent: Intent = Intent(this, RegisterCompanyActivity::class.java)
        registerCompanyActivityResult.launch(intent)
    }

    private fun navigateToRegisterUser() {
        var intent: Intent = Intent(this, RegisterUserActivity::class.java)
        registerUserActivityResult.launch(intent)
    }
}