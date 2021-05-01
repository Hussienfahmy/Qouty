package com.hussien.qouty.data;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(exportSchema = false, version = 1, entities = {Quote.class})
public abstract class Database extends RoomDatabase {

    private static Database instance;
    private static final Object LOCK = new Object();

    public abstract QuotesDao quotesDao();

    public static Database getInstance(Context context){
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class,
                            "quotes_database")
                            .createFromAsset("databases/quotes.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }//end if
            }//end synchronized
        }//end if
        return instance;
    }//end getInstance()
}//end class