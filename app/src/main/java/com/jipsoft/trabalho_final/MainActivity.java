package com.jipsoft.trabalho_final;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jipsoft.trabalho_final.core.DBConnection;
import com.jipsoft.trabalho_final.domain.dao.CenterDAO;
import com.jipsoft.trabalho_final.domain.dao.CostDAO;
import com.jipsoft.trabalho_final.domain.dao.UserDAO;
import com.jipsoft.trabalho_final.domain.entity.User;

public class MainActivity extends AppCompatActivity {

    EditText loginEditText;
    EditText passwordEditText;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginEditText = findViewById(R.id.login);
        passwordEditText = findViewById(R.id.password);

        initializeDatabase();
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
            DBConnection connection = new DBConnection(database);

            UserDAO.createTable();
            CenterDAO.createTable();
            CostDAO.createTable();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Erro ao conectar no banco de dados!", Toast.LENGTH_SHORT);
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
            Toast.makeText(getApplicationContext(), "Senha inválida!", Toast.LENGTH_SHORT).show();
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