package com.example.android.popularmoviez.Data;

import com.example.android.popularmoviez.App;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by Suj🌠 on 30-11-2016.
 */
@SimpleSQLTable(table = "reviews", provider = App.MOVIE_PROVIDER)

public class ReviewsTable {
    @SimpleSQLColumn(value = "id",primary = true)
    public String r_id;

    @SimpleSQLColumn(value = "author",primary = true)
    public String author;

    @SimpleSQLColumn("url")
    public String url;

    @SimpleSQLColumn("content")
    public String Content;
}
