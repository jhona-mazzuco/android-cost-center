package com.jipsoft.trabalho_final.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.jipsoft.trabalho_final.R;
import com.jipsoft.trabalho_final.domain.dao.CenterDAO;
import com.jipsoft.trabalho_final.domain.entity.Center;
import com.jipsoft.trabalho_final.view.CenterCreateActivity;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CenterAdapter extends ArrayAdapter<Center> {

    public CenterAdapter(@NonNull Context context, List<Center> data) {
        super(context, R.layout.center_item_layout, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View layout = layoutInflater.inflate(R.layout.center_item_layout, parent, false);
        initializeComponents(layout, position);
        return layout;
    }

    private void initializeComponents(View layout, int position) {
        TextView viewName = layout.findViewById(R.id.center_name);
        viewName.setText(getItem(position).getName());

        ImageButton editImageBtn = layout.findViewById(R.id.center_edit_btn);
        ImageButton removeImageBtn = layout.findViewById(R.id.center_remove_btn);

        editImageBtn.setOnClickListener(editCenter(position));
        removeImageBtn.setOnClickListener(removeCenter(position));
    }

    private View.OnClickListener editCenter(int position) {
        return v -> {
            Intent instant = new Intent(getContext(), CenterCreateActivity.class);
            instant.putExtra("CENTER_ID", getItem(position).getId());
            ((Activity) getContext()).startActivityForResult(instant, 2);
        };
    }

    private View.OnClickListener removeCenter(int position) {
        return v -> {
            Center center = getItem(position);
            CenterDAO.remove(center.getId());
            remove(center);
            notifyDataSetChanged();
        };
    }
}
