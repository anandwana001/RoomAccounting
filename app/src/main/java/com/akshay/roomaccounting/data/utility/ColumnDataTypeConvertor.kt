/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.roomaccounting.data.utility

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.akshay.roomaccounting.data.model.Address
import com.akshay.roomaccounting.data.model.SingleEntryInvoice
import com.akshay.roomaccounting.data.model.StatusInvoice
import com.akshay.roomaccounting.utility.MoshiAdapters
import javax.inject.Inject

/**
 * As room doesn't allow saving object reference to the entity, this convertor supports the
 * room in converting the object references.
 */
@ProvidedTypeConverter
class ColumnDataTypeConvertor @Inject constructor(
  val moshiAdapters: MoshiAdapters
) {

  @TypeConverter
  fun fromStringToSingleEntryInvoiceList(value: String?): List<SingleEntryInvoice>? {
    return value?.let {
      moshiAdapters.toListfromJson(value)
    } ?: kotlin.run {
      null
    }
  }

  @TypeConverter
  fun fromSingleEntryInvoiceList(value: List<SingleEntryInvoice>?): String? {
    return value?.let {
      moshiAdapters.toJsonFromList(value)
    } ?: run {
      null
    }
  }

  @TypeConverter
  fun fromStringToAddress(value: String?): Address? {
    return value?.let {
      moshiAdapters.fromJson(value)
    } ?: run {
      null
    }
  }

  @TypeConverter
  fun fromAddress(value: Address?): String? {
    return value?.let {
      moshiAdapters.toJson(value)
    } ?: run {
      null
    }
  }

  /**
   * Converters for Enum type.
   */
  @TypeConverter
  fun fromStatusInvoice(status: StatusInvoice): String {
    return status.name
  }

  @TypeConverter
  fun toStatusInvoice(status: String): StatusInvoice {
    return StatusInvoice.valueOf(status)
  }
}
