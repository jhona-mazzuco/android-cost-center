package com.jipsoft.trabalho_final.view;

import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.jipsoft.trabalho_final.core.DBConnection;

public class BaseActivity<T> extends AppCompatActivity {


    protected T repository;
    protected SQLiteDatabase database;
    protected ListView listView;
    protected Button createBtn;

    protected void initializeDatabase(T dao) {
        database = DBConnection.getInstace();
        this.repository = dao;
    }

}
