package com.jipsoft.trabalho_final;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jipsoft.trabalho_final.core.DBConnection;
import com.jipsoft.trabalho_final.domain.dao.UserDAO;
import com.jipsoft.trabalho_final.domain.entity.User;

public class CenterActivity extends AppCompatActivity {

    SQLiteDatabase database;
    User loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);

        initializeDatabase();
        getLoggedIn();
    }

    private void initializeDatabase() {
        database = DBConnection.getInstace();
    }

    @Override
    protected void onDestroy() {
        if (this.database != null) {
            this.database.close();
        }

        super.onDestroy();
    }

    private void getLoggedIn() {
        int id = getIntent().getIntExtra("USER_ID", -1);
        if (id > 0) {
            loggedIn = UserDAO.find(id);
        } else {
            Toast.makeText(getApplicationContext(), "Usuário não encontrado!", Toast.LENGTH_SHORT).show();
        }
    }
}