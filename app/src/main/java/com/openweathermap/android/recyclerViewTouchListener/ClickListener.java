package com.openweathermap.android.recyclerViewTouchListener;

import android.view.View;

/**
 * Created by Rohit on 25-02-2017.
 */

public interface ClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
