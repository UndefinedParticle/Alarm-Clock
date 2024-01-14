package com.undefinedparticle.alarmclock.activities

import android.app.AlertDialog
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.NumberPicker
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.undefinedparticle.alarmclock.R
import com.undefinedparticle.alarmclock.adapters.AlarmAdapter
import com.undefinedparticle.alarmclock.database.AlarmsModel
import com.undefinedparticle.alarmclock.database.DbHelper
import com.undefinedparticle.alarmclock.databinding.ActivityMainBinding
import com.undefinedparticle.alarmclock.models.AlarmModel
import com.undefinedparticle.alarmclock.models.AlarmViewModel
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var alarmAdapter: AlarmAdapter
    private lateinit var alarmList: List<AlarmsModel>
    private lateinit var selectedTime: String
    private lateinit var amPm: String
    private var currTime: Long = 0
    private var remainingH: Int = 0
    private var remainingM: Int = 0
    private var timeDifferenceMillis: Long = 0
    private lateinit var alarmModel: AlarmModel

    companion object {
        lateinit var db: DbHelper

        lateinit var alarmViewModel: AlarmViewModel
            private set
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currTime = System.currentTimeMillis()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        val uiMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (uiMode == Configuration.UI_MODE_NIGHT_YES) {
            // Dark mode
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        } else {
            // Light mode
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
            window.decorView.systemUiVisibility = 0 // Clear any existing flags
        }

        alarmViewModel = ViewModelProvider(this)[AlarmViewModel::class.java]

        db = DbHelper(this)
        alarmModel = AlarmModel()
        alarmList = ArrayList()
        //alarmList = db.getAllData()

        alarmAdapter = AlarmAdapter(this, alarmList)
        binding.alarmRecyclerView.adapter = alarmAdapter
        binding.handler = ClickHandler()

        binding.addButton.setOnClickListener {
            showTimePickerDialog()
        }

        observeLiveData()

    }

    private fun observeLiveData() {

        alarmViewModel.deleting.observe(this, Observer {

            binding.deleting = it

        })

        alarmViewModel.getAllAlarms.observe(this, Observer {

            alarmList = it
            alarmAdapter.setData(alarmList)

        })

    }

    inner class ClickHandler() {

        fun deleteAlarm(view: View) {

            val newList = alarmAdapter.getAlarmData()
            var tempList: List<AlarmsModel> = ArrayList()

            for (pos in newList.indices) {

                val model = newList[pos]
                if (model.getSelected()) {
                    //db.deleteData(pos)

                    //Log.d("deleteAlarm","pos: $pos");
                } else {
                    tempList += model
                }
            }

            //db.deleteAllData()
            alarmViewModel.deleteAll()

            for (pos in tempList.indices) {
                val model = tempList[pos]

                //db.insertData(pos,model.getAlarmTime(),model.getRemainingHours().toString(),model.getRemainingMinutes().toString(),if (model.getSelected()) 1 else 0)
                alarmViewModel.insertData(
                    AlarmsModel(
                        pos,
                        model.alarmTime,
                        model.remainingHours,
                        model.remainingMinutes,
                        model.selectedState
                    )
                )
            }

            //alarmList = db.getAllData()
            //alarmAdapter.setData(alarmList)

            alarmViewModel.deleting.value = false
        }

    }

    private fun showTimePickerDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.time_picker_dialog, null)
        val hourPicker: NumberPicker = dialogView.findViewById(R.id.hourPicker)
        val minutePicker: NumberPicker = dialogView.findViewById(R.id.minutePicker)
        val amPmPicker: NumberPicker = dialogView.findViewById(R.id.amPmPicker)


        // Set up Hour NumberPicker for 12-hour format
        hourPicker.minValue = 1
        hourPicker.maxValue = 12

        // Set up Minute NumberPicker
        minutePicker.minValue = 0
        minutePicker.maxValue = 59

        // Set up AM/PM NumberPicker
        amPmPicker.minValue = 0
        amPmPicker.maxValue = 1
        amPmPicker.displayedValues = arrayOf("AM", "PM")


        val builder = AlertDialog.Builder(this, R.style.CustomDialogTheme)
        builder.setView(dialogView)
            .setTitle("Select Time")
            .setPositiveButton("OK") { _, _ ->

                addAlarmData(hourPicker.value, minutePicker.value, amPmPicker.value)

            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

        val alertDialog = builder.create()

        // Apply custom styles to the buttons
        alertDialog.setOnShowListener {
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.customTextColor))
//            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundResource(R.drawable.positive_button_background)

            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.customTextColor))
//            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundResource(R.drawable.positive_button_background)
        }

        alertDialog.show()
    }

    private fun addAlarmData(hourVal: Int, minuteVal: Int, amPmVal: Int) {

        val currentCalendar = Calendar.getInstance()
        val selectedCalendar = Calendar.getInstance()

        if (amPmVal == 1) {
            //val diff = 12 - (hourPicker.value % 12);
            if (hourVal == 12) {
                selectedCalendar.set(Calendar.HOUR_OF_DAY, 12)
            } else {
                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourVal + 12)
            }

        } else {
            if (hourVal == 12) {
                selectedCalendar.set(Calendar.HOUR_OF_DAY, 0)
            } else {
                selectedCalendar.set(Calendar.HOUR_OF_DAY, hourVal)
            }
        }

        // Set the selected time in the calendar
        selectedCalendar.set(Calendar.MINUTE, minuteVal)

        // Handle the selected time
        selectedTime = "${hourVal}:${String.format("%02d", minuteVal)}"
        amPm = if (amPmVal == 0) "AM" else "PM"

        // Do something with the selected time
        val timeInMillis = selectedCalendar.timeInMillis;
        timeDifferenceMillis = selectedCalendar.timeInMillis - currentCalendar.timeInMillis
        val minutes = (timeDifferenceMillis / (1000 * 60)).toInt()

        // Calculate hours and remaining minutes
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        remainingH = hours
        remainingM = remainingMinutes

        Log.d(
            "timeInMillis",
            "timeInMillis: $timeInMillis remainingH: $remainingH remainingM: $remainingM"
        );

        val newAlarm = AlarmsModel()
        newAlarm.alarmTime = "$selectedTime $amPm"
        newAlarm.remainingHours = remainingH
        newAlarm.remainingMinutes = remainingM

        //db.insertData(alarmList.size+1,"$selectedTime $amPm",remainingH.toString(),remainingM.toString(),0)
        alarmViewModel.insertData(
            AlarmsModel(
                alarmList.size + 1,
                "$selectedTime $amPm",
                remainingH,
                remainingM,
                false
            )
        )
        // Set the properties of the new alarm as needed
        //alarmList += newAlarm
        //alarmAdapter.setData(alarmList)

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (alarmViewModel.deleting.value == true) {
            alarmViewModel.deleting.value = false

            alarmAdapter.updateItemsOnLongClick()
        } else {
            super.onBackPressed()
            // Keep the existing logic for other cases
        }
    }
}