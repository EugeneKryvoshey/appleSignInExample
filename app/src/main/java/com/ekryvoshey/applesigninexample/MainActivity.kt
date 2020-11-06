package com.ekryvoshey.applesigninexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ekryvoshey.applesigninexample.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.zze

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            signInWithApple()
        }
    }

    private fun signInWithApple() {
        val pending = FirebaseAuth.getInstance().pendingAuthResult
        if (pending != null) {
            pending
                .addOnSuccessListener { onSuccess(it) }
                .addOnFailureListener { onFailure(it) }
        } else {
            val provider = OAuthProvider.newBuilder("apple.com").apply {
                scopes = listOf("email", "name")
                addCustomParameter("locale", "uk")
            }.build()
            FirebaseAuth.getInstance().startActivityForSignInWithProvider(this, provider)
                .addOnSuccessListener { onSuccess(it) }
                .addOnFailureListener { onFailure(it) }
        }
    }

    private fun onSuccess(authResult: AuthResult) {
        val token = (authResult.credential as zze).idToken
        Snackbar.make(
            binding.root,
            "Success to login with Apple ID",
            2
        ).show()
    }

    private fun onFailure(exception: Exception) {
        val errorText = exception.message ?: "Error unknown"
        Snackbar.make(
            binding.root,
            "Failed to login with Apple ID: $errorText",
            5
        ).show()
    }
}
