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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
public class MainActivity extends AppCompatActivity implements NewsList.OnItemclickListner, AdapterView.OnItemSelectedListener {
     RecyclerView recyclerView;
     List<News> ne;
    NewsList adapter;
    String url;
    ArrayList<String> spin;
    Spinner spinner;
     SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recycle);
        swipeRefreshLayout=findViewById(R.id.refresh);
        spinner=findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        spin=new ArrayList<>();
        spin.add("Breaking-News");
        spin.add("Technology");
        spin.add("Entertainment");
        spin.add("Sports");
        spin.add("Science");
        ArrayAdapter a=new ArrayAdapter(this, android.R.layout.simple_spinner_item,spin);
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(a);
        ne=new ArrayList<>();
    //    fatchdata();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!ne.isEmpty()) {
                    ne.clear();
                    fatchdata();
                    adapter.notifyDataSetChanged();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void fatchdata()
    {
        RequestQueue queue= Volley.newRequestQueue(this);
       //  url="https://gnews.io/api/v4/top-headlines?token=e23fdb89958221d5d545593dd936681c&lang=en&country=in&max20&topic=technology";
        JsonObjectRequest json=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("articles");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject num = jsonArray.getJSONObject(i);
                        News a = new News();
                      //  Log.d("Welcome",num.getString("title").toString());
                        a.setTittle(num.getString("title").toString());
                        a.setImgUrl(num.getString("image").toString());
                        a.setUrl(num.getString("url").toString());
                        a.setTime(num.getString("publishedAt").toString());
                        JSONObject j=num.getJSONObject("source");
                        a.setAuthor(j.getString("name").toString());
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
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(),spin.get(position), LENGTH_LONG).show();
        if(position==0)
        {
            url="https://gnews.io/api/v4/top-headlines?token=e23fdb89958221d5d545593dd936681c&lang=en&country=in&max20&topic=breaking-news";
            if(!ne.isEmpty())
            {
                ne.clear();
            }
            fatchdata();
        }
        else if(position==1)
        {
            url="https://gnews.io/api/v4/top-headlines?token=e23fdb89958221d5d545593dd936681c&lang=en&country=in&max20&topic=technology";
            if(!ne.isEmpty())
            {
                ne.clear();
            }
            fatchdata();
        }
        else if(position==2)
        {
            url="https://gnews.io/api/v4/top-headlines?token=e23fdb89958221d5d545593dd936681c&lang=en&country=in&max20&topic=entertainment";
            if(!ne.isEmpty())
            {
                ne.clear();
            }
            fatchdata();
        }
        else if(position==3)
        {
            url="https://gnews.io/api/v4/top-headlines?token=e23fdb89958221d5d545593dd936681c&lang=en&country=in&max20&topic=sports";
            if(!ne.isEmpty())
            {
                ne.clear();
            }
            fatchdata();
        }
        else if(position==4)
        {
            url="https://gnews.io/api/v4/top-headlines?token=e23fdb89958221d5d545593dd936681c&lang=en&country=in&max20&topic=science";
            if(!ne.isEmpty())
            {
                ne.clear();
            }
            fatchdata();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        url="https://gnews.io/api/v4/top-headlines?token=e23fdb89958221d5d545593dd936681c&lang=en&country=in&max20&topic=breaking-news";
        fatchdata();
    }
}