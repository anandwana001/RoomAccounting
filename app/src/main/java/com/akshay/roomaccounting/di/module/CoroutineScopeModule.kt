/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.roomaccounting.di.module

import com.akshay.roomaccounting.di.qualifier.ApplicationScope
import com.akshay.roomaccounting.di.qualifier.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

/**
 * Provider for a [CoroutineScope].
 */
@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopeModule {

  @Singleton
  @Provides
  @ApplicationScope
  fun providesCoroutineScope(
    @IoDispatcher dispatcher: CoroutineDispatcher
  ): CoroutineScope = CoroutineScope(dispatcher)
}
