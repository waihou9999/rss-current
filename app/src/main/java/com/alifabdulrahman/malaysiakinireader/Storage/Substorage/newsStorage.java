package com.alifabdulrahman.malaysiakinireader.Storage.Substorage;

import android.content.Context;

import com.alifabdulrahman.malaysiakinireader.Model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.Storage.storage;
import com.alifabdulrahman.malaysiakinireader.Storage.Substorage.NewsSectionStorage.MKSectionStorage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.alifabdulrahman.malaysiakinireader.Model.Sorter.sorting;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class newsStorage extends storage {
    private final String storageName = "NewsStorage";
    private ArrayList<ArticleData>articleDatas;
    private ArrayList<ArticleData>articleDatas2;
    private String newsType;
    private String newsType2 = "a";
    private settings settings = new settings(context);
    private MKSectionStorage newsSectionStorage = new MKSectionStorage(context);
    private boolean orderLatest;
    private sorting sorting;

    public newsStorage(Context context, String newsType) {
        super(context);
        this.newsType = newsType;
    }

    public boolean isOrderLatest() {
        return orderLatest;
    }

    //Load data of articles
    public void loadData() {
        Gson gson = new Gson();
        Gson xson = new Gson();
        String json = tinyDB.getString(newsType);
        String yson = tinyDB.getString(newsType2);
        Type dataType = new TypeToken<ArrayList<ArticleData>>() {
        }.getType();
        articleDatas = gson.fromJson(json, dataType);
        articleDatas2 = xson.fromJson(yson, dataType);

        orderLatest = settings.loadSettings(newsType);

        if (!orderLatest && (articleDatas != null)) {
            Collections.sort(articleDatas, new Comparator<ArticleData>() {
                @Override
                public int compare(ArticleData o1, ArticleData o2) {
                    return o1.getPublishDate().compareTo(o2.getPublishDate());
                }
            });
        }

        if (articleDatas == null) {
            articleDatas = new ArrayList<>();
        }

        if (articleDatas2 == null) {
            articleDatas2 = new ArrayList<>();
        }
    }

    //Save data of articles retrieved
    public void saveData(ArrayList<ArticleData>articleDatas) {
        Gson gson = new Gson();
        Gson xson = new Gson();
        String json;
        String yson;

        //save in latest order
        ArrayList<ArticleData> toSaveInOrder = new ArrayList<>(articleDatas);
        ArrayList<ArticleData> toSaveInOrder2;
        if (articleDatas2 != null) {
            toSaveInOrder2 = new ArrayList<>(articleDatas2);
            yson = xson.toJson(toSaveInOrder2);
            tinyDB.putString(newsType2, yson);
        }

        sorting = new sorting(context);
        toSaveInOrder = sorting.sortByLatest(toSaveInOrder);
        json = gson.toJson(toSaveInOrder);
        tinyDB.putString(newsType, json);
    }

    public ArrayList<ArticleData>loadArt1(){
        return articleDatas;
    }

    public ArrayList<ArticleData>loadArt2(){
        return articleDatas2;
    }
}
