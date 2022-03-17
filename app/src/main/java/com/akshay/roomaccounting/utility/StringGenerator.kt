/**
 * Created by anandwana001 on
 * 06, March, 2022
 **/

package com.akshay.roomaccounting.utility

/**
 * Utility for string related work.
 */
object StringGenerator {

  /**
   * Generates the id in the form of `AB1234` where
   *  First two char are UPPERCASE Alphabet and other Four char are Numeric.
   */
  fun generateUniqueId(): String {
    val prefix = ('A'..'Z').map { it }.shuffled().subList(0, 2).joinToString("")
    val suffix = (0..9).map { it }.shuffled().subList(0, 4).joinToString("")
    return "$prefix$suffix"
  }
}
