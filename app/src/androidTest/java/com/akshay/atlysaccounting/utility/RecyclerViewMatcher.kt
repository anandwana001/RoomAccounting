/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.atlysaccounting.utility

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.akshay.atlysaccounting.R
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

/**
 * Helper Matcher to instrumentation test items in RecyclerView.
 */

fun atPositionOnView(recyclerViewId: Int, position: Int, targetViewId: Int): Matcher<View> {
  return object : TypeSafeMatcher<View>() {
    var resources: Resources? = null
    var childView: View? = null

    override fun describeTo(description: Description) {
      var idDescription = recyclerViewId.toString()
      if (this.resources != null) {
        idDescription = try {
          this.resources!!.getResourceName(recyclerViewId)
        } catch (var4: Resources.NotFoundException) {
          "$recyclerViewId (resource name not found)"
        }
      }
      description.appendText("with id: $idDescription")
    }

    public override fun matchesSafely(view: View): Boolean {
      this.resources = view.resources
      if (childView == null) {
        val recyclerView = view.rootView.findViewById<View>(recyclerViewId) as? RecyclerView
        if (recyclerView?.id == recyclerViewId) {
          childView = recyclerView.findViewHolderForAdapterPosition(position)?.itemView
        } else {
          return false
        }
      }
      return if (targetViewId == -1) {
        view === childView
      } else {
        view === childView?.findViewById<View>(targetViewId)
      }
    }
  }
}

/**
 * Returns item count ViewAssertion for a recycler view.
 **/
fun hasItemCount(count: Int): ViewAssertion {
  return RecyclerViewItemCountAssertion(count)
}

private class RecyclerViewItemCountAssertion(private val count: Int) : ViewAssertion {
  override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
    if (noViewFoundException != null) {
      throw noViewFoundException
    }
    check(view is RecyclerView) { "The asserted view is not RecyclerView" }
    checkNotNull(view.adapter) { "No adapter is assigned to RecyclerView" }
    ViewMatchers.assertThat(
      "RecyclerView item count",
      view.adapter!!.itemCount,
      CoreMatchers.equalTo(count)
    )
  }
}

/**
 * Helper function to test if the text matches on given recyclerview item or not.
 */
fun checkTextOnRecyclerViewItem(recyclerViewId: Int, itemId: Int, text: String, position: Int) {
  onView(
    atPositionOnView(
      recyclerViewId = recyclerViewId,
      position = position,
      targetViewId = itemId
    )
  ).check(matches(withText(text)))
}
