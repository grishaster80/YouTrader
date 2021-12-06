package com.technopark.youtrader.network.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

class FirebaseService : IAuthService {
    private var auth: FirebaseAuth = Firebase.auth

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

    override suspend fun sighOut(): Flow<Boolean> = flow {
        auth.signOut()
        // TODO wrap in task and emit real value
        emit(true)
    }

    override suspend fun updatePassword(password: String): Flow<Boolean?> = flow {
        emit(auth.currentUser?.updatePassword(password)?.awaitVoid())
    }

    companion object {
        private const val TAG = "FirebaseServiceTag"
    }
}
