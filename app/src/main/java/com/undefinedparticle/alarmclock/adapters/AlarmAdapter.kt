package com.undefinedparticle.alarmclock.adapters

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.undefinedparticle.alarmclock.models.AlarmModel
import com.undefinedparticle.alarmclock.R
import com.undefinedparticle.alarmclock.activities.MainActivity
import com.undefinedparticle.alarmclock.database.AlarmsModel
import com.undefinedparticle.alarmclock.databinding.AlarmItemBinding

class AlarmAdapter(private val context: Context, private var alarmList: List<AlarmsModel>) :
    RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    private lateinit var alarmsModel: AlarmsModel
    private var remainingHours: Int = 0
    private var remainingMinutes: Int = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alarm_item, parent, false)
        return AlarmViewHolder(view)
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        alarmsModel = alarmList[position]

        holder.binding.model = alarmsModel
        holder.binding.alarmTime = alarmsModel.alarmTime
        holder.binding.selected = false
        holder.binding.deleting = false
        holder.binding.handler = holder.ClickHandler(context, holder, position)

        remainingHours = alarmsModel.remainingHours
        remainingMinutes = alarmsModel.remainingMinutes

        if (remainingHours.toInt() == 0) {
            holder.binding.remainingTime.text = "Alarm in ${kotlin.math.abs(remainingMinutes)} mins"
        } else {
            holder.binding.remainingTime.text =
                context.getString(
                    R.string.alarm_in_hours_minutes,
                    kotlin.math.abs(remainingHours).toString(),
                    kotlin.math.abs(remainingMinutes).toString()
                )
        }
    }

    fun setData(newList: List<AlarmsModel>) {
        alarmList = newList
        notifyDataSetChanged()
    }

    fun getAlarmData(): List<AlarmsModel>{
        return alarmList
    }

    fun updateItemsOnLongClick() {
        for (alarmModel in alarmList) {
            alarmModel.setDeleting(!alarmModel.getDeleting())
            alarmModel.setSelected(false)

            MainActivity.alarmViewModel.deleting.value = alarmModel.getDeleting()
        }
        notifyDataSetChanged()
    }


    inner class AlarmViewHolder(private var itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: AlarmItemBinding = AlarmItemBinding.bind(itemView)

        inner class ClickHandler(
            private val context: Context,
            private val holder: AlarmViewHolder,
            private val position: Int
        ) {
            // Add a long click listener to itemView
            fun onLongClick(view: View): Boolean {
                vibrate(context)
                /*holder.binding.deleting = holder.binding.deleting?.not() ?: false
                holder.binding.selected = false*/

                updateItemsOnLongClick()

                return true
            }

            fun onClick(view: View) {
                //holder.binding.selected = holder.binding.selected?.not() ?: false
                alarmList[position].setSelected(!alarmList[position].getSelected())
                notifyDataSetChanged()
                //Here I want to toggle "selected" variable in that particular alarmModel
            }


        }

        fun vibrate(context: Context) {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                // Vibrate with vibration effect for SDK version 26 and above
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        200,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                // Vibrate for devices below SDK version 26
                vibrator.vibrate(200)
            }
        }
    }


}