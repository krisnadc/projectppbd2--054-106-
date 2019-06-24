package com.tugas.rental_kamera;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class lihatsemuakamera extends AppCompatActivity {

    static ArrayList<Kamera> stockData;
    Menu option;
    protected Cursor cursor;
    DataBase dm;
    RecyclerView rcv_stock;
    KameraAdapter adapter;
    public static beranda_admin beradm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihatsemuakamera);
        dm = new DataBase(this);
        stockData = new ArrayList<>();
        RefreshList();
        rcv_stock = (RecyclerView) findViewById(R.id.yukkk);

        adapter = new KameraAdapter(stockData);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(lihatsemuakamera.this);
        rcv_stock.setLayoutManager(layoutManager);
        rcv_stock.setAdapter(adapter);
        rcv_stock.addOnItemTouchListener(new lihatsemuakamera.RecyclerTouchListener(getApplicationContext(), rcv_stock, new ClickListener() {
            @Override
            public void OnClick(View v, String position) {
                final String selection = position;
                final CharSequence[] dialogitem = {"Edit Data", "Hapus Data"};
                AlertDialog.Builder builder = new AlertDialog.Builder(lihatsemuakamera.this);
                builder.setTitle("Pilih Menu:");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch(item){
                            case 0 :
                                Intent i = new Intent(getApplicationContext(), UbahData.class);
                                i.putExtra("kode", selection);
                                startActivity(i);
                                break;

                            case 1 :
                                SQLiteDatabase db = dm.getWritableDatabase();
                                db.execSQL("delete from Kameratable where kode = '"+selection+"'");
                                Toast.makeText(getApplicationContext(),selection+" Telah Di Hapus",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),lihatsemuakamera.class));
                                break;
                        }
                    }
                });
                builder.create().show();
            }

            @Override
            public void onLongClick(View v, int position) {

            }
        }));
    }


    public void RefreshList(){
        // menampilkan data biodata
        SQLiteDatabase db = dm.getReadableDatabase();
        cursor = db.rawQuery("select * from Kameratable",null);
        cursor.moveToFirst();
        for (int cc=0; cc<cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            stockData.add(new Kamera(cursor.getString(0).toString(),cursor.getString(1).toString(),cursor.getString(2).toString()));
        }
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,beranda_admin.class));
    }
}
