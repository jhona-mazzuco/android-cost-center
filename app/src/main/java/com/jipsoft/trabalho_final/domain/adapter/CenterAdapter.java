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
import com.jipsoft.trabalho_final.domain.dao.CenterDAO;
import com.jipsoft.trabalho_final.domain.entity.Center;
import com.jipsoft.trabalho_final.domain.activity.CenterCreateActivity;
import com.jipsoft.trabalho_final.domain.activity.CostActivity;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CenterAdapter extends BaseAdapter<Center, CenterDAO> implements BasicAdapterMethod {

    public CenterAdapter(@NonNull Context context, List<Center> data) {
        super(context, R.layout.center_item_layout, data, new CenterDAO());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View layout = layoutInflater.inflate(R.layout.center_item_layout, parent, false);
        initializeComponents(layout, position);
        return layout;
    }

    @Override
    public void initializeComponents(View layout, int position) {
        TextView viewName = layout.findViewById(R.id.center_name);
        viewName.setText(getItem(position).getName());
        viewName.setOnClickListener(openCosts(position));

        ImageButton editImageBtn = layout.findViewById(R.id.cost_edit_btn);
        editImageBtn.setOnClickListener(update(position));

        ImageButton removeImageBtn = layout.findViewById(R.id.cost_remove_btn);
        removeImageBtn.setOnClickListener(remove(position));
    }

    @Override
    public View.OnClickListener update(int position) {
        return v -> {
            Intent intent = new Intent(getContext(), CenterCreateActivity.class);
            intent.putExtra("CENTER_ID", getItem(position).getId());
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
                            Center center = getItem(position);
                            repository.remove(center.getId());
                            remove(center);
                            notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        };
    }

    private View.OnClickListener openCosts(int position) {
        return v -> {
            Intent intent = new Intent(getContext(), CostActivity.class);
            intent.putExtra("CENTER_ID", getItem(position).getId());
            ((Activity) getContext()).startActivityForResult(intent, 2);
        };
    }
}
