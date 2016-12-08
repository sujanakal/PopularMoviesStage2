package com.example.android.popularmoviez.Data;

import com.example.android.popularmoviez.App;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by Suj🌠 on 30-11-2016.
 */
@SimpleSQLTable(table = "trailers", provider = App.MOVIE_PROVIDER)

public class MovieTrailers {
    @SimpleSQLColumn(value = "id",primary = true)
    public int t_id;

    @SimpleSQLColumn("movie_id")
    public int movie_id;

    @SimpleSQLColumn("trailer_id")
    public String trailer_id;

    @SimpleSQLColumn("iso_3166_1")
    public String iso_3166_1;

    @SimpleSQLColumn("iso_639_1")
    public String iso_639_1;

    @SimpleSQLColumn("key")
    public String key;

    @SimpleSQLColumn("name")
    public String Name;

    @SimpleSQLColumn("site")
    public String site;

    @SimpleSQLColumn("size")
    public int Size;

    @SimpleSQLColumn("type")
    public String type;
}
