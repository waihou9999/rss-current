package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;

import com.alifabdulrahman.malaysiakinireader.Helper.TinyDB;
import com.alifabdulrahman.malaysiakinireader.model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.NewsSectionStorage.MKSectionStorage;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.NewsStorage;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.currentArticle;
import com.google.android.gms.common.util.ArrayUtils;

import java.util.ArrayList;

public class saver {
    private Activity activity;
    private Context context;
    private com.alifabdulrahman.malaysiakinireader.storage.substorage.currentArticle currentArticle;
    private NewsStorage newsStorage;
    private MKSectionStorage newsSectionStorage;
    private TinyDB tinyDB;

    public saver(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        currentArticle = new currentArticle(context);
        newsSectionStorage = new MKSectionStorage(context);
    }

    public void saveNewsType(ArrayList<ArticleData> articleDatas, String newsType) {
        newsStorage = new NewsStorage(context, newsType);
    }

    public void saveList(boolean wasReading, int index, String link){
        currentArticle.saveReading(wasReading);
        currentArticle.saveIndex(index);
        currentArticle.saveURL(link);
    }

    public void clearText(){
        currentArticle.clearText();
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
}