/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.roomaccounting.utility

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions
import com.akshay.roomaccounting.HiltTestActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Hilt doesn't support testing fragments in isolation.
 * This is a workaround provided by official Hilt team to test Fragments.
 */
@ExperimentalCoroutinesApi
inline fun <reified T : Fragment> launchFragmentInHiltContainer(
  fragmentArgs: Bundle? = null,
  @StyleRes themeResId: Int = androidx.fragment.testing.R.style.FragmentScenarioEmptyFragmentActivityTheme,
  crossinline action: Fragment.() -> Unit = {}
): ActivityScenario<HiltTestActivity> {

  val startActivityIntent = Intent.makeMainActivity(
    ComponentName(
      ApplicationProvider.getApplicationContext(),
      HiltTestActivity::class.java
    )
  ).putExtra(FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY, themeResId)

  return ActivityScenario.launch<HiltTestActivity>(startActivityIntent).onActivity { activity ->
    val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
      Preconditions.checkNotNull(T::class.java.classLoader),
      T::class.java.name
    )
    fragment.arguments = fragmentArgs
    activity.supportFragmentManager
      .beginTransaction()
      .add(android.R.id.content, fragment, "")
      .commitNow()
    fragment.action()
  }
}
