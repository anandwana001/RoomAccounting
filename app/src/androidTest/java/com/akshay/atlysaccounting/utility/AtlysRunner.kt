/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.atlysaccounting.utility

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 *
 */
class AtlysRunner : AndroidJUnitRunner() {
  override fun newApplication(
    cl: ClassLoader?,
    className: String?,
    context: Context?
  ): Application {
    return super.newApplication(cl, HiltTestApplication::class.java.name, context)
  }
}