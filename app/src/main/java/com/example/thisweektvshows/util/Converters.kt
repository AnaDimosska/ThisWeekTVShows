package com.example.thisweektvshows.util

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.joinToString(separator = ",")
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.split(",")?.map { it.trim() }
    }

    @TypeConverter
    fun fromIntList(intList: List<Int>): String {
        return intList.joinToString(",")
    }

    @TypeConverter
    fun toIntList(intListString: String): List<Int> {
        return intListString.split(",").map { it.toInt() }
    }
}