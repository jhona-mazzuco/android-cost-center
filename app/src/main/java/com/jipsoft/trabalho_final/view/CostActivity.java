package com.jipsoft.trabalho_final.view;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.jipsoft.trabalho_final.R;
import com.jipsoft.trabalho_final.adapter.CostAdapter;
import com.jipsoft.trabalho_final.domain.dao.CostDAO;
import com.jipsoft.trabalho_final.domain.entity.Center;
import com.jipsoft.trabalho_final.domain.entity.Cost;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CostActivity extends BaseActivity<CostDAO> implements BasicActivityMethod, View.OnUnhandledKeyEventListener {

    private Center center;
    private TextView totalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);

        initializeComponents();
        initializeDatabase(new CostDAO());
        getCenter();
        loadData();
    }

    @Override
    public void initializeComponents() {
        createBtn = findViewById(R.id.cost_btn);
        listView = findViewById(R.id.cost_list);
        totalView = findViewById(R.id.cost_total_value);
    }

    @Override
    public void loadData() {
        List<Cost> costs = repository.find(center.getId());
        ListAdapter adapter = new CostAdapter(this, costs);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                loadData();
            }
        });
        listView.setAdapter(adapter);
        Double sum = Double.valueOf(0);
        for (Cost cost : costs) {
            sum += cost.getPrice();
        }

        totalView.setText("R$ " + sum.toString());
    }

    private void getCenter() {
        int id = getIntent().getIntExtra("CENTER_ID", -1);
        if (id > -1) {
            center = repository.getRelationshipRepository().findById(id);
        }
    }

    public void onClickNewBtn(View view) {
        Intent intent = new Intent(CostActivity.this, CostCreateActivity.class);
        intent.putExtra("CENTER_ID", center.getId());
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.loadData();
    }

    @Override
    public boolean onUnhandledKeyEvent(View v, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_REFRESH) {
            loadData();
        }

        return false;
    }
}