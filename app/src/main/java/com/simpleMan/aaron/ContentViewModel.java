package com.simpleMan.aaron;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ContentViewModel extends AndroidViewModel {
    private ContentRepository repository;
    private LiveData<List<Content>> allContent;

    public ContentViewModel(@NonNull Application application) {
        super(application);

        repository = new ContentRepository(application);
        allContent = repository.getAllContents();
    }

    public void insert(Content content){
        repository.insert(content);
    }

    public void delete(Content content) {
        repository.delete(content);
    }

    public void deleteAllContents() {
        repository.deleteAllContents();

    }

    public LiveData<List<Content>> getAllContent() {
        return allContent;
    }
}
