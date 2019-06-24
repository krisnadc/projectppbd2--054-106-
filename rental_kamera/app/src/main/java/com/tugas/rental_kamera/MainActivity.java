package com.tugas.rental_kamera;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<Kamera> stockData;
    DataBase dm;
    Cursor cursor;
    RecyclerView rcv_stock;
    KameraAdapter adapter;
    public static MainActivity coba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coba = this;
        dm = new DataBase(this);
        adddata();
        rcv_stock = (RecyclerView) findViewById(R.id.yuk);

        adapter = new KameraAdapter(stockData);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rcv_stock.setLayoutManager(layoutManager);
        rcv_stock.setAdapter(adapter);
        rcv_stock.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), rcv_stock, new ClickListener() {
            @Override
            public void OnClick(View v, String position) {
                final String selection = position;
                new AlertDialog.Builder(coba)
                        .setTitle("Sewa ")
                        .setMessage("Apakah anda mau menyewa ? ")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent pindah = new Intent(getApplicationContext(), KameraSewa.class);
                                pindah.putExtra("idnya", selection);
                                startActivity(pindah);
                            }
                        }).create().show();
            }

            @Override
            public void onLongClick(View v, int position) {

            }
        }));
    }

    public void adddata() {
        stockData = new ArrayList<>();
        SQLiteDatabase db = dm.getReadableDatabase();
        cursor = db.rawQuery("select * from Kameratable where status = '"+"Available"+"'",null);
        cursor.moveToFirst();
        for (int cc=0; cc<cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            stockData.add(new Kamera(cursor.getString(0).toString(),cursor.getString(1).toString(),cursor.getString(2).toString()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.login){
            Intent tambah = new Intent(this,admin.class);
            startActivity(tambah);
        }
        return true;
    }

    private class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ClickListener clickListener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());

                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                Kamera list_item = stockData.get(rv.getChildAdapterPosition(child));
                clickListener.OnClick(child, list_item.getKodeKamera());
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

    }
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }
                }).create().show();
    }

}
