package com.undefinedparticle.alarmclock.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.undefinedparticle.alarmclock.models.AlarmModel

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DbQuery.DB_NAME, null, DbQuery.DB_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        // Create the table when the database is created
        db?.execSQL(DbQuery.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Implement database upgrade logic if needed
    }

    fun insertData(
        id: Int,
        alarmTime: String,
        remainingHours: String,
        remainingMinutes: String,
        selectedState: Int
    ): Long {
        val writableDb = writableDatabase

        val values = ContentValues()
        values.put(DbQuery.ALARM_ID, id)
        values.put(DbQuery.ALARM_TIME, alarmTime)
        values.put(DbQuery.REMAINING_HOURS, remainingHours)
        values.put(DbQuery.REMAINING_MINUTES, remainingMinutes)
        values.put(DbQuery.SELECTED_STATE, selectedState)

        return writableDb.insert(DbQuery.TABLE_NAME, null, values)
    }

    fun updateData(
        id: Int,
        alarmTime: String,
        remainingHours: String,
        remainingMinutes: String,
        selectedState: Int
    ): Int {

        val db = this.writableDatabase

        val values = ContentValues()
        //values.put(DbQuery.ALARM_ID,id)
        values.put(DbQuery.ALARM_TIME, alarmTime)
        values.put(DbQuery.REMAINING_HOURS, remainingHours)
        values.put(DbQuery.REMAINING_MINUTES, remainingMinutes)
        values.put(DbQuery.SELECTED_STATE, selectedState)

        val whereClause = "ALARM_ID = $id"

        return db.update(DbQuery.TABLE_NAME, values, whereClause, null)
    }

    /*fun deleteData(id: Int): Int {
        val writableDb = this.writableDatabase

        return writableDb.delete(DbQuery.TABLE_NAME, id.toString(), null)
    }*/

    fun deleteData(id: Int): Int {
        val writableDb = this.writableDatabase
        val whereClause = "${DbQuery.ALARM_ID} = ?"
        val whereArgs = arrayOf(id.toString())

        return writableDb.delete(DbQuery.TABLE_NAME, whereClause, whereArgs)
    }



    fun getData(id: Int): AlarmModel {
        val db = this.readableDatabase
        val whereClause = "ALARM_ID = $id"

        val cursor = db.query(
            DbQuery.TABLE_NAME,
            null,
            whereClause,
            null,
            null,
            null,
            null
        )

        return if (cursor != null && cursor.moveToFirst()) {
            val alarmIdColumnIndex = cursor.getColumnIndex(DbQuery.ALARM_ID)
            val alarmTimeColumnIndex = cursor.getColumnIndex(DbQuery.ALARM_TIME)
            val remainingHoursColumnIndex = cursor.getColumnIndex(DbQuery.REMAINING_HOURS)
            val remainingMinutesColumnIndex = cursor.getColumnIndex(DbQuery.REMAINING_MINUTES)
            val selectedStateColumnIndex = cursor.getColumnIndex(DbQuery.SELECTED_STATE)

            AlarmModel(
                cursor.getInt(alarmIdColumnIndex),
                cursor.getString(alarmTimeColumnIndex),
                cursor.getInt(remainingHoursColumnIndex),
                cursor.getInt(remainingMinutesColumnIndex),
                (cursor.getInt(selectedStateColumnIndex) == 0)
            )
        } else {
            AlarmModel()
        }
    }

    fun getAllData(): List<AlarmModel> {
        val alarmList: MutableList<AlarmModel> = mutableListOf()
        val db = this.readableDatabase

        val cursor = db.rawQuery(DbQuery.SELECT_ALL, null)

        cursor.use {
            // Ensure the cursor is not null and has at least one row
            if (it != null && it.moveToFirst()) {
                do {
                    val alarmIdColumnIndex = it.getColumnIndex(DbQuery.ALARM_ID)
                    val alarmTimeColumnIndex = it.getColumnIndex(DbQuery.ALARM_TIME)
                    val remainingHoursColumnIndex = it.getColumnIndex(DbQuery.REMAINING_HOURS)
                    val remainingMinutesColumnIndex = it.getColumnIndex(DbQuery.REMAINING_MINUTES)
                    val selectedStateColumnIndex = it.getColumnIndex(DbQuery.SELECTED_STATE)

                    // Retrieve data from the cursor
                    val alarmId = it.getInt(alarmIdColumnIndex)
                    val alarmTime = it.getString(alarmTimeColumnIndex)
                    val remainingHours = it.getInt(remainingHoursColumnIndex)
                    val remainingMinutes = it.getInt(remainingMinutesColumnIndex)
                    val selectedState = it.getInt(selectedStateColumnIndex) == 0

                    // Create an AlarmModel instance and add it to the list
                    val alarmModel = AlarmModel(
                        alarmId,
                        alarmTime,
                        remainingHours,
                        remainingMinutes,
                        selectedState
                    )
                    alarmList.add(alarmModel)

                } while (it.moveToNext()) // Move to the next row
            }
        }

        return alarmList
    }


}