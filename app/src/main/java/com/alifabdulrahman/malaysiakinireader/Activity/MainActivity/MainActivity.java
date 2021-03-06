package com.alifabdulrahman.malaysiakinireader.Activity.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.NewsSectionActivity.MKSection;
import com.alifabdulrahman.malaysiakinireader.Helper.saver;
import com.alifabdulrahman.malaysiakinireader.R;
import com.alifabdulrahman.malaysiakinireader.Model.NewsSource;
import com.alifabdulrahman.malaysiakinireader.Storage.Substorage.currentArticle;
import com.alifabdulrahman.malaysiakinireader.Storage.Substorage.settings;


public class MainActivity extends AppCompatActivity implements Serializable {

    private int backButtonCount = 0;
    private ArrayList<NewsSource> newsSources = new ArrayList<>();
    private ArrayAdapter<NewsSource> adapter;
    private ListView lv;
    private AlertDialog.Builder dialog_builder;
    private AlertDialog.Builder startUp;
    private final sectionManager sectionManger = new sectionManager(this);
    private Intent intent = null;
    private saver saver;
    //private currentArticle currentArticle = new currentArticle(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.news_list);
        saver = new saver(MainActivity.this, this);
        Handler mainHandler = new Handler(Looper.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                newsSources.add(new NewsSource("MalaysiaKini", ""));
                newsSources.add(new NewsSource("Other", ""));
                newsSources.add(new NewsSource("Help", ""));
                newsSources.add(new NewsSource("About", ""));

                //newsSources.add(new NewsSource("The New York Times (in development)", "https://www.nytimes.com/"));
                //newsSources.add(new NewsSource("The Star (in development)", "https://www.thestar.com.my"));
                //https://www.thestar.com.my

                adapter = new ArrayAdapter<NewsSource>(MainActivity.this, android.R.layout.simple_list_item_1, newsSources) {
                    @Override
                    public View getView(int pos, View convertView, ViewGroup parent) {
                        View view = super.getView(pos, convertView, parent);

                        TextView tv = view.findViewById(android.R.id.text1);
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

                        LayoutParams params = view.getLayoutParams();
                        params.height = 125;
                        view.setLayoutParams(params);

                        return view;
                    }
                };

                newsSources = new ArrayList<>();
                dialog_builder = new AlertDialog.Builder(MainActivity.this);
                startUp = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Notice")
                        .setMessage("To read the full (paid) article, please log in on the first article you before starting the voice reading. Once it is logged in, you won't have to log in again.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                //Display disclaimer on first run.
                checkFirstRun(startUp);

                //loadLastArticle();

                lv.setAdapter(adapter);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        saver.saveSource(position);
                        intent = sectionManger.section(position);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });

            }

        };
        mainHandler.post(myRunnable);
    }

    @Override
    public void onBackPressed() {
        //Quit app
        if (backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

    public void checkFirstRun(AlertDialog.Builder startUp) {
        boolean firstRun = (new settings(this).checkFirstRun(startUp));
        if (firstRun)
            startUp.show();
        new settings(this).editFirstRun();
    }

    public void loadLastArticle(){
        currentArticle currentArticle = new currentArticle(this);

        if (currentArticle.loadReading()){
            Intent intent = new Intent(MainActivity.this, MKSection.class);
            startActivity(intent);
        }
    }
}
