package com.technopark.youtrader.network.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.technopark.youtrader.model.CryptoCurrencyTransaction
import com.technopark.youtrader.model.PortfolioCurrencyInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FirebaseRepository: IFirebaseRepository {

    private val auth: FirebaseAuth = Firebase.auth
    private val db = Firebase.database.reference

    private val cryptoCurrencyTransactionList: MutableList<CryptoCurrencyTransaction> = mutableListOf()

    private fun getUserId() = auth.currentUser?.uid

    override fun addListener(listener: () -> Unit) {
        val localListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                cryptoCurrencyTransactionList.clear();
                dataSnapshot.children.mapNotNullTo(cryptoCurrencyTransactionList) {
                    it.getValue(CryptoCurrencyTransaction::class.java)
                }
                listener()
            }

            override fun onCancelled(databaseError: DatabaseError) {
               // Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        getUserId()?.let { db.child("transactions").child(it).addValueEventListener(localListener)
        }
    }

    override suspend fun getCurrencyTransactionsById(currencyId: String): Flow<List<CryptoCurrencyTransaction>> = flow {
        emit(cryptoCurrencyTransactionList.filter { it.id == currencyId }.toList())
    }.flowOn(Dispatchers.IO)

    override suspend fun insertTransaction(currencyId: String, amount: Double, price: Double) {

        val timestamp = System.currentTimeMillis()
        val currencyTransaction = CryptoCurrencyTransaction(currencyId, timestamp, amount, price)

        getUserId()?.let {
            db.child("transactions").child(it).child(timestamp.toString()).setValue(currencyTransaction)
        }
    }

    override suspend fun getPortfolioCurrencies(): Flow<List<PortfolioCurrencyInfo>> = flow {

        val list = cryptoCurrencyTransactionList.groupByTo(mutableMapOf()) {
            it.id
        }.mapValues { entry ->
            PortfolioCurrencyInfo(entry.key,entry.value.sumOf{it.amount},entry.value.sumOf{it.price})
        }.values.toList()
        emit(list)
    }.flowOn(Dispatchers.IO)

}