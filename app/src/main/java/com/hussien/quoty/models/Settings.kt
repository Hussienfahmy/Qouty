package com.hussien.quoty.models

/**
 * Settings.
 *
 * class represents the user preferences
 * @property numberOfQuotes displayed on the recycler view
 * @property tags of the displayed quotes
 * @property languages of the displayed quotes
 * @property font of the displayed quotes
 * @property notificationsEnabled if true the app will send notifications regularly
 * @property notifyEveryMinutes sends notification every specified minutes
 * @property firstTimeAppOpen indicate if the app in first time opens (used to copy the datastore to shared preferences to display it's content on the screen)
 */
data class Settings(
    val numberOfQuotes: Int,
    val tags: Set<String>,
    val languages: Set<String>,
    val font: String,
    val notificationsEnabled: Boolean,
    val notifyEveryMinutes: Int,
    val firstTimeAppOpen: Boolean,
)