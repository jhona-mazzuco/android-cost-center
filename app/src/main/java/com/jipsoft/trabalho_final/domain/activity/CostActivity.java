package com.jipsoft.trabalho_final.domain.activity;

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
import com.jipsoft.trabalho_final.domain.adapter.CostAdapter;
import com.jipsoft.trabalho_final.domain.dao.CostDAO;
import com.jipsoft.trabalho_final.domain.entity.Center;
import com.jipsoft.trabalho_final.domain.entity.Cost;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.P)
public class CostActivity extends BaseActivity<CostDAO> implements BasicActivityMethod {

    private Center center;
    private TextView totalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);

        initializeDatabase(new CostDAO());
        initializeComponents();
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
        registerObserver(adapter);
        listView.setAdapter(adapter);
        Double sum = Double.valueOf(0);
        for (Cost cost : costs) {
            sum += cost.getPrice();
        }

        totalView.setText("R$ " + sum.toString());
    }

    private void registerObserver(ListAdapter adapter) {
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                loadData();
            }
        });
    }

    private void getCenter() {
        int id = getIntent().getIntExtra("CENTER_ID", -1);
        if (id > 0) {
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
}