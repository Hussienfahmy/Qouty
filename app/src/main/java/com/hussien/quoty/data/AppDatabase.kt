package com.hussien.quoty.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hussien.quoty.models.Quote

@Database(
    exportSchema = false,
    version = 1,
    entities = [Quote::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract val quotesDao: QuotesDao
}
