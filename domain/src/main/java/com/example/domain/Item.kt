package com.example.domain

import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName


@Entity(tableName = "items")
data class Item (
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @SerialName("id")
    var id: Int?,
    @SerializedName("uid")
    @SerialName("uid")
    @ColumnInfo(name = "uid")
    var uid:String?,
    @SerializedName("title")
    @ColumnInfo(name = "title")
    @SerialName("title")
    var title: String?,
    @SerializedName("description")
    @ColumnInfo(name = "description")
    @SerialName("description")
    var description: String?,
    @SerializedName("priority")
    @ColumnInfo(name = "priority")
    @SerialName("priority")
    var priority: Int?,
    @SerializedName("type")
    @ColumnInfo(name = "type")
    @SerialName("type")
    var type: Int?,
    @SerializedName("count")
    @ColumnInfo(name = "count")
    @SerialName("count")
    var count: Int?,
    @SerializedName("frequency")
    @ColumnInfo(name = "frequency")
    @SerialName("frequency")
    var frequency:Int?,
    @SerializedName("color")
    @ColumnInfo(name = "color")
    @SerialName("color")
    var color: Int?,
    @SerializedName("date")
    @ColumnInfo(name = "date")
    @SerialName("date")
    var date: Long?,
    @SerializedName("done_dates")
    @ColumnInfo(name = "done_dates")
    @SerialName("done_dates")
    @TypeConverters(DateConverter::class)
    var done_dates: List<Long>?
)

