/**
 * Created by anandwana001 on
 * 02, March, 2022
 **/

package com.akshay.atlysaccounting.ui.home

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatDelegate
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import com.akshay.atlysaccounting.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

/**
 * Tests for [HomeActivity].
 */
@HiltAndroidTest
class HomeActivityTest {

  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @get:Rule
  var activityScenarioRule = activityScenarioRule<HomeActivity>()

  @Test
  fun testHomeActivity_profileAvatar_isDisplayed() {
    onView(withId(R.id.avatar_image_view))
      .check(matches(isDisplayed()))
  }

  @Test
  fun testHomeActivity_landscape_profileAvatar_isDisplayed() {
    setOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    onView(withId(R.id.avatar_image_view))
      .check(matches(isDisplayed()))
  }

  @Test
  fun testHomeActivity_appLogo_isDisplayed() {
    onView(withId(R.id.app_logo_image_view))
      .check(matches(isDisplayed()))
  }

  @Test
  fun testHomeActivity_landscape_appLogo_isDisplayed() {
    setOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    onView(withId(R.id.app_logo_image_view))
      .check(matches(isDisplayed()))
  }

  @Test
  fun testHomeActivity_themeIcon_isDisplayed() {
    activityScenarioRule.scenario.onActivity {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
    onView(withId(R.id.theme_image_view))
      .check(matches(isDisplayed()))
  }

  @Test
  fun testHomeActivity_landscape_themeIcon_isDisplayed() {
    setOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    activityScenarioRule.scenario.onActivity {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
    onView(withId(R.id.theme_image_view))
      .check(matches(isDisplayed()))
  }

  @Test
  fun testHomeActivity_lightThemeIcon_isDisplayed() {
    activityScenarioRule.scenario.onActivity {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    onView(withId(R.id.theme_image_view))
      .check(matches(isDisplayed()))
  }

  @Test
  fun testHomeActivity_nightThemeIcon_isDisplayed() {
    activityScenarioRule.scenario.onActivity {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    onView(withId(R.id.theme_image_view))
      .check(matches(isDisplayed()))
  }

  /**
   * Helper function to change the orientation of screen.
   */
  private fun setOrientation(orientation: Int) {
    activityScenarioRule.scenario.onActivity {
      it.requestedOrientation = orientation
    }
  }
}