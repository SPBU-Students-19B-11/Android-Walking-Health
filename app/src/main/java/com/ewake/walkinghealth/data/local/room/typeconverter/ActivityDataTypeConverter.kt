package com.ewake.walkinghealth.data.local.room.typeconverter

import androidx.room.TypeConverter
import com.ewake.walkinghealth.data.local.room.entity.UserActivityData
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class ActivityDataTypeConverter {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val type = Types.newParameterizedType(MutableList::class.java, UserActivityData::class.java)

    private val adapter = moshi.adapter<MutableList<UserActivityData>>(type)

    @TypeConverter
    fun dataToJson(data: MutableList<UserActivityData>): String {
        return adapter.toJson(data)
    }

    @TypeConverter
    fun jsonToData(json: String): MutableList<UserActivityData> {
        return adapter.fromJson(json) ?: mutableListOf()
    }
}