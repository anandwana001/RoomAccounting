/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.atlysaccounting.di

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.akshay.atlysaccounting.dao.FakeDatabaseHelperImpl
import com.akshay.atlysaccounting.data.dao.InvoiceDao
import com.akshay.atlysaccounting.data.helper.DatabaseHelper
import com.akshay.atlysaccounting.data.persistence.AppDatabase
import com.akshay.atlysaccounting.data.utility.ColumnDataTypeConvertor
import com.akshay.atlysaccounting.di.module.DatabaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

/**
 * Provider for Room Database Dependencies for Unit Testing.
 */
@Module
@TestInstallIn(
  components = [SingletonComponent::class],
  replaces = [DatabaseModule::class]
)
object UnitTestModule {

  @Provides
  fun provideTestAppDatabase(
    columnDataTypeConvertor: ColumnDataTypeConvertor
  ) =
    Room.inMemoryDatabaseBuilder(
      ApplicationProvider.getApplicationContext(),
      AppDatabase::class.java
    )
      .allowMainThreadQueries()
      .addTypeConverter(columnDataTypeConvertor)
      .build()

  @Provides
  fun provideTestInvoiceDao(appDatabase: AppDatabase): InvoiceDao = appDatabase.invoiceDao()

  @Provides
  fun provideTestDatabaseHelper(invoiceDao: InvoiceDao): DatabaseHelper =
    FakeDatabaseHelperImpl(invoiceDao)
}
