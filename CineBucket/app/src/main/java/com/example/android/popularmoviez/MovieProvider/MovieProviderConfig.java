package com.example.android.popularmoviez.MovieProvider;

import com.example.android.popularmoviez.App;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/**
 * Created by Suj🌠 on 30-11-2016.
 */
@SimpleSQLConfig(
        name = App.MOVIE_PROVIDER,
        authority = "com.example.android.popularmoviez",
        database = "Cine.db",
        version = 1)

public class MovieProviderConfig implements ProviderConfig{

    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}
