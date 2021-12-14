package com.technopark.youtrader.network.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProfileFirebaseRepository: IProfileFirebaseRepository {

    private val auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.database.reference

    private var fullName: String = "undefined"
    private var passcode: String = "undefined"

    private fun getUserId() = auth.currentUser?.uid

    override suspend fun setFullNameToFirebase(username: String) {
        getUserId()?.let {
            db.child("ProfileData").child(it).child("fullName").setValue(username)
        }
    }

    override suspend fun setPasscodeToFirebase(passcode: String) {
        getUserId()?.let {
            db.child("ProfileData").child(it).child("passcode").setValue(passcode)
        }
    }

    override suspend fun getFullNameFromFirebase(): Flow<String> = flow{
        emit(fullName)
    }.flowOn(Dispatchers.IO)

    override suspend fun getPasscodeFromFirebase(): Flow<String>  = flow{
        emit(passcode)
    }.flowOn(Dispatchers.IO)

    override fun addListener(listener: () -> Unit) {
        val localListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                fullName = "undefined"
                fullName = (dataSnapshot.child("fullName").value?:"undefined").toString()
                passcode = (dataSnapshot.child("passcode").value?:"undefined").toString()
                listener()
            }

            override fun onCancelled(databaseError: DatabaseError) {
//                 Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        getUserId()?.let {
            db.child("ProfileData").child(it).addValueEventListener(localListener)
        }
    }

}