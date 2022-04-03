package com.alifabdulrahman.malaysiakinireader.Helper;

import android.app.Activity;
import android.content.Context;

import com.alifabdulrahman.malaysiakinireader.Model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.Storage.Substorage.NewsSectionStorage.MKSectionStorage;
import com.alifabdulrahman.malaysiakinireader.Storage.Substorage.newsStorage;
import com.alifabdulrahman.malaysiakinireader.Storage.Substorage.currentArticle;

import java.util.ArrayList;

public class loader {
    private Activity activity;
    private Context context;
    private currentArticle currentArticle;
    private newsStorage newsStorage;
    private MKSectionStorage MKSectionStorage;
    private ArrayList<ArticleData> articleDatas;

    public loader(Activity activity, Context context){
        this.activity = activity;
        this.context = context;
        currentArticle = new currentArticle(context);
        MKSectionStorage = new MKSectionStorage(context);
        String newsType = MKSectionStorage.getNewsSectionType();

        newsStorage = new newsStorage(context, newsType);
        newsStorage.loadData();
        articleDatas = newsStorage.loadArt1();

    }

    public ArrayList<ArticleData> getArticleDatas() {
        return articleDatas;
    }

    public String getNewsType(){ return MKSectionStorage.getNewsSectionType();}

    public int getIndex(){
        return currentArticle.loadIndex();
    }

    public boolean getTSS() {
        return currentArticle.startTSS();
    }

    public String getUrl(){
        return currentArticle.loadData();
    }

    public ArrayList<String>getText(){
        return currentArticle.loadText();
    }

    public ArticleData getLastArc() {
        return currentArticle.loadLastArc();
    }

    public int getReadIndex() {
        return currentArticle.getReadIndex();
    }

    public int getSource() {
        return currentArticle.getSource();
    }
}
