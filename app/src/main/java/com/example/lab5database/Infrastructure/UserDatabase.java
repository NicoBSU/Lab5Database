package com.example.lab5database.Infrastructure;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.lab5database.Entities.UserRecord;

@Database(entities = {UserRecord.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase{
    public abstract IUserDao userDao();

    //singleton
    private static UserDatabase dbInstance;

    public synchronized static UserDatabase getInstance(Context context) {
        if (dbInstance == null)
            dbInstance = createDbInstance(context);
        return dbInstance;
    }

    //create instance of sqlite database
    private static UserDatabase createDbInstance(final Context context) {
        return Room.databaseBuilder(context, UserDatabase.class, "user-database")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        AsyncTask.execute(() -> {
                            getInstance(context)
                                    .userDao()
                                    .insertAll(UserRecord.seedData());
                        });
                    }
                })
                .build();
    }
}
