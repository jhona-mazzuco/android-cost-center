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

    public void createTables() {
        String userSql = new StringBuilder()
                .append(" CREATE TABLE IF NOT EXISTS users ( ")
                .append("     id INTEGER PRIMARY KEY,        ")
                .append("     login VARCHAR(50),             ")
                .append("     password VARCHAR(25)           ")
                .append(" )                                  ")
                .toString();
        database.execSQL(userSql);

        String centerSql = new StringBuilder()
                .append(" CREATE TABLE IF NOT EXISTS centers (         ")
                .append("    id INTEGER PRIMARY KEY,                   ")
                .append("    name VARCHAR(50),                         ")
                .append("    user_id INTEGER,                          ")
                .append("    FOREIGN KEY(user_id) REFERENCES users(id) ")
                .append(" )                                            ")
                .toString();
        database.execSQL(centerSql);

        String costSql = new StringBuilder()
                .append(" CREATE TABLE IF NOT EXISTS costs (               ")
                .append("    id INTEGER PRIMARY KEY,                       ")
                .append("    name VARCHAR(50),                             ")
                .append("    price double,                                 ")
                .append("    center_id INTEGER,                            ")
                .append("    FOREIGN KEY(center_id) REFERENCES centers(id) ")
                .append(" )                                                ")
                .toString();
        database.execSQL(costSql);
    }
}
