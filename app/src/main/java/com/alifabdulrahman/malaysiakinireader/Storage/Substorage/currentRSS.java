package com.alifabdulrahman.malaysiakinireader.Storage.Substorage;

import android.content.Context;

import com.alifabdulrahman.malaysiakinireader.Storage.storage;

public class currentRSS extends storage {
    private final String storageName = "currentRSS";

    public currentRSS(Context context) {
        super(context);
    }

    public void saveData(String link) {
        tinyDB.putString("allLink", link);
    }

    public String loadData(){
        return tinyDB.getString("allLink");
    }

    public void clearData(){
        tinyDB.clear();
    }
}
