package com.example.newsapp;

public class News {
    private String tittle;
   private String author;
   private String url;
   private String time;
   private  String imgUrl;
    News(){}
    public News(String t, String aut, String ur, String img, String tim)
    {
        this.tittle=t;
        this.author=aut;
        this.url=ur;
        this.imgUrl=img;
        this.time=tim;
    }
    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
