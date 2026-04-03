package com.sonichighways.core.network.local.model

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "recent_songs")
data class SongEntity(
    @PrimaryKey val id: Long,
    val collectionId: Long,
    val title: String,
    val artist: String,
    val album: String,
    val imageUrl: String,
    val previewUrl: String,
    val durationMs: Long,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface SongDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSong(song: SongEntity)

    @Query("SELECT * FROM recent_songs ORDER BY timestamp DESC LIMIT 10")
    fun getRecentSongs(): Flow<List<SongEntity>>
}