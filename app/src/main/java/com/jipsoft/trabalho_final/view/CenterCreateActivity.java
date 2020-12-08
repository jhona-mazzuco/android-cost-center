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
import com.jipsoft.trabalho_final.domain.dao.UserDAO;
import com.jipsoft.trabalho_final.domain.entity.Center;
import com.jipsoft.trabalho_final.domain.entity.User;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CenterCreateActivity extends AppCompatActivity {

    SQLiteDatabase database;
    User loggedIn;
    EditText editName;
    Center center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_create);

        initializeComponents();
        initializeDatabase();
        initializeCenter();
        getLoggedIn();
    }

    private void initializeComponents() {
        editName = findViewById(R.id.create_center_name_edit_text);
    }

    private void initializeDatabase() {
        database = DBConnection.getInstace();
    }

    private void initializeCenter() {
        int id = getIntent().getIntExtra("CENTER_ID", -1);
        if (id > 0) {
            center = CenterDAO.findById(id);
            editName.setText(center.getName());
        }
    }

    private void getLoggedIn() {
        int id = getIntent().getIntExtra("USER_ID", -1);
        if (id > 0) {
            loggedIn = UserDAO.find(id);
        }
    }

    private void create() {
        CenterDAO.create(new Center(editName.getText().toString(), loggedIn));
    }

    private void update() {
        center.setName(editName.getText().toString());
        CenterDAO.update(center);
    }

    public void save(View view) {
        String name = editName.getText().toString();
        if (name != null && !name.isEmpty()) {
            if (center == null) {
                create();
            } else {
                update();
            }
        }

        finish();
    }
}