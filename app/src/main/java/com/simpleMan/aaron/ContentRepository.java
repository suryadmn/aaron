package com.simpleMan.aaron;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ContentRepository {
    private ContentDao contentDao;
    private LiveData<List<Content>> allContents;

    public ContentRepository(Application application){
        ContentDatabase database = ContentDatabase.getInstance(application);
        contentDao = database.contentDao();
        allContents = contentDao.getAllNotes();
    }

    public void insert(Content content){
        new InsertContentAsynTask(contentDao).execute(content);
    }

    public LiveData<List<Content>> getAllContents() {
        return allContents;
    }

    private static class InsertContentAsynTask extends AsyncTask<Content, Void, Void> {
        private ContentDao contentDao;

        private InsertContentAsynTask(ContentDao contentDao) {
            this.contentDao = contentDao;
        }

        @Override
        protected Void doInBackground(Content... contents) {
            contentDao.insert(contents[0]);
            return null;
        }
    }
}
