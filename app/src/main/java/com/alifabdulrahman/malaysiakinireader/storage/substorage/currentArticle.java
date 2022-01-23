package com.alifabdulrahman.malaysiakinireader.storage.substorage;

import android.content.Context;

import com.alifabdulrahman.malaysiakinireader.model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.storage.storage;
import com.google.gson.Gson;

import java.util.ArrayList;

public class currentArticle extends storage {


    public currentArticle(Context context) {
        super(context);
    }

    public void saveText(ArrayList<String> text){
        tinyDB.putListString("MyContent", text);
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
    public ArrayList<String> loadText(){
        return tinyDB.getListString("MyCotent");
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

    public void saveLastArc(ArticleData articleData){
        Gson gson = new Gson();
        String json = gson.toJson(articleData);
        tinyDB.putString("lastArc", json);
    }

    public ArticleData loadLastArc(){
        Gson gson = new Gson();
        String json = tinyDB.getString("lastArc");
        ArticleData articleData = gson.fromJson(json, ArticleData.class);
        return articleData;
    }

    public String loadLastArticle(){
        return tinyDB.getString("lastURL");
    }


    public boolean startTSS(){return tinyDB.getBoolean("startTSS");}

    public void setTTS(boolean tss){tinyDB.putBoolean("startTSS", tss);}
}
