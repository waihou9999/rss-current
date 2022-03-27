package com.alifabdulrahman.malaysiakinireader.Storage.Substorage;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;

import com.alifabdulrahman.malaysiakinireader.Storage.storage;

public class settings extends storage {

    private final String storageName = "settings";

    public settings(Context context) {
        super(context);
    }

    //Save the user's order settings
    public void saveSettings(String newsType, boolean orderLatest){
        tinyDB.putBoolean("order" + newsType, orderLatest);
    }

    //Load the user's order settings
    public boolean loadSettings(String newsType){
        return tinyDB.getBoolean("order" + newsType);
    }

    public boolean checkFirstRun(AlertDialog.Builder startUp) {
        SharedPreferences sp = context.getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        boolean firstRun = context.getSharedPreferences("settings", MODE_PRIVATE).getBoolean("firstRun", true);
        return firstRun;
    }

    public void editFirstRun() {
        SharedPreferences sp = context.getSharedPreferences("settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("firstRun", false);
        editor.apply();
    }
}
