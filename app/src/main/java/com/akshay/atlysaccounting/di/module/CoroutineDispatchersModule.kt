/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.atlysaccounting.di.module

import com.akshay.atlysaccounting.di.qualifier.DefaultDispatcher
import com.akshay.atlysaccounting.di.qualifier.IoDispatcher
import com.akshay.atlysaccounting.di.qualifier.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Provider for all three types of [CoroutineDispatcher].
 */
@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatchersModule {

  @IoDispatcher
  @Provides
  fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

  @DefaultDispatcher
  @Provides
  fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

  @MainDispatcher
  @Provides
  fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}
