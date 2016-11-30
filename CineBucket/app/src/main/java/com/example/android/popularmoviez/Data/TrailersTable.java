package com.example.android.popularmoviez.Data;

import com.example.android.popularmoviez.App;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by Suj🌠 on 30-11-2016.
 */
@SimpleSQLTable(table = "trailers", provider = App.MOVIE_PROVIDER)

public class TrailersTable {
    @SimpleSQLColumn(value = "id",primary = true)
    public String t_id;

    @SimpleSQLColumn("iso_3166_1")
    public String iso_3166_1;

    @SimpleSQLColumn("iso_639_1")
    public String iso_639_1;

    @SimpleSQLColumn(value = "key",primary = true)
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
