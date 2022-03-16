/**
 * Created by anandwana001 on
 * 10, March, 2022
 **/

package com.akshay.atlysaccounting.utility

import android.content.Context
import androidx.test.core.app.ApplicationProvider

/**
 * Utility to handle resources for tests.
 */
object ResourceHandler {

  fun getResourceString(id: Int): String {
    val targetContext: Context = ApplicationProvider.getApplicationContext()
    return targetContext.resources.getString(id)
  }
}