package com.undefinedparticle.alarmclock.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.undefinedparticle.alarmclock.database.AlarmsRepository
import com.undefinedparticle.alarmclock.database.AlarmsRoomDatabase
import com.undefinedparticle.alarmclock.database.AlarmsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmViewModel(application: Application) : AndroidViewModel(application) {

    var deleting: MutableLiveData<Boolean> = MutableLiveData(false)
    var getAllAlarms: LiveData<List<AlarmsModel>>
    var repository: AlarmsRepository

    init {

        val alarmsDao = AlarmsRoomDatabase.getDatabase(application).alarmsDao()
        repository = AlarmsRepository(alarmsDao)
        getAllAlarms = repository.getAllAlarms

    }

    fun insertData(alarmsModel: AlarmsModel) = viewModelScope.launch(Dispatchers.IO) {

        repository.insertData(alarmsModel)

    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {

        repository.deleteAllData()

    }

}