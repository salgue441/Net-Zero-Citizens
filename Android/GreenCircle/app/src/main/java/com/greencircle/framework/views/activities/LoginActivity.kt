package com.greencircle.framework.views.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.greencircle.R
import com.greencircle.databinding.ActivityLoginBinding
import com.greencircle.framework.views.MainActivity
import com.greencircle.utils.Constants

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
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
                        Log.d("GoogleSignIn", "Signed in successfully")
                        Log.d("GoogleSignIn", "account: $account")
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Listener Methods
        registerCompanyOnClickListener()
        registerUserOnClickListener()

        // Google Login
        googleLoginListener()
    }

    // Google Login Methods
    private fun googleLoginListener() {
        // Google Login
        val googleButton = binding.root.findViewById<View>(R.id.sign_in_button)

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(Constants.GOOGLE_CLIENT_ID)
                .requestEmail()
                .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        Log.d("GoogleSignIn", "acct: $acct")
        if (acct != null) {
            // Cambiar por navigateToForm()
            googleSignOut(mGoogleSignInClient)
        }

        googleButton.setOnClickListener {
            when (it.id) {
                R.id.sign_in_button -> googleLogin(mGoogleSignInClient)
            }
        }
    }

    private fun googleLogin(mGoogleSignInClient: GoogleSignInClient) {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        googleSignInActivityResult.launch(signInIntent)
    }

    private fun googleSignOut(mGoogleSignInClient: GoogleSignInClient) {
        mGoogleSignInClient.signOut()
    }

    // On Click Listener Methods
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