package com.example.android.popularmoviez.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suj🌠 on 25-03-2016.
 */
public class Movie implements Parcelable {

//    @SerializedName("poster_path")
    private String posterPath;
//    @SerializedName("adult")
    private boolean adult;
//    @SerializedName("overview")
    private String overview;
//    @SerializedName("release_date")
    private String releaseDate;
//    @SerializedName("genre_ids")
    private List<Integer> genreIds = new ArrayList<>();
//    @SerializedName("id")
    private Integer id;
//    @SerializedName("original_title")
    private String originalTitle;
//    @SerializedName("original_language")
    private String originalLanguage;
//    @SerializedName("title")
    private String title;
//    @SerializedName("backdrop_path")
    private String backdropPath;
//    @SerializedName("popularity")
    private Double popularity;
//    @SerializedName("vote_count")
    private Integer voteCount;
//    @SerializedName("video")
    private Boolean video;
//    @SerializedName("vote_average")
    private Double voteAverage;

    public Movie()
    {}

    protected Movie(Parcel in) {
        backdropPath = in.readString();
        id = in.readInt();
        originalTitle = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        posterPath = in.readString();
        popularity = in.readDouble();
        title = in.readString();
        voteAverage = in.readDouble();
        voteCount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(backdropPath);
        dest.writeInt(id);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(posterPath);
        dest.writeDouble(popularity);
        dest.writeString(title);
        dest.writeDouble(voteAverage);
        dest.writeInt(voteCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getVote_count() {return voteCount;}
    public void setVote_count(int vote_count) {
        this.voteCount = vote_count;
    }

    public Double getVote_average() {
        return voteAverage;
    }
    public void setVote_average(Double vote_average) {
        this.voteAverage = vote_average;
    }

    public String getBackdrop_path() {
        return backdropPath;
    }
    public void setBackdrop_path(String backdrop_path) {
        this.backdropPath = backdrop_path;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return originalTitle;
    }
    public void setOriginal_title(String original_title) {
        this.originalTitle = original_title;
    }

    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return releaseDate;
    }
    public void setRelease_date(String release_date) {
        this.releaseDate = release_date;
    }

    public String getPoster_path() {
        return posterPath;
    }
    public void setPoster_path(String poster_path) {
        this.posterPath = poster_path;
    }

    public double getPopularity() {
        return popularity;
    }
    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAdult() { return adult; }
    public void setAdult(boolean adult) { this.adult = adult; }

    public List<Integer> getGenreIds() { return genreIds; }
    public void setGenreIds(List<Integer> genreIds) { this.genreIds = genreIds; }

    public Boolean getVideo() { return video; }
    public void setVideo(Boolean video) { this.video = video; }

    public String getOriginalLanguage() { return originalLanguage; }
    public void setOriginalLanguage(String originalLanguage) { this.originalLanguage = originalLanguage; }
}
