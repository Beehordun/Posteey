package com.example.posteey.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar
import java.util.concurrent.TimeUnit
import kotlin.math.abs

object DateTimeUtil {

    fun getFormattedDateTime(dateTimeString: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val timeNow = Calendar.getInstance(Locale.getDefault()).time

        try {
            val dateTime = formatter.parse(dateTimeString)!!
            val diff = abs(timeNow.time - dateTime.time)
            val minutes = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS)
            val hours = TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS)
            val days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)

            return when{
                minutes < 60 -> "$minutes minutes ago"
                hours < 24 -> "$hours hours ago"
                days.toInt() == 1 -> "$days day ago"
                days >= 1 -> "$days days ago"
                else -> ""
            }
        } catch (exception: ParseException) {
            return ""
        }
    }
}