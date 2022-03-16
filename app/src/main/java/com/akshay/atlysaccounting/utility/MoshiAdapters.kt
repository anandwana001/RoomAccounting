/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.atlysaccounting.utility

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

/**
 * This is a Moshi specific adapter to parse the json of object or list data.
 */
class MoshiAdapters @Inject constructor(
  val moshi: Moshi
) {

  inline fun <reified T> toJsonFromList(value: List<T>): String {
    return toListJsonAdapter<T>().toJson(value)
  }

  inline fun <reified T> toListfromJson(value: String): List<T>? {
    return toListJsonAdapter<T>().fromJson(value)
  }

  inline fun <reified T> toJson(value: T): String {
    return toJsonAdapter<T>().toJson(value)
  }

  inline fun <reified T> fromJson(value: String): T? {
    return toJsonAdapter<T>().fromJson(value)
  }

  inline fun <reified T> toListJsonAdapter(): JsonAdapter<List<T>> {
    val listType = Types.newParameterizedType(List::class.java, T::class.java)
    return moshi.adapter(listType)
  }

  inline fun <reified T> toJsonAdapter(): JsonAdapter<T> {
    return moshi.adapter(T::class.java)
  }
}