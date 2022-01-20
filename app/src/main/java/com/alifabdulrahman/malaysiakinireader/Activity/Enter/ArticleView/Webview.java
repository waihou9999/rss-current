package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import androidx.annotation.RequiresApi;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alifabdulrahman.malaysiakinireader.R;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.currentArticle;
import com.example.myappname.TinyDB;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Webview {
    private WebView mWebView;
    private int timex = 1000;
    private Activity activity;
    private Context context;
    private SwipeRefreshLayout pullToRefresh;
    private String url;
    private ArrayList<String>tempList;
    private TTS tts;
    private loader loader;


    public Webview(Activity activity, Context context, TTS tts) {
        tempList = new ArrayList<>();
        mWebView = activity.findViewById(R.id.webview);
        this.activity = activity;
        this.context = context;
        this.tts = tts;
        this.loader = new loader(activity, context);
        this.url = loader.getUrl();

        mWebView.getSettings().setJavaScriptEnabled(true);
        loadWebView(url);

        pullToRefresh = activity.findViewById(R.id.pullToRefresh2);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    reloadWebView();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    @SuppressLint("JavascriptInterface")
    public void loadWebView(String url){
        mWebView.addJavascriptInterface(new GetHTML(context), "Scrap");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading (WebView view, String url){
                return false;
            }

            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view, url);

                try {
                    scrap();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        mWebView.loadUrl(url);
        tts.init();
    }


    public void reloadWebView() throws InterruptedException {
        mWebView.loadUrl(url);

        try {
            Thread.sleep(timex);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        scrap();
    }

    public void scrap() throws InterruptedException {
        mWebView.loadUrl("javascript:window.Scrap.getHTML" +
                "(document.getElementsByTagName('html')[0].outerHTML);");
    }

    public class GetHTML {
        private Context ctx;
        private com.example.myappname.TinyDB tinyDB;


        public GetHTML(Context ctx) {
            this.ctx = ctx;
            tinyDB = new TinyDB(context);
        }

        @JavascriptInterface
        public void getHTML(String html) {
            Document doc = Jsoup.parse(html);

            tempList.clear();

            Elements classContents = doc.select("div[id $= full-content-container]");

            Elements contents = classContents.select("p, li");

            if (classContents == null || classContents.isEmpty()) {
                classContents = doc.select("div[id $= __next]");
                contents = classContents.select("p, li");
            }

            for (Element content : contents) {

                if (!(content.text().equals(""))) {

                    if (!tempList.contains(content.text()))
                        tempList.add(content.text());
                }
            }

            tts.setText(tempList);
        }
    }

    /*
    public class saveText extends AsyncTask<String, Void, ArrayList<String>> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            tinyDB.remove("MyContent");
            tinyDB.putBoolean("finished", false);
            System.out.println("plz saving");
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);

            tinyDB.putListString("MyContent", tempList);
            tinyDB.putBoolean("finished", true);
            System.out.println("plz done saving");
        }
    }

     */
}

