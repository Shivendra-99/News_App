package com.example.newsapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.LENGTH_LONG;

public class NewsList extends RecyclerView.Adapter<NewsList.NewsViewHolder> {
    List<News> data;
    LayoutInflater inflater;
    private OnItemclickListner mListner;

    public interface OnItemclickListner {
        public void onItemClick(String url);
    }
    public NewsList(List<News> ne, Context con) {
        this.data = ne;
        this.inflater = LayoutInflater.from(con);
    }
    public void setOnItemClickListener(OnItemclickListner listener) {
        mListner = listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new NewsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.t1.setText(data.get(position).getTittle());
        holder.aut.setText(data.get(position).getAuthor());
        holder.tim.setText(data.get(position).getTime());
        Picasso.get().load(data.get(position).getImgUrl()).into(holder.image);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView t1, aut, tim;
        ImageView image;
        RelativeLayout relativeLayout;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.text);
            aut = itemView.findViewById(R.id.author);
            tim = itemView.findViewById(R.id.time);
            image = itemView.findViewById(R.id.img);
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListner!=null)
                    {
                        int position=getAdapterPosition();
                        String url=data.get(position).getUrl();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            mListner.onItemClick(url);
                        }
                    }
                }
            });
        }
    }
}
