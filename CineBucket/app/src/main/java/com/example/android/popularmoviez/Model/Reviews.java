package com.example.android.popularmoviez.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Suj🌠 on 14-08-2016.
 */
public class Reviews {
    @SerializedName("id")
    private String id;

    @SerializedName("author")
    private String author;

    @SerializedName("url")
    private String url;

    @SerializedName("content")
    private String content;

    public Reviews(){}

    public void setAuthor(String author){ this.author=author; }
    public String getAuthor(){ return author; }

    public void setContent(String content) { this.content = content; }
    public String getContent() { return content; }

    public void setUrl(String url){ this.url=url; }
    public String getUrl(){ return url; }
}
