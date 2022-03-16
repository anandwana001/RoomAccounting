/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.atlysaccounting.ui.binding

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.akshay.atlysaccounting.ui.recyclerview.AtlysRecyclerViewAdapter

/**
 * Binding Adapter specific to RecyclerView and ite relatable views.
 */
@BindingAdapter("app:data")
fun <T : Any> setDataToAtlyRecyclerViewAdapter(
  recyclerView: RecyclerView,
  liveData: LiveData<List<T>?>
) {
  val dataList = liveData.value
  val adapter: AtlysRecyclerViewAdapter<T> = recyclerView.adapter as AtlysRecyclerViewAdapter<T>
  require(recyclerView.adapter is AtlysRecyclerViewAdapter<*>) { "Can only bind data to a AtlysRecyclerViewAdapter." }
  if (dataList != null) {
    adapter.setDataUnchecked(dataList)
  }
}

/**
 * Use app:list while passing list as an argument for RecyclerView Adapter.
 */
@BindingAdapter("app:list")
fun <T : Any> setListToAtlyRecyclerViewAdapter(
  recyclerView: RecyclerView,
  dataList: List<T>?
) {
  val adapter: AtlysRecyclerViewAdapter<T> = recyclerView.adapter as AtlysRecyclerViewAdapter<T>
  require(recyclerView.adapter is AtlysRecyclerViewAdapter<*>) { "Can only bind data to a AtlysRecyclerViewAdapter." }
  if (dataList != null) {
    adapter.setDataUnchecked(dataList)
  }
}
