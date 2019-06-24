package com.tugas.rental_kamera;

import android.view.View;

public interface ClickListener {
    void OnClick(View v, String position);

    void onLongClick(View v, int position);
}
