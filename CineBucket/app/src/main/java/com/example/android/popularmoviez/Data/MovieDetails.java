package com.example.android.popularmoviez.Data;

import com.example.android.popularmoviez.App;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by Suj🌠 on 30-11-2016.
 */

@SimpleSQLTable(table = "movies", provider = App.MOVIE_PROVIDER)

public class MovieDetails {
    @SimpleSQLColumn("movie_id")
    public int movie_id;

    @SimpleSQLColumn("poster_path")
    public String posterPath;

    @SimpleSQLColumn("adult")
    public boolean adult;

    @SimpleSQLColumn("overview")
    public String overview;

    @SimpleSQLColumn("release_date")
    public String releaseDate;

    @SimpleSQLColumn(value = "id", primary = true)
    public int _id;

    @SimpleSQLColumn("original_title")
    public String originalTitle;

    @SimpleSQLColumn("original_language")
    public String originalLanguage;

    @SimpleSQLColumn("title")
    public String Title;

    @SimpleSQLColumn("backdrop_path")
    public String backdropPath;

    @SimpleSQLColumn("popularity")
    public Double popularity;

    @SimpleSQLColumn("vote_count")
    public Integer voteCount;

    @SimpleSQLColumn("vote_average")
    public Double voteAverage;
}
