/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.atlysaccounting.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.akshay.atlysaccounting.data.dao.InvoiceDao
import com.akshay.atlysaccounting.data.model.Invoice
import com.akshay.atlysaccounting.data.utility.ColumnDataTypeConvertor

/**
 * This class serves as an main access point to the persisted data and
 * configure the local database.
 */
@Database(entities = [Invoice::class], version = 1)
@TypeConverters(ColumnDataTypeConvertor::class)
abstract class AppDatabase : RoomDatabase() {

  abstract fun invoiceDao(): InvoiceDao

  companion object {
    const val DATABASE_NAME = "AtlysAccounting"
  }
}