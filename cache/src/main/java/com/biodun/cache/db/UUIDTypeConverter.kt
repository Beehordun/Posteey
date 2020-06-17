package com.biodun.cache.db

import androidx.room.TypeConverter
import java.util.UUID

class UUIDTypeConverter {

    @TypeConverter
    fun fromUUIDToString(data: UUID): String = data.toString()

    @TypeConverter
    fun stringToUUID(data: String?): UUID {
        if (data == null) {
            return UUID.randomUUID()
        }

        return UUID.fromString(data)

    }
}