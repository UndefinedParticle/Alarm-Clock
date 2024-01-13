package com.undefinedparticle.alarmclock.database

class DbQuery {

    companion object {
        var DB_VERSION:Int = 9
        var DB_NAME = "AlarmClock_DB"
        var TABLE_NAME = "All_Alarms"
        var ALARM_ID = "ALARM_ID"
        var ALARM_TIME = "ALARM_TIME"
        var REMAINING_HOURS = "REMAINING_HOURS"
        var REMAINING_MINUTES = "REMAINING_MINUTES"
        var SELECTED_STATE = "SELECTED_STATE"
        var CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME (ALARM_ID INTEGER PRIMARY KEY, ALARM_TIME TEXT NOT NULL, REMAINING_HOURS TEXT NOT NULL, REMAINING_MINUTES TEXT NOT NULL, SELECTED_STATE INTEGER NOT NULL);"
        var DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        var DELETE_ITEM = "DELETE FROM items WHERE ALARM_ID = $ALARM_ID"
        var DELETE_ALL = "DELETE FROM $TABLE_NAME"
        var SELECT_ALL = "SELECT *FROM All_Alarms"
        //public static final String UPDATE_ITEM = "UPDATE All_Alarms SET IF EXISTS " + TABLE_NAME;
    }
}