package com.alifabdulrahman.malaysiakinireader.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.sectionManager;
import com.alifabdulrahman.malaysiakinireader.model.NewsSectionData;
import  com.alifabdulrahman.malaysiakinireader.Helper.TinyDB;

import java.util.ArrayList;

public abstract class storage {
    protected TinyDB tinyDB;
    protected Context context;
    protected sectionManager sectionManager;

    public storage(Context context){
        this.context = context;
        tinyDB = new TinyDB(context);
        sectionManager = new sectionManager(context);
    }


    public void saveData() {

    }

}
