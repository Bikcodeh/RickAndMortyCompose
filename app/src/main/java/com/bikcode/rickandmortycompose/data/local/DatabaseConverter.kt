package com.bikcode.rickandmortycompose.data.local

import androidx.room.TypeConverter
import com.bikcode.rickandmortycompose.domain.model.Location
import com.bikcode.rickandmortycompose.domain.model.Origin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class DatabaseConverter {

    @TypeConverter
    fun listOfStringToString(values: List<String>): String {
        return Gson().toJson(values)
    }

    @TypeConverter
    fun stringToListOfString(value: String): ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun stringFromLocation(location: Location?): String? {
        return Gson().toJson(location)
    }

    @TypeConverter
    fun locationFromString(jsonString: String?): Location? {
        return Gson().fromJson(jsonString, Location::class.java)
    }

    @TypeConverter
    fun stringFromOrigin(origin: Origin?): String? {
        return Gson().toJson(origin)
    }

    @TypeConverter
    fun originFromString(jsonString: String?): Origin? {
        return Gson().fromJson(jsonString, Origin::class.java)
    }
}