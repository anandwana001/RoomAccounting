/**
 * Created by anandwana001 on
 * 04, March, 2022
 **/

package com.akshay.roomaccounting.ui.binding

import android.widget.Button
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.akshay.roomaccounting.R
import com.akshay.roomaccounting.data.model.StatusInvoice

/**
 * BindingAdapter specific to Button and its relatable Views.
 */
@BindingAdapter("app:checkStatus")
fun setButtonStyleBasedOnStatus(button: Button, status: StatusInvoice?) {
  status?.let {
    button.apply {
      if(status == StatusInvoice.PAID) {
        background =
          ResourcesCompat.getDrawable(
            button.context.resources,
            R.drawable.mark_as_paid_background_disable,
            null
          )
        isEnabled = false
      } else {
        background =
          ResourcesCompat.getDrawable(
            button.context.resources,
            R.drawable.mark_as_paid_background,
            null
          )
        isEnabled = true
      }
    }
  }
}
