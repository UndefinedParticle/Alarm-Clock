package com.undefinedparticle.alarmclock.models

class AlarmModel {
    private var alarmId:Int = 0
    private var alarmTime: String = ""
    private var remainingHours: Int = 0
    private var remainingMinutes: Int = 0
    private var remainingTime: Int = 0
    private var selectedState: Boolean = false
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

    fun getAlarmId():Int{
        return alarmId
    }
    fun setAlarmId(alarmId:Int){
        this.alarmId = alarmId
    }
    fun getRemaingTime(): Int{
        return remainingTime
    }

    fun setRemainingTime(remainingTime: Int){
        this.remainingTime = remainingTime
    }

    fun getSelectedState():Boolean{
        return selectedState
    }

    fun setSelectedState(selectedState: Boolean){
        this.selectedState = selectedState
    }

    // Getter for alarmTime
    fun getAlarmTime(): String {
        return alarmTime
    }

    // Setter for alarmTime
    fun setAlarmTime(time: String) {
        alarmTime = time
    }

    // Getter for remainingTime
    fun getRemainingHours(): Int {
        return remainingHours
    }

    // Setter for remainingTime
    fun setRemainingMinutes(time: Int) {
        remainingMinutes = time
    }

    fun getRemainingMinutes(): Int {
        return remainingMinutes
    }

    // Setter for remainingTime
    fun setRemainingHours(time: Int) {
        remainingHours = time
    }
}
