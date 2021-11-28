package com.technopark.youtrader.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.technopark.youtrader.database.transaction_entity.LocalCryptoCurrencyTransaction
import com.technopark.youtrader.database.transaction_entity.Transaction

import com.technopark.youtrader.model.CryptoCurrency
import com.technopark.youtrader.model.CryptoCurrencyTransaction


@Database(
    entities = [
        CryptoCurrency::class,
        CryptoCurrencyTransaction::class,
        Transaction::class,
        LocalCryptoCurrencyTransaction::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cryptoCurrencyDao(): CryptoCurrencyDao
    abstract  fun cryptoTransactionDao() : CryptoTransactionDao
    object MIGRATION_1_2: Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `currencyTransaction` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `price` REAL NOT NULL, `amount` REAL NOT NULL, `timestamp` TEXT DEFAULT CURRENT_TIMESTAMP, `cryptoCurrencyId` TEXT NOT NULL, FOREIGN KEY(`cryptoCurrencyId`) REFERENCES `LocalCryptoCurrencyTransaction`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION )")
            database.execSQL("CREATE TABLE IF NOT EXISTS `LocalCryptoCurrencyTransaction` (`id` TEXT NOT NULL, `symbol` TEXT NOT NULL, `name` TEXT NOT NULL, PRIMARY KEY(`id`))")
            database.execSQL("INSERT INTO LocalCryptoCurrencyTransaction values(`1`,`BTC`,`Bitcoin`)")
            database.execSQL("INSERT INTO currencyTransaction (price,amount,timestamp,cryptoCurrencyId) values(12.888,0.00123,`1637722187`,`1`)")
            database.execSQL("INSERT INTO currencyTransaction (price,amount,timestamp,cryptoCurrencyId) values(6.456,-0.00023,`1619578187`,`1`)")
            database.execSQL("INSERT INTO currencyTransaction (price,amount,timestamp,cryptoCurrencyId) values(300.55,0.0011,`1619491787`,`1`)")
        }
    }
}
