package com.example.android.popularmoviez.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Suj🌠 on 14-08-2016.
 */
public class Trailers implements Parcelable{
    @SerializedName("id")
    private String id;

    @SerializedName("iso_3166_1")
    private String iso_3166_1;

    @SerializedName("iso_639_1")
    private String iso_639_1;

    @SerializedName("key")
    private String key;

    @SerializedName("name")
    private String name;

    @SerializedName("site")
    private String site;

    @SerializedName("size")
    private int size;

    @SerializedName("type")
    private String type;

    public Trailers(){}

    protected Trailers(Parcel in){

        id = in.readString();
        /*iso_3166_1 = in.readString();
        iso_639_1 = in.readString();*/
        key = in.readString();
        name = in.readString();
        site = in.readString();
        size = in.readInt();
        type = in.readString();
    }

    public static final Creator<Trailers> CREATOR = new Creator<Trailers>() {
        @Override
        public Trailers createFromParcel(Parcel in) {
            return new Trailers(in);
        }

        @Override
        public Trailers[] newArray(int size) {
            return new Trailers[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        /*dest.writeString(iso_3166_1);
        dest.writeString(iso_639_1);*/
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(site);
        dest.writeInt(size);
        dest.writeString(type);
    }



    public void setId(String id){ this.id=id; }
    public String getId() { return id; }

    public void setIso_3166_1(String iso_3166_1){ this.iso_3166_1=iso_3166_1; }
    public String getIso_3166_1() { return iso_3166_1; }

    public void setIso_639_1(String iso_639_1){ this.iso_639_1=iso_639_1; }
    public String getIso_639_1() { return iso_639_1; }

    public void setKey(String key){ this.key=key; }
    public String getKey() { return key; }

    public void setName(String name){ this.name=name; }
    public String getName() { return name; }

    public void setSize(int size) { this.size = size; }
    public int getSize() { return size; }

    public void setSite(String site) { this.site = site; }
    public String getSite() { return site; }

    public void setType(String type) { this.type = type; }
    public String getType() { return type; }
}
