package com.undefinedparticle.alarmclock.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


// Annotates class to be a Room Database with a table (entity) of the AlarmsTable class
@Database(entities = [AlarmsModel::class], version = 1, exportSchema = false)
public abstract class AlarmsRoomDatabase: RoomDatabase() {

    abstract fun alarmsDao(): AlarmsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: AlarmsRoomDatabase? = null

        fun getDatabase(context: Context): AlarmsRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlarmsRoomDatabase::class.java,
                    "ALARMS_DATABASE"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}