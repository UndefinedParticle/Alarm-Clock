package com.undefinedparticle.alarmclock.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ALARMS_TABLE")
class AlarmsModel {

    @PrimaryKey @ColumnInfo(name = "alarmId")
    var alarmId: Int = 0

    @ColumnInfo(name = "alarmTime")
    var alarmTime: String = ""

    @ColumnInfo(name = "remainingHours")
    var remainingHours: Int = 0

    @ColumnInfo(name = "remainingMinutes")
    var remainingMinutes: Int = 0

    @ColumnInfo(name = "selectedState")
    var selectedState: Boolean = false


    private var deleting: Boolean = false
    private var selected: Boolean = false

    constructor()
    constructor(
        alarmId: Int,
        alarmTime: String,
        remainingHours: Int,
        remainingMinutes: Int,
        selectedState: Boolean
    ) {
        this.alarmId = alarmId
        this.alarmTime = alarmTime
        this.remainingHours = remainingHours
        this.remainingMinutes = remainingMinutes
        this.selectedState = selectedState
    }

    fun getDeleting(): Boolean{
        return deleting
    }
    fun setDeleting(deleting: Boolean){
        this.deleting = deleting
    }
    fun getSelected(): Boolean{
        return selected
    }
    fun setSelected(selected: Boolean){
        this.selected = selected
    }

}