package com.simpleMan.aaron;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Content.class}, version = 1)
public abstract class ContentDatabase extends RoomDatabase {

    private static ContentDatabase instance;

    public abstract ContentDao contentDao();

    public static synchronized ContentDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ContentDatabase.class, "content_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ContentDao contentDao;

        private PopulateDbAsyncTask(ContentDatabase db) {
            contentDao = db.contentDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contentDao.insert(new Content("Content arab", "Content bahasa"));
            return null;
        }
    }

}
