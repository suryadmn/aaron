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
        allContents = contentDao.getAllContents();
    }

    public void insert(Content content){
        new InsertContentAsynTask(contentDao).execute(content);
    }

    public void delete(Content content) {
        new DeleteContentAsynTask(contentDao).execute(content);
    }

    public void deleteAllContents() {
        new DeleteAllContentAsynTask(contentDao).execute();
    }

    public LiveData<List<Content>> getAllContents() {
        return allContents;
    }

    /**-----=====Get all data=====-----*/
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


    /**-----=====For delete item content=====-----*/
    private static class DeleteContentAsynTask extends AsyncTask<Content, Void, Void> {
        private ContentDao contentDao;

        private DeleteContentAsynTask(ContentDao contentDao) {
            this.contentDao = contentDao;
        }

        @Override
        protected Void doInBackground(Content... contents) {
            contentDao.delete(contents[0]);
            return null;
        }
    }

    /**-----=====For delete All item content=====-----*/
    private static class DeleteAllContentAsynTask extends AsyncTask<Void, Void, Void> {
        private ContentDao contentDao;

        private DeleteAllContentAsynTask(ContentDao contentDao) {
            this.contentDao = contentDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contentDao.deleteAllContents();
            return null;
        }
    }
}
