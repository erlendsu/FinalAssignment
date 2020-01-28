package com.auas.finalassignment.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tripTable")
data class Trip(

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "destination")
    var destination: String,

    @ColumnInfo(name = "airline")
    var airline: String,

    @ColumnInfo(name = "cost")
    var cost: String,

    @ColumnInfo(name = "extra")
    var extra: String,

    @ColumnInfo(name = "completed")
    var completed: Boolean = false,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

) : Parcelable