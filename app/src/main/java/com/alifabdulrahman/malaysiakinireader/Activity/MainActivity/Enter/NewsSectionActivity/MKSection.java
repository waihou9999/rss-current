package com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.NewsSectionActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.TextView;

import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleList.MalaysiaKini.MKListingActivity;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.MainActivity;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.sectionManager;
import com.alifabdulrahman.malaysiakinireader.R;
import com.alifabdulrahman.malaysiakinireader.Model.NewsSectionData;
import com.alifabdulrahman.malaysiakinireader.Model.NewsSource;
import com.alifabdulrahman.malaysiakinireader.Storage.Substorage.currentArticle;
import com.alifabdulrahman.malaysiakinireader.Storage.Substorage.NewsSectionStorage.MKSectionStorage;

import java.io.Serializable;
import java.util.ArrayList;

public class MKSection extends AppCompatActivity implements Serializable {
    private ArrayList<NewsSectionData> newsSection = new ArrayList<>();
    private ArrayList<NewsSectionData> newsSection2 = new ArrayList<>();
    //private NewsSectionAdapter newsSectionAdapter;
    private MKSectionStorage newsSectionStorage;
    private NewsSource MK;
    private sectionManager sectionManager = new sectionManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mk_section);
        //newsSectionAdapter = new NewsSectionAdapter(this, newsSection2);
        MK = new NewsSource("MalaysiaKini", "https://malaysiakini.com");
        initializeDefaults();

        newsSectionStorage = new MKSectionStorage(this);

        newsSection2 = newsSectionStorage.loadData();
        newsSectionStorage.loadReading();


        if (newsSection2 != null) {
            newsSection.addAll(newsSection2);
        }

        //loadLastArticle();
        setupListView();
    }

    @Override
    protected void onStop(){
        super.onStop();
        newsSectionStorage.saveData();
    }

    @Override
    public void onResume(){
        super.onResume();
        setupListView();

    }

    private void setupListView() {
        final ArrayAdapter<NewsSectionData> adapter = new ArrayAdapter<NewsSectionData>(this, android.R.layout.simple_list_item_1, newsSection){
            @Override
            public View getView(int pos, View convertView, ViewGroup parent){
                View view = super.getView(pos, convertView, parent);

                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

                ViewGroup.LayoutParams params = view.getLayoutParams();
                //params.height = 200;
                view.setLayoutParams(params);

                return view;
            }
        };

        ListView listView = findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = adapter.getItem(position).getSectionLink();
                String newsType = adapter.getItem(position).getSectionName();
                Intent toNewsListing = new Intent(MKSection.this, MKListingActivity.class);
                startActivity(toNewsListing);
                newsSectionStorage.saveReading(url, newsType, true);
                newsSectionStorage.saveData();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    //ArrayList<CharSequence> arrayListCollection = new ArrayList<>();
    //ArrayAdapter<NewsSectionData> adapter;
    EditText txt1;
    EditText txt2;

    public void initializeDefaults() {
        newsSection.add(new NewsSectionData(MK,"News (English)", "https://www.malaysiakini.com/rss/en/news.rss"));
        newsSection.add(new NewsSectionData(MK,"Opinions (English)", "https://www.malaysiakini.com/rss/en/columns.rss"));
        newsSection.add(new NewsSectionData(MK, "Letters (English)", "https://www.malaysiakini.com/rss/en/letters.rss"));
        newsSection.add(new NewsSectionData(MK, "News (Bahasa Malaysia)", "https://www.malaysiakini.com/rss/my/news.rss"));
        newsSection.add(new NewsSectionData(MK,"Opinions (Bahasa Malaysia)", "https://www.malaysiakini.com/rss/my/columns.rss"));
        newsSection.add(new NewsSectionData(MK,"Letters (Bahasa Malaysia)", "https://www.malaysiakini.com/rss/my/letters.rss"));
        newsSection.add(new NewsSectionData(MK,"News (Chinese)", "https://www.malaysiakini.com/rss/zh/news.rss"));
        newsSection.add(new NewsSectionData(MK,"Opinions (Chinese)", "https://www.malaysiakini.com/rss/zh/columns.rss"));
        newsSection.add(new NewsSectionData(MK,"Letters (Chinese)", "https://www.malaysiakini.com/rss/zh/letters.rss"));
    }

    public void loadLastArticle(){
        currentArticle currentArticle = new currentArticle(this);

        if (currentArticle.loadReading()){
            Intent intent = new Intent(MKSection.this, MKListingActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed(){
        finish();
        super.onBackPressed();
        newsSectionStorage.setReading(false);
        //Intent toMain = sectionManager.section(4);
        //Intent toMain = new Intent(this, MainActivity.class);
        //startActivity(toMain);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
