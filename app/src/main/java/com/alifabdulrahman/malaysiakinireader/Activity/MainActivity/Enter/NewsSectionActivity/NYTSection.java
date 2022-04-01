package com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.NewsSectionActivity;


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

import androidx.appcompat.app.AppCompatActivity;

import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleList.MalaysiaKini.MKListingActivity;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.MainActivity;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.sectionManager;
import com.alifabdulrahman.malaysiakinireader.R;
import com.alifabdulrahman.malaysiakinireader.Model.NewsSectionData;
import com.alifabdulrahman.malaysiakinireader.Model.NewsSource;
import com.alifabdulrahman.malaysiakinireader.Storage.Substorage.NewsSectionStorage.NYTSectionStorage;
import com.alifabdulrahman.malaysiakinireader.Storage.Substorage.currentArticle;

import java.io.Serializable;
import java.util.ArrayList;

//basically duplicate from MKSection (not yet developed)

public class NYTSection extends AppCompatActivity implements Serializable {
    private ArrayList<NewsSectionData> newsSection = new ArrayList<>();
    private ArrayList<NewsSectionData> newsSection2 = new ArrayList<>();
    //private NewsSectionAdapter newsSectionAdapter;
    private NewsSource NYT;
    private NYTSectionStorage NYTSectionStorage;
    private com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.sectionManager sectionManager = new sectionManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_section);
        //newsSectionAdapter = new NewsSectionAdapter(this, newsSection2);
        NYT = new NewsSource("News York Times", "https://www.nytimes.com/");
        initializeDefaults();

        NYTSectionStorage = new NYTSectionStorage(this);

        newsSection2 = NYTSectionStorage.loadData();
        NYTSectionStorage.loadReading();


        if (newsSection2 != null) {
            newsSection.addAll(newsSection2);
        }

        loadLastArticle();

        setupListView();
    }

    @Override
    protected void onStop(){
        super.onStop();
        NYTSectionStorage.saveData();
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
                Intent toNewsListing = new Intent(NYTSection.this, MKListingActivity.class);
                startActivity(toNewsListing);
                NYTSectionStorage.saveReading(url, newsType, true);
                NYTSectionStorage.saveData();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    //ArrayList<CharSequence> arrayListCollection = new ArrayList<>();
    //ArrayAdapter<NewsSectionData> adapter;
    EditText txt1;
    EditText txt2;

    public void initializeDefaults() {
        newsSection.add(new NewsSectionData(NYT,"World", "https://rss.nytimes.com/services/xml/rss/nyt/World.xml"));
        newsSection.add(new NewsSectionData(NYT,"Sports", "https://rss.nytimes.com/services/xml/rss/nyt/Sports.xml"));
        newsSection.add(new NewsSectionData(NYT, "Business", "https://rss.nytimes.com/services/xml/rss/nyt/Business.xml"));
    }

    public void toAdd(View view){
        //System.out.println(newsSection.size());
        AlertDialog.Builder addNewsSourceAlert = new AlertDialog.Builder(this);
        final EditText editLabel = new EditText(NYTSection.this);
        final EditText editURL = new EditText(NYTSection.this);
        editLabel.setHint("Label");
        editLabel.setFilters(new InputFilter[] {new InputFilter.LengthFilter(64)});
        editLabel.setSingleLine();
        editLabel.setPadding(60, 30, 60, 30);
        editURL.setPadding(60, 30, 60, 30);
        editURL.setHint("RSS Feed URL");

        addNewsSourceAlert.setTitle("Add RSS Feed");
        addNewsSourceAlert.setView(editLabel);
        addNewsSourceAlert.setView(editURL);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(editLabel);
        layout.addView(editURL);
        addNewsSourceAlert.setView(layout);

        addNewsSourceAlert.setPositiveButton("Add", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        txt1 = editLabel;
                        txt2 = editURL;
                        collectInput(newsSection2);
                    }
                });

        addNewsSourceAlert.setNegativeButton("Cancel", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        addNewsSourceAlert.show();
    }

    public void toRemove(View view){
        // setup the alert builder
        if (!newsSection2.isEmpty()) {
            AlertDialog.Builder removeNewsSourceAlert = new AlertDialog.Builder(this);
            removeNewsSourceAlert.setTitle("Remove RSS Feed");// add a checkbox list
            final String[] newsSection2List = new String[newsSection2.size()];
            final ArrayList<Integer> selectedItems = new ArrayList<>();
            for (int i = 0; i < newsSection2.size(); i++) {
                newsSection2List[i] = newsSection2.get(i).getSectionName();
            }
            boolean[] checkedItems = null;
            removeNewsSourceAlert.setMultiChoiceItems(newsSection2List, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    if (isChecked) {
                        selectedItems.add(which);
                    } else if (selectedItems.contains(which)) {
                        selectedItems.remove(which);
                    }
                }
            });
            removeNewsSourceAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    removeInput(newsSection2, selectedItems);
                }
            });
            removeNewsSourceAlert.setNegativeButton("Cancel", null);
            AlertDialog dialog = removeNewsSourceAlert.create();
            dialog.show();
        }

    }

    public void collectInput(ArrayList<NewsSectionData> newsSection2) {
        String getInput1 = txt1.getText().toString();
        String getInput2 = txt2.getText().toString();
        if (!(getInput1 == null || getInput1.trim().equals("") || getInput2 == null || getInput2.trim().equals(""))) {
            newsSection2.add(new NewsSectionData(NYT, getInput1, getInput2));
            newsSection.add(new NewsSectionData(NYT, getInput1, getInput2));
        }
        //newsSection.add(new NewsSectionData("MalaysiaKini News (English)", "https://www.malaysiakini.com/rss/en/news.rss"));
        //System.out.println(newsSection2 == null);
        NYTSectionStorage.saveData();
        setupListView();
        //System.out.println(newsSection2.size());
    }

    public void removeInput(ArrayList<NewsSectionData> newsSection2, ArrayList<Integer> selectedItems) {
        for (int i = selectedItems.size()-1; i >= 0; i--) {
            int x = selectedItems.get(i);
            int y = selectedItems.get(i) + 9;
            newsSection2.remove(x);
            newsSection.remove(y);
        }
        NYTSectionStorage.saveData();
        setupListView();
        //System.out.println(newsSection2.size());
    }

    public void loadLastArticle(){
        currentArticle currentArticle = new currentArticle(this);

        if (currentArticle.loadReading()){
            Intent intent = new Intent(NYTSection.this, MKListingActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onBackPressed(){
        finish();
        super.onBackPressed();
        NYTSectionStorage.setReading(false);
        //Intent toMain = sectionManager.section(4);
        //Intent toMain = new Intent(this, MainActivity.class);
        //startActivity(toMain);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
