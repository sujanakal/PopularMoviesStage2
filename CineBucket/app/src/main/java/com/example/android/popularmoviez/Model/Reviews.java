package com.example.android.popularmoviez.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Suj🌠 on 14-08-2016.
 */
public class Reviews implements Parcelable{
    @SerializedName("id")
    private String id;

    @SerializedName("author")
    private String author;

    @SerializedName("url")
    private String url;

    @SerializedName("content")
    private String content;

    public Reviews(){}

    protected Reviews(Parcel in) {
        id = in.readString();
        author = in.readString();
        url = in.readString();
        content = in.readString();
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(url);
        dest.writeString(content);
    }


    public void setAuthor(String author){ this.author=author; }
    public String getAuthor(){ return author; }

    public void setContent(String content) { this.content = content; }
    public String getContent() { return content; }

    public void setUrl(String url){ this.url=url; }
    public String getUrl(){ return url; }

}
