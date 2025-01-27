package com.spapalamprou.turtlesafe.data.api

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Extension function to change the format of a date string from "dd/MM/yyyy" to "yyyy-MM-dd".
 *
 * @return The date string converted to "yyyy-MM-dd" format.
 */
fun String.changeDateFormat(): String {
    val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date: Date = inputFormat.parse(this)
    val outputDateString = outputFormat.format(date)
    return outputDateString
}

/**
 * Extension function to add the ":00" suffix to the time string, if it is not empty.
 *
 * @return The time string with ":00" suffix or `null` if the time string is empty.
 */
fun String.changeTimeFormatNullable(): String? {
    if (this.isEmpty()) {
        return null
    }
    return "$this:00"
}

/**
 * Extension function to add the suffix ":00" to a time string.
 *
 * @return The time string with ":00" suffix.
 */
fun String.changeTimeFormat(): String {
    return "$this:00"
}