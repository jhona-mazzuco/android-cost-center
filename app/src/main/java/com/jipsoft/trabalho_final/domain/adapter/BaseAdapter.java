package com.jipsoft.trabalho_final.domain.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class BaseAdapter<E, R> extends ArrayAdapter<E> {

    protected R repository;

    public BaseAdapter(@NonNull Context context, int layout, List<E> data, R repository) {
        super(context, layout, data);
        this.repository = repository;
    }
}
