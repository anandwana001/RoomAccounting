/**
 * Created by anandwana001 on
 * 27, February, 2022
 **/

package com.akshay.atlysaccounting.data.persistence

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.akshay.atlysaccounting.data.dao.InvoiceDao
import com.akshay.atlysaccounting.data.model.Invoice
import com.akshay.atlysaccounting.data.utility.Constants
import com.akshay.atlysaccounting.di.qualifier.ApplicationScope
import com.akshay.atlysaccounting.utility.FileRetriever
import com.akshay.atlysaccounting.utility.MoshiAdapters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

/**
 * Callback for the [AppDatabase].
 */
class AppDatabaseCallback @Inject constructor(
  private val invoiceDao: Provider<InvoiceDao>,
  private val fileRetriever: FileRetriever,
  private val moshiAdapters: MoshiAdapters,
  @ApplicationScope private val coroutineScope: CoroutineScope
) : RoomDatabase.Callback() {

  override fun onCreate(db: SupportSQLiteDatabase) {
    super.onCreate(db)

    /**
     * Pre-populating the data.json file.
     */
    coroutineScope.launch {
      val computedInvoiceList = computePrepopulateData(Constants.assets_file_name)
      invoiceDao.get().insertListOfInvoice(computedInvoiceList)
    }
  }

  private fun computePrepopulateData(assetName: String): List<Invoice> {
    val jsonString = fileRetriever.loadJsonFileFromAssets(assetName)
    val invoiceList = checkNotNull(moshiAdapters.toListfromJson<Invoice>(jsonString)) {
      "JSON file failed to parse"
    }
    return invoiceList
  }
}
