package com.jipsoft.trabalho_final.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.jipsoft.trabalho_final.R;
import com.jipsoft.trabalho_final.adapter.CenterAdapter;
import com.jipsoft.trabalho_final.domain.dao.CenterDAO;
import com.jipsoft.trabalho_final.domain.dao.UserDAO;
import com.jipsoft.trabalho_final.domain.entity.Center;
import com.jipsoft.trabalho_final.domain.entity.User;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CenterActivity extends BaseActivity<CenterDAO> implements BasicActivityMethod {

    private User loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);

        initializeComponents();
        initializeDatabase(new CenterDAO());
        getLoggedIn();
        loadData();
    }

    @Override
    public void initializeComponents() {
        createBtn = findViewById(R.id.center_btn);
        listView = findViewById(R.id.center_list);
    }

    @Override
    public void loadData() {
        List<Center> centers = repository.find(loggedIn.getId());
        ListAdapter adapter = new CenterAdapter(this, centers);
        listView.setAdapter(adapter);
    }

    private void getLoggedIn() {
        int id = getIntent().getIntExtra("USER_ID", -1);
        if (id > 0) {
            loggedIn = UserDAO.find(id);
        }
    }

    public void onClickNewBtn(View view) {
        Intent intent = new Intent(CenterActivity.this, CenterCreateActivity.class);
        intent.putExtra("USER_ID", loggedIn.getId());
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.loadData();
    }
}