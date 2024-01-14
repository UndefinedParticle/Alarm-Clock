package com.undefinedparticle.alarmclock.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface AlarmsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(alarmsModel: AlarmsModel)

    @Query("DELETE FROM ALARMS_TABLE")
    suspend fun deleteAllData()

    @Query("SELECT * FROM ALARMS_TABLE ORDER BY alarmId ASC")
    fun getAllData(): LiveData<List<AlarmsModel>>

}