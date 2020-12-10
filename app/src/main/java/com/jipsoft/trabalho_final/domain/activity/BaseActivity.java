package com.jipsoft.trabalho_final.domain.activity;

import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity<T> extends AppCompatActivity {


    protected T repository;
    protected ListView listView;
    protected Button createBtn;

    protected void initializeDatabase(T dao) {
        this.repository = dao;
    }

}
