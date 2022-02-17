package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;

import com.alifabdulrahman.malaysiakinireader.model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.NewsSectionStorage.MKSectionStorage;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.NewsStorage;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.currentArticle;

import java.util.ArrayList;

public class loader {
    private Activity activity;
    private Context context;
    private currentArticle currentArticle;
    private NewsStorage newsStorage;
    private MKSectionStorage MKSectionStorage;
    private ArrayList<ArticleData> articleDatas;

    public loader(Activity activity, Context context){
        this.activity = activity;
        this.context = context;
        currentArticle = new currentArticle(context);
        MKSectionStorage = new MKSectionStorage(context);
        String newsType = MKSectionStorage.getNewsSectionType();

        newsStorage = new NewsStorage(context, newsType);
        newsStorage.loadData();
        articleDatas = newsStorage.loadArt1();

    }

    public ArrayList<ArticleData> getArticleDatas() {
        System.out.println("whatdataload" + articleDatas);
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
        return currentArticle.loadLastArticle();
    }

    public ArrayList<String>getText(){
        return currentArticle.loadText();
    }

    public ArticleData getLastArc() {
        return currentArticle.loadLastArc();
    }
}
