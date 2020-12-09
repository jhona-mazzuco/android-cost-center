package com.jipsoft.trabalho_final.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import com.jipsoft.trabalho_final.R;
import com.jipsoft.trabalho_final.domain.dao.CenterDAO;
import com.jipsoft.trabalho_final.domain.dao.UserDAO;
import com.jipsoft.trabalho_final.domain.entity.Center;
import com.jipsoft.trabalho_final.domain.entity.User;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CenterCreateActivity extends BaseActivity<CenterDAO> implements BasicCreateActivityMethod {

    User loggedIn;
    EditText editName;
    Center center;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_create);

        initializeComponents();
        initializeDatabase(new CenterDAO());
        loadData();
        getLoggedIn();
    }

    @Override
    public void initializeComponents() {
        editName = findViewById(R.id.create_cost_name_edit_text);
    }

    @Override
    public void loadData() {
        int id = getIntent().getIntExtra("CENTER_ID", -1);
        if (id > 0) {
            center = repository.findById(id);
            editName.setText(center.getName());
        }
    }

    @Override
    public void create() {
        repository.create(new Center(editName.getText().toString(), loggedIn));
    }

    @Override
    public void update() {
        center.setName(editName.getText().toString());
        repository.update(center);
    }

    @Override
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

    private void getLoggedIn() {
        int id = getIntent().getIntExtra("USER_ID", -1);
        if (id > 0) {
            loggedIn = UserDAO.find(id);
        }
    }
}