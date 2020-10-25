package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.VoiceInteractor;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity implements NewsList.OnItemclickListner {
     RecyclerView recyclerView;
     List<News> ne;
    NewsList adapter;
     SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycle);
        swipeRefreshLayout=findViewById(R.id.refresh);
        ne=new ArrayList<>();
        fatchdata();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ne.clear();
                fatchdata();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void fatchdata()
    {
        if(ne.isEmpty())
        {
            Toast.makeText(this,"DATA is empty", LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"DATA is full", LENGTH_LONG).show();
        }
        RequestQueue queue= Volley.newRequestQueue(this);
        String url="http://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=87878f798bbd43649938ccc05261c206\n";
        JsonObjectRequest json=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("articles");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject num = jsonArray.getJSONObject(i);
                        News a = new News();
                        Log.d("Welcome",num.getString("title").toString());
                        a.setTittle(num.getString("title").toString());
                        a.setImgUrl(num.getString("urlToImage").toString());
                        a.setAuthor(num.getString("author").toString());
                        a.setUrl(num.getString("url").toString());
                        a.setTime(num.getString("publishedAt").toString());
                        ne.add(a);
                    }
                    adapter = new NewsList(ne,getApplicationContext());
                   // Toast.makeText(this,"I reached to adapter", LENGTH_LONG).show();
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(MainActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(json);
    }
    @Override
    public void onItemClick(String ur) {
        String url=ur;
        CustomTabsIntent.Builder builder=new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent=builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}