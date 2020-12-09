package com.jipsoft.trabalho_final.adapter;

import android.view.View;

public interface BasicAdapterMethod {

    void initializeComponents(View layout, int position);

    View.OnClickListener update(int position);

    View.OnClickListener remove(int position);
}
