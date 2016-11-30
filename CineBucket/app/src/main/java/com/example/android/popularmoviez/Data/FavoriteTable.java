package com.example.android.popularmoviez.Data;

import com.example.android.popularmoviez.App;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;

/**
 * Created by Suj🌠 on 30-11-2016.
 */
@SimpleSQLTable(table = "favorite", provider = App.MOVIE_PROVIDER)

public class FavoriteTable {
    @SimpleSQLColumn(value = "id", primary = true)
    public String f_id;
}
