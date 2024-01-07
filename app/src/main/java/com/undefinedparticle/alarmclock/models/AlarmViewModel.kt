package com.undefinedparticle.alarmclock.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class AlarmViewModel(application: Application) : AndroidViewModel(application) {

    public var deleting: MutableLiveData<Boolean> = MutableLiveData(false)

}