package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements JsonTask.JsonTaskListener {
    private WebView myWebView;
    public void showExternalWebPage(){
        // TODO: Add your code for showing external web page here
        myWebView.loadUrl("file:///android_res/layout/activity_main.xml");
    }

    public void showInternalWebPage(){
        // TODO: Add your code for showing internal web page here

        //myWebView.loadUrl("file:///android_asset/about.html");
        Intent second = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(second);

    }
    private RecyclerView view;
    private Gson gson;
    private final String JSON_URL = "https://mobprog.webug.se/json-api?login=a23jesja";

    ArrayList<Mountain> items = new ArrayList<>();
    ArrayList<RecyclerViewItem> recyclerItems = new ArrayList<>();
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myWebView = findViewById(R.id.min_webview);
        myWebView.setWebViewClient(new WebViewClient()); // Do not open in Chrome!
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        gson = new Gson();
        //items.add(new Mountain("Jeppe"));
        //items.add(new Mountain("Peppe"));
        //items.add(new Mountain("Yeppe"));
        for(int i=0; i<items.size(); i++){
            Log.d("Unique ID", items.get(i).toString()+"Välkommen");
            recyclerItems.add(new RecyclerViewItem(items.get(i).toString()));
        }
        adapter = new RecyclerViewAdapter(this, recyclerItems, new RecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(RecyclerViewItem item) {
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        view = findViewById(R.id.recycler_view);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);

        new JsonTask(this).execute(JSON_URL);
    }
    @Override
    public void onPostExecute(String json) {
        Log.d("===>", json);
        Type type = new TypeToken<List<Mountain>>() {}.getType();
        List<Mountain> listOfMountains = gson.fromJson(json, type);
        items.clear();
        items.addAll(listOfMountains);
        for(int i=0; i<items.size(); i++){
            Log.d("===>1111", items.get(i).toString()+"Välkommen");
            recyclerItems.add(new RecyclerViewItem(items.get(i).toString()));
        }
        adapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_external_web) {
            showExternalWebPage();
            myWebView.setVisibility(View. GONE);
            view.setVisibility(View.VISIBLE);
            Log.d("==>","Will display external web page");
            return true;
        }

        if (id == R.id.action_internal_web) {
            showInternalWebPage();
            myWebView.setVisibility(View. VISIBLE);
            view.setVisibility(View.GONE);
            Log.d("==>","Will display internal web page");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}