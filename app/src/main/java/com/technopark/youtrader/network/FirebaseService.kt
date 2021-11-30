package com.technopark.youtrader.network

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.technopark.youtrader.network.auth.CommonAuthResult
import com.technopark.youtrader.network.auth.awaitFirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class FirebaseService : IAuthService {
    private var auth: FirebaseAuth = Firebase.auth

    fun checkSignIn(email: String): Boolean {
        val user = auth.currentUser
        if (user != null && user.email == email) {
            Log.d(TAG, "User: $email logged in already")
            return true
        }
        Log.d(TAG, "User: $email isn't logged in")
        return false
    }

    override suspend fun authenticate(email: String, password: String): Flow<CommonAuthResult?> =
        flow {
            emit(
                auth.signInWithEmailAndPassword(email, password)
                    .awaitFirebaseAuth()
            )
        }

    override suspend fun registerUser(email: String, password: String): Flow<CommonAuthResult?> =
        flow {
            emit(
                auth.createUserWithEmailAndPassword(email, password)
                    .awaitFirebaseAuth()
            )
        }.onEach { Log.d(TAG, auth.currentUser?.email.toString()) }

    override fun sighOut() {
        Log.d(TAG, "sighOut")
        auth.signOut()
    }

    override fun updatePassword(password: String) {
        auth.currentUser?.updatePassword(password)
    }

    companion object {
        private const val TAG = "FirebaseServiceTag"
    }
}
