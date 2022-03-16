/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.atlysaccounting.ui.binding

import android.annotation.SuppressLint
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.akshay.atlysaccounting.R
import com.akshay.atlysaccounting.data.model.StatusInvoice
import com.akshay.atlysaccounting.utility.CurrencyUtility
import com.akshay.atlysaccounting.utility.DateParser
import java.util.*

/**
 * Binding Adapter specific to TextView and its relatable views.
 */
@BindingAdapter("app:date", "app:prefix", requireAll = false)
fun setDate(textView: TextView, date: String?, prefix: String?) {
  date?.let {
    val parsedDate = DateParser.formatDateStringToPattern(it, "dd MMM yyyy")
    prefix?.let {
      textView.text = String.format(
        textView.context.resources.getString(R.string.date_with_prefix),
        prefix,
        parsedDate
      )
    } ?: run {
      textView.text = parsedDate
    }
  } ?: run {
    textView.text = textView.context.resources.getString(R.string.due_date_unknown)
  }
}

@BindingAdapter("app:total")
fun setTotalAmount(textView: TextView, total: Double?) {
  total?.let {
    textView.text = CurrencyUtility.getCurrencyOf(Locale.UK, total)
  } ?: run {
    textView.text = textView.context.resources.getString(R.string.invoice_total_amount_zero)
  }
}

@SuppressLint("ResourceAsColor")
@BindingAdapter(
  "app:spannableString",
  "app:startIndex",
  "app:endIndex",
  "app:spanColor",
  requireAll = true
)
fun setSpannableString(
  textView: TextView,
  text: String,
  startIndex: Int,
  endIndex: Int,
  @ColorRes textColor: Int
) {
  val spannableString = SpannableString(text)
  spannableString.setSpan(
    ForegroundColorSpan(textColor), startIndex, endIndex,
    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
  )
  textView.text = spannableString
}

@BindingAdapter("app:invoiceStatus")
fun setStatusText(textView: TextView, status: StatusInvoice?) {
  when (status) {
    StatusInvoice.PAID -> {
      textView.text = textView.context.resources.getString(R.string.paid_status)
      textView.setTextColor(textView.context.resources.getColor(R.color.paid_status_bubble_shade))
    }
    StatusInvoice.PENDING -> {
      textView.text = textView.context.resources.getString(R.string.pending_status)
      textView.setTextColor(textView.context.resources.getColor(R.color.pending_status_bubble_shade))
    }
    StatusInvoice.DRAFT -> {
      textView.text = textView.context.resources.getString(R.string.draft_status)
      textView.setTextColor(textView.context.resources.getColor(R.color.draft_status_bubble_shade))
    }
  }
}

@BindingAdapter("app:statusBackground")
fun setBackgroundForStatus(view: View, status: StatusInvoice?) {
  when (status) {
    StatusInvoice.PAID -> view.background =
      ResourcesCompat.getDrawable(
        view.context.resources,
        R.drawable.paid_status_background,
        null
      )
    StatusInvoice.PENDING -> view.background =
      ResourcesCompat.getDrawable(
        view.context.resources,
        R.drawable.pending_status_background,
        null
      )
    StatusInvoice.DRAFT -> view.background =
      ResourcesCompat.getDrawable(
        view.context.resources,
        R.drawable.draft_status_background,
        null
      )
  }
}

@BindingAdapter("app:statusBubble")
fun setBubbleForStatus(view: View, status: StatusInvoice?) {
  when (status) {
    StatusInvoice.PAID -> view.background =
      ResourcesCompat.getDrawable(
        view.context.resources,
        R.drawable.paid_bubble,
        null
      )
    StatusInvoice.PENDING -> view.background =
      ResourcesCompat.getDrawable(
        view.context.resources,
        R.drawable.pending_bubble,
        null
      )
    StatusInvoice.DRAFT -> view.background =
      ResourcesCompat.getDrawable(
        view.context.resources,
        R.drawable.draft_bubble,
        null
      )
  }
}

@BindingAdapter("app:street", "app:city", "app:country", "app:postcode", requireAll = false)
fun setAddress(
  textView: TextView,
  street: String?,
  city: String?,
  country: String?,
  postcode: String?
) {
  textView.text = String.format(
    textView.context.resources.getString(R.string.complete_address),
    street,
    city,
    postcode,
    country
  )
}

@BindingAdapter("app:quantity", "app:price", requireAll = true)
fun setCompleteBill(textView: TextView, quantity: Int, price: Double) {
  textView.text = String.format(
    textView.context.resources.getString(R.string.quantity_price_bill),
    quantity,
    price
  )
}
