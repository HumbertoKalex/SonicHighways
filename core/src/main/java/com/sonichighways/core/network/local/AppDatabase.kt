package com.sonichighways.core.network.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sonichighways.core.network.local.model.SongDao
import com.sonichighways.core.network.local.model.SongEntity

@Database(entities = [SongEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao
}