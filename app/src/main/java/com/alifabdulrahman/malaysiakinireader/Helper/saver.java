package com.alifabdulrahman.malaysiakinireader.Helper;

import android.app.Activity;
import android.content.Context;

import com.alifabdulrahman.malaysiakinireader.Model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.Storage.Substorage.NewsSectionStorage.MKSectionStorage;
import com.alifabdulrahman.malaysiakinireader.Storage.Substorage.newsStorage;
import com.alifabdulrahman.malaysiakinireader.Storage.Substorage.currentArticle;

import java.util.ArrayList;

public class saver {
    private Activity activity;
    private Context context;
    private com.alifabdulrahman.malaysiakinireader.Storage.Substorage.currentArticle currentArticle;
    private com.alifabdulrahman.malaysiakinireader.Storage.Substorage.newsStorage newsStorage;
    private MKSectionStorage newsSectionStorage;
    private TinyDB tinyDB;

    public saver(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        currentArticle = new currentArticle(context);
        newsSectionStorage = new MKSectionStorage(context);
    }

    public void saveNewsType(ArrayList<ArticleData> articleDatas, String newsType) {
        newsStorage = new newsStorage(context, newsType);
    }

    public void saveList(boolean wasReading, int index, String link){
        currentArticle.saveReading(wasReading);
        currentArticle.saveIndex(index);
        currentArticle.saveURL(link);
    }

    public void noLastArt(){
        currentArticle.saveReading(false);
    }

    public void setURL(String url){
        currentArticle.saveData(url);
    }

    public void saveText(ArrayList<String>text){
        currentArticle.saveText(text);
    }

    public void saveIndex(int index) {
        currentArticle.saveIndex(index);
    }

    public void saveArc(ArticleData articleData) {
        currentArticle.saveLastArc(articleData);
    }

    public void setTSS(boolean tts) {
        currentArticle.setTTS(tts);
    }

    public void saveReadIndex(int readIndex) {
        currentArticle.setReadIndex(readIndex);
    }
}