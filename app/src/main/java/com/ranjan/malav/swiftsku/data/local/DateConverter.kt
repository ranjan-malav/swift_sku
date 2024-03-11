package com.ranjan.malav.swiftsku.data.local

import androidx.room.TypeConverter
import java.util.Date


class DateConverters {
    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}