package com.alifabdulrahman.malaysiakinireader.storage.substorage.NewsSectionStorage;

import android.content.Context;

import com.alifabdulrahman.malaysiakinireader.model.NewsSectionData;
import com.alifabdulrahman.malaysiakinireader.storage.storage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NYTSectionStorage extends storage{
    private final String storageName = "NYTSectionStorage";
    private ArrayList<NewsSectionData> newsSection2;
    private final String newsSectionType = "newsSectionType";

    public NYTSectionStorage(Context context) {
        super(context);
    }

    public ArrayList<NewsSectionData> loadData() {
        Gson hson = new Gson();
        String kson = tinyDB.getString(newsSectionType);
        Type dataType = new TypeToken<ArrayList<NewsSectionData>>() {}.getType();
        newsSection2 = hson.fromJson(kson, dataType);

        if (newsSection2 == null) {
            return newsSection2 = new ArrayList<>();
        }
        return newsSection2;
    }

    @Override
    public void saveData() {
        Gson hson = new Gson();
        String kson = hson.toJson(newsSection2);
        tinyDB.putString(newsSectionType, kson);
    }

    public void saveReading(String url, String newsType, boolean wasReading){
        tinyDB.putString("sectionURL", url);
        tinyDB.putString("sectionType", newsType);
        tinyDB.putBoolean("wasReading", wasReading);
    }

    public void setReading(boolean wasReading){
        tinyDB.putBoolean ("wasReading", wasReading);
    }

    public boolean loadReading(){
        return tinyDB.getBoolean("wasReading");
    }

    public String getSectionURL(){
        return tinyDB.getString("sectionURL");

    }

    public String getNewsSectionType(){
        return tinyDB.getString("sectionType");
    }
}
