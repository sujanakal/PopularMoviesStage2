package com.example.android.popularmoviez.Data;

import com.example.android.popularmoviez.App;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by Suj🌠 on 30-11-2016.
 */
@SimpleSQLTable(table = "reviews", provider = App.MOVIE_PROVIDER)

public class MovieReviews {
    @SimpleSQLColumn(value = "id",primary = true)
    public int r_id;

    @SimpleSQLColumn("movie_id")
    public int movie_id;

    @SimpleSQLColumn("review_id")
    public String review_id;

    @SimpleSQLColumn("author")
    public String author;

    @SimpleSQLColumn("url")
    public String url;

    @SimpleSQLColumn("content")
    public String Content;
}
