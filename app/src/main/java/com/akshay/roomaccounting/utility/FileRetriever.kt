/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.roomaccounting.utility

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.FileNotFoundException
import javax.inject.Inject

/**
 * Utility class to retrieve the file from assets directory.
 */
class FileRetriever @Inject constructor(
  @ApplicationContext private val context: Context,
) {

  /**
   * Retrieve the JSON files from the assets.
   */
  fun loadJsonFileFromAssets(assetName: String): String {
    val jsonString: String
    try {
      jsonString = context.assets.open(assetName).bufferedReader().use {
        it.readText()
      }
    } catch (e: FileNotFoundException) {
      error("Asset doesn't exist: $assetName")
    }
    return jsonString
  }
}