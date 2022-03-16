/**
 * Created by anandwana001 on
 * 06, March, 2022
 **/

package com.akshay.atlysaccounting.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData

/**
 * BindingAdapter specific to view groups.
 */
@BindingAdapter("app:isVisible")
fun <T> setVisibility(view: View, livedata: LiveData<List<T>>) {
  if(livedata.value != null) {
    view.visibility = View.VISIBLE
  } else {
    view.visibility = View.GONE
  }
}
