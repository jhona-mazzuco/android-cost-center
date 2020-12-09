package com.jipsoft.trabalho_final.view;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.jipsoft.trabalho_final.R;
import com.jipsoft.trabalho_final.core.DBConnection;
import com.jipsoft.trabalho_final.domain.dao.CenterDAO;
import com.jipsoft.trabalho_final.domain.dao.CostDAO;
import com.jipsoft.trabalho_final.domain.dao.UserDAO;
import com.jipsoft.trabalho_final.domain.entity.Center;
import com.jipsoft.trabalho_final.domain.entity.User;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    EditText loginEditText;
    EditText passwordEditText;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
        initializeDatabase();
    }

    private void initializeComponents() {
        loginEditText = findViewById(R.id.login);
        passwordEditText = findViewById(R.id.password);
    }

    @Override
    protected void onDestroy() {
        if (this.database != null) {
            this.database.close();
        }

        super.onDestroy();
    }

    private void initializeDatabase() {
        try {
            database = this.openOrCreateDatabase(DBConnection.DB_NAME, MODE_PRIVATE, null);
            new DBConnection(database);

            UserDAO.createTable();
            new CenterDAO().createTable();
            new CostDAO().createTable();
        } catch (Exception e) {
            finish();
        }
    }

    public void onClickSign(View view) {
        String login = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        User user = UserDAO.findByLogin(login);
        if (user != null) signIn(user, password); else signUp(login, password);
    }

    private void signIn(User user, String password) {
        if (!user.getPassword().equals(password)) {
            return;
        }

        goToCenter(user.getId());
    }

    private void signUp(String login, String password) {
        Long id = UserDAO.create(new User(login, password));
        goToCenter(id);
    }

    private void goToCenter(Number id) {
        Intent intent = new Intent(MainActivity.this, CenterActivity.class);
        intent.putExtra("USER_ID", id);
        startActivity(intent);
    }
}