package me.vavra.sportresults.storage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SportResultEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sportResultDao(): SportResultDao
}