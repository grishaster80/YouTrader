package com.technopark.youtrader.network

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseService : IAuthService {
    private var auth: FirebaseAuth = Firebase.auth

    override fun checkSignIn(email: String): Boolean {
        val user = auth.currentUser
        if (user != null && user.email == email) {
            Log.d(TAG, "User: $email logged in already")
            return true
        }
        Log.d(TAG, "User: $email isn't logged in")
        return false
    }

    override fun signIn(email: String, password: String) {
        if (checkSignIn(email)) {
            Log.d(TAG, "User: $email logged in already")
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                    } else {
                        Log.d(TAG, "signInWithEmail:failure", task.exception)
                    }
                }
        }
    }

    override fun sighUp(email: String, password: String) {
        if (!checkSignIn(email)) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                    } else {
                        Log.d(TAG, "createUserWithEmail:failure", task.exception)
                    }
                }
        }
    }

    override fun sighOut() {
        Log.d(TAG, "sighOut")
        auth.signOut()
    }

    override fun updatePassword(password: String) {
        auth.currentUser?.updatePassword(password)
    }

    companion object {
        private const val TAG = "FirebaseService"
    }
}
