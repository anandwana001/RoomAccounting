/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.atlysaccounting.di.qualifier

import kotlinx.coroutines.CoroutineScope
import javax.inject.Qualifier

/**
 * Qualifier for application level [CoroutineScope].
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope