package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

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
    private currentArticle currentArticle;
    private Activity activity;
    private Context context;
    private String url;
    private SwipeRefreshLayout pullToRefresh;
    private ArrayList<String> sentences;
    private com.example.myappname.TinyDB tinyDB;

    public Webview(Activity activity, Context context) {
        mWebView = activity.findViewById(R.id.webview);
        this.activity = activity;
        this.context = context;
        currentArticle = new currentArticle(context);
        url = currentArticle.loadData();
        tinyDB = new TinyDB(context);


        mWebView.getSettings().setJavaScriptEnabled(true);
        loadWebView();

        pullToRefresh = activity.findViewById(R.id.pullToRefresh2);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.loadUrl(url);
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    @SuppressLint("JavascriptInterface")
    public void loadWebView(){
        mWebView.addJavascriptInterface(new GetHTML(context), "Scrap");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading (WebView view, String url){
                return false;
            }

            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view, url);
                try {
                    Thread.sleep(timex);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }

                mWebView.loadUrl("javascript:window.Scrap.getHTML" +
                        "(document.getElementsByTagName('html')[0].outerHTML);");

                //String cookies = CookieManager.getInstance().getCookie(url);
                //System.out.println( "All the cookies in a string:" + cookies);
            }
        });

        mWebView.loadUrl(url);
    }


    public void reloadWebView() {

        try {
            Thread.sleep(timex);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
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

            ArrayList<String> tempList = new ArrayList<>();
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

                tinyDB = new TinyDB(context);
                tinyDB.putListString("MyContent", tempList);
            }

        }
    }
}
