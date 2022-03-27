package com.alifabdulrahman.malaysiakinireader.Storage.Substorage;

import android.content.Context;

import com.alifabdulrahman.malaysiakinireader.Model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.Storage.storage;
import com.google.gson.Gson;

import java.util.ArrayList;

public class currentArticle extends storage {


    public currentArticle(Context context) {
        super(context);
    }

    public void saveText(ArrayList<String> text){
        tinyDB.putListString("MyContent", text);
    }

    public ArrayList<String> loadText(){
        return tinyDB.getListString("MyContent");
    }

    public void clearText(){
        tinyDB.remove("MyContent");
    }

    public void saveReading(boolean wasReading){
        tinyDB.putBoolean("wasReading", wasReading);
    }

    public void saveIndex(int index){
        tinyDB.putInt("lastIndex", index);
    }

    public int loadIndex(){
        return tinyDB.getInt("lastIndex");
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

    public void saveNewsType(String newsType){
        tinyDB.putString("lastNewsType", newsType);
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

    public void setReadIndex(int readIndex){
        tinyDB.putInt("readIndex", readIndex);
    }

    public int getReadIndex(){
        return tinyDB.getInt("readIndex");
    }

    public boolean startTSS(){return tinyDB.getBoolean("startTSS");}

    public void setTTS(boolean tss){
        tinyDB.putBoolean("startTSS", tss);
    }
}
