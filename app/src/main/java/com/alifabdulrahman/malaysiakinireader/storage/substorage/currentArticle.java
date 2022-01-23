package com.alifabdulrahman.malaysiakinireader.storage.substorage;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import com.alifabdulrahman.malaysiakinireader.storage.storage;

public class currentArticle extends storage {


    public currentArticle(Context context) {
        super(context);
    }

    public void saveReading(boolean wasReading){
        tinyDB.putBoolean("wasReading", wasReading);
    }

    public void saveIndex(int index){
        tinyDB.putInt("lastIndex", index);
    }

    public int loadIndex(){
        return tinyDB.getInt("index");
    }

    public boolean loadReading(){
        return tinyDB.getBoolean("wasReading");
    }

    public void saveData(String link) {
        tinyDB.putString("lastURL", link);
    }

    public void saveURL(String link) {
        tinyDB.putString("lastURL", link);
    }

    public String loadData(){
        return tinyDB.getString("lastURL");
    }

    public String loadNewsType(){
        return tinyDB.getString("lastNewsType");
    }

    public String loadNewsSectionURL(){
        return tinyDB.getString("lastURL");
    }

    public void saveNewsSectionURL(String newsSection){
        tinyDB.putString("lastURL", newsSection);
    }

    public String loadLastArticle(){
        return tinyDB.getString("lastURL");
    }


    public boolean startTSS(){return tinyDB.getBoolean("startTSS");}

    public void setTTS(boolean tss){tinyDB.putBoolean("startTSS", tss);}
}
