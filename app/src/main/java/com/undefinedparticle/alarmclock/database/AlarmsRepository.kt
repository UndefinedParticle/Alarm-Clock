package com.undefinedparticle.alarmclock.database

import androidx.lifecycle.LiveData

class AlarmsRepository(private val alarmsDao: AlarmsDao) {

    val getAllAlarms: LiveData<List<AlarmsModel>> = alarmsDao.getAllData()
    suspend fun insertData(alarmsModel: AlarmsModel) {
        alarmsDao.insertData(alarmsModel)
    }

    suspend fun deleteAllData(){
        alarmsDao.deleteAllData()
    }

}