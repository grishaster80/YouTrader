package com.technopark.youtrader.network.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlin.coroutines.resumeWithException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine

suspend fun Task<AuthResult>.awaitFirebaseAuth(): CommonAuthResult? {
    if (isComplete) {
        val e = exception
        return if (e == null) {
            if (isCanceled) {
                throw CancellationException("Task $this was cancelled normally.")
            } else {
                handleAuthTaskWithoutException()
            }
        } else {
            throw e
        }
    }

    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            val e = exception
            if (e == null) {
                if (isCanceled) cont.cancel() else {
                    val commonResult = CommonAuthResult(result?.user?.email)
                    cont.resume(commonResult, null)
                }
            } else {
                cont.resumeWithException(e)
            }
        }
    }
}

fun Task<AuthResult>.handleAuthTaskWithoutException(): CommonAuthResult {
    if (isSuccessful) {
        return CommonAuthResult(result?.user?.email)
    } else {
        throw FirebaseUnknownErrorException()
    }
}

suspend fun Task<Void>.awaitVoid(): Boolean {
    if (isComplete) {
        val e = exception
        return if (e == null) {
            if (isCanceled) {
                throw CancellationException("Task $this was cancelled normally.")
            } else {
                handleVoidTaskWithoutException()
            }
        } else {
            throw e
        }
    }

    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            val e = exception
            if (e == null) {
                if (isCanceled) cont.cancel() else {
                    cont.resume(true, null)
                }
            } else {
                cont.resumeWithException(e)
            }
        }
    }
}

fun Task<Void>.handleVoidTaskWithoutException(): Boolean {
    if (isSuccessful) {
        return true
    } else {
        throw FirebaseUnknownErrorException()
    }
}
