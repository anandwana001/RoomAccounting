/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.atlysaccounting.di.module

import android.content.Context
import androidx.room.Room
import com.akshay.atlysaccounting.data.dao.InvoiceDao
import com.akshay.atlysaccounting.data.helper.DatabaseHelper
import com.akshay.atlysaccounting.data.helper.DatabaseHelperImpl
import com.akshay.atlysaccounting.data.persistence.AppDatabase
import com.akshay.atlysaccounting.data.persistence.AppDatabaseCallback
import com.akshay.atlysaccounting.data.utility.ColumnDataTypeConvertor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * A module which provides all the dependencies required by the database.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Provides
  @Singleton
  fun provideAppDatabase(
    @ApplicationContext context: Context,
    callback: AppDatabaseCallback,
    columnDataTypeConvertor: ColumnDataTypeConvertor
  ): AppDatabase {
    return Room.databaseBuilder(
      context.applicationContext,
      AppDatabase::class.java,
      AppDatabase.DATABASE_NAME
    ).addTypeConverter(columnDataTypeConvertor)
      .addCallback(callback)
      .build()
  }

  @Provides
  @Singleton
  fun provideInvoiceDao(appDatabase: AppDatabase): InvoiceDao = appDatabase.invoiceDao()

  @Provides
  @Singleton
  fun provideDatabaseHelper(databaseHelper: DatabaseHelperImpl): DatabaseHelper = databaseHelper
}
