package com.alifabdulrahman.malaysiakinireader.Storage;

import android.content.Context;

import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.sectionManager;

public abstract class storage {
    protected TinyDB tinyDB;
    protected Context context;
    protected sectionManager sectionManager;

    public storage(Context context){
        this.context = context;
        tinyDB = new TinyDB(context);
        sectionManager = new sectionManager(context);
    }

    public void saveData() {}
}
