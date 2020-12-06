package com.jipsoft.trabalho_final.core;

import android.database.sqlite.SQLiteDatabase;

public class DBConnection {

    public final static String DB_NAME = "cost_center";
    private static SQLiteDatabase database;

    public DBConnection(SQLiteDatabase database) {
        this.database = database;
    }

    public static SQLiteDatabase getInstace() {
        if (database != null) {
            return database;
        }

        return null;
    }
}
