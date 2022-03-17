/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.roomaccounting.di.module

import com.akshay.roomaccounting.data.utility.ColumnDataTypeConvertor
import com.akshay.roomaccounting.utility.MoshiAdapters
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Provider for all database helper dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseHelperModule {

  @Provides
  fun provideMoshi(): Moshi {
    return Moshi.Builder()
      .addLast(KotlinJsonAdapterFactory())
      .build()
  }

  @Provides
  fun provideColumnDataTypeConverter(
    moshiAdapters: MoshiAdapters
  ): ColumnDataTypeConvertor {
    return ColumnDataTypeConvertor(moshiAdapters)
  }
}
