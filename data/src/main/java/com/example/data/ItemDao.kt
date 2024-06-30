package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.domain.Item
import kotlinx.coroutines.flow.Flow


@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItems(items: List<Item>)

    @Update
    suspend fun update(item: Item)

    @Query("SELECT * FROM items")
    fun getAll(): Flow<List<Item>>

    @Query("DELETE FROM items")
    suspend fun deleteAll()

}