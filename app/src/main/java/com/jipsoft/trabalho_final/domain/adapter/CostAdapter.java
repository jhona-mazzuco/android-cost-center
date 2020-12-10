package com.jipsoft.trabalho_final.domain.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.jipsoft.trabalho_final.R;
import com.jipsoft.trabalho_final.domain.dao.CostDAO;
import com.jipsoft.trabalho_final.domain.entity.Center;
import com.jipsoft.trabalho_final.domain.entity.Cost;
import com.jipsoft.trabalho_final.domain.activity.CostCreateActivity;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CostAdapter extends BaseAdapter<Cost, CostDAO> implements BasicAdapterMethod {

    public CostAdapter(@NonNull Context context, List<Cost> data) {
        super(context, R.layout.center_item_layout, data, new CostDAO());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View layout = layoutInflater.inflate(R.layout.cost_item_layout, parent, false);
        initializeComponents(layout, position);
        return layout;
    }

    @Override
    public void initializeComponents(View layout, int position) {
        TextView viewName = layout.findViewById(R.id.cost_name);
        viewName.setText(getItem(position).getName());

        TextView viewPrice = layout.findViewById(R.id.cost_price);
        viewPrice.setText("R$ " + getItem(position).getPrice().toString());

        ImageButton editImageBtn = layout.findViewById(R.id.cost_edit_btn);
        editImageBtn.setOnClickListener(update(position));

        ImageButton removeImageBtn = layout.findViewById(R.id.cost_remove_btn);
        removeImageBtn.setOnClickListener(remove(position));
    }

    @Override
    public View.OnClickListener update(int position) {
        return v -> {
            Intent intent = new Intent(getContext(), CostCreateActivity.class);
            intent.putExtra("COST_ID", getItem(position).getId());
            ((Activity) getContext()).startActivityForResult(intent, 2);
        };
    }

    @Override
    public View.OnClickListener remove(int position) {
        return v -> {
            new AlertDialog.Builder(getContext())
                    .setMessage("Do you want to delete?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Cost cost = getItem(position);
                            repository.remove(cost.getId());
                            remove(cost);
                            notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        };
    }
}
