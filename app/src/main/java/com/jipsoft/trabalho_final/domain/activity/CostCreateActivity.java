package com.jipsoft.trabalho_final.domain.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.jipsoft.trabalho_final.R;
import com.jipsoft.trabalho_final.domain.dao.CostDAO;
import com.jipsoft.trabalho_final.domain.entity.Center;
import com.jipsoft.trabalho_final.domain.entity.Cost;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CostCreateActivity extends BaseActivity<CostDAO> implements BasicCreateActivityMethod {

    Center center;
    EditText editName;
    EditText editPrice;
    Cost cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost_create);

        initializeComponents();
        initializeDatabase(new CostDAO());
        loadData();
        getCenter();
    }

    @Override
    public void initializeComponents() {
        editName = findViewById(R.id.create_cost_name_edit_text);
        editPrice = findViewById(R.id.create_cost_price_edit);
    }

    @Override
    public void loadData() {
        int id = getIntent().getIntExtra("COST_ID", -1);
        if (id > 0) {
            cost = repository.findById(id);
            editName.setText(cost.getName());
            editPrice.setText(cost.getPrice().toString());
        }
    }

    @Override
    public void save(View view) {
        String name = editName.getText().toString();
        String price = editPrice.getText().toString();
        boolean hasName = name != null && !name.isEmpty();
        boolean hasPrice = price != null && !price.isEmpty();
        if (hasName && hasPrice) {
            if (cost == null) {
                create();
            } else {
                update();
            }
        }

        finish();
    }

    @Override
    public void create() {
        repository.create(new Cost(editName.getText().toString(), getTransformedPrice(), center));
        Toast.makeText(this, "Cost created!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void update() {
        cost.setName(editName.getText().toString());
        cost.setPrice(getTransformedPrice());
        repository.update(cost);
        Toast.makeText(this, "Cost updated!", Toast.LENGTH_SHORT).show();
    }

    private void getCenter() {
        int id = getIntent().getIntExtra("CENTER_ID", -1);
        if (id > 0) {
            center = repository.getRelationshipRepository().findById(id);
        }
    }

    private Double getTransformedPrice() {
        return Double.valueOf(editPrice.getText().toString());
    }
}