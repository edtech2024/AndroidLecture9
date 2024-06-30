package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.domain.DateConverter
import com.example.domain.Item


@Database(entities = [Item::class], version = 2 )
@TypeConverters(DateConverter::class)
abstract class ItemDatabase: RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {

        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: ItemDatabase? = null

        fun getDatabase(context: Context): ItemDatabase {

            if (INSTANCE != null) {
                return INSTANCE!!
            }

            if (INSTANCE == null) {
                synchronized(this) {
                    // Using an in-memory database because the information stored here disappears when the process is killed.
                    val instance = Room.inMemoryDatabaseBuilder(
                        context.applicationContext,
                        ItemDatabase::class.java)
                        .build()
                    INSTANCE = instance
                }
            }
            return INSTANCE!!
        }
    }

}
