package com.hussien.qouty.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hussien.qouty.models.Quote

@Database(
    exportSchema = false,
    version = 1,
    entities = [Quote::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract val quotesDao: QuotesDao
}
