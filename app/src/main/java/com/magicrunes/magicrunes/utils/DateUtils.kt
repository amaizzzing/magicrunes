package com.magicrunes.magicrunes.utils

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

object DateUtils{
    private var format = DateTimeFormat.forPattern("dd.MM.YYYY")

    fun isTheSameDay(dayOne: DateTime?, dayTwo: DateTime?): Boolean =
        dayOne?.dayOfMonth == dayTwo?.dayOfMonth &&
                dayOne?.monthOfYear == dayTwo?.monthOfYear &&
                dayOne?.year == dayTwo?.year

    fun getStringDate(date: Long) = format.print(date)
}
