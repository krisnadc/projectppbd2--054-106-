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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class beranda_admin extends AppCompatActivity {

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
        setContentView(R.layout.activity_beranda_admin);
        dm = new DataBase(this);
        beradm = this;
        stockData = new ArrayList<>();
        RefreshList();
        rcv_stock = (RecyclerView) findViewById(R.id.yukk);

        adapter = new KameraAdapter(stockData);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(beranda_admin.this);
        rcv_stock.setLayoutManager(layoutManager);
        rcv_stock.setAdapter(adapter);
        rcv_stock.addOnItemTouchListener(new beranda_admin.RecyclerTouchListener(getApplicationContext(), rcv_stock, new ClickListener() {
            @Override
            public void OnClick(View v, String position) {

                final String selection = position; //.getItemAtPosition(arg2).toString();
                new AlertDialog.Builder(beradm)
                        .setTitle("Sewa ")
                        .setMessage("Apakah Kamera Telah Di Kembalikan ? ")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                SQLiteDatabase db = dm.getWritableDatabase();
                                db.execSQL("Update Kameratable set status = '"+"Available"+"' where kode = '"+ selection+"'");
                                startActivity(new Intent(getApplicationContext(),beranda_admin.class));
                            }
                        }).create().show();

            }

            @Override
            public void onLongClick(View v, int position) {

            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionadmin, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.tambah){
            Intent pin = new Intent(this,TambahKamera.class);
            startActivity(pin);
        }
        if (item.getItemId()==R.id.logout){
            Intent pin = new Intent(this,MainActivity.class);
            startActivity(pin);
        }
        if (item.getItemId()==R.id.lihat){
            Intent pin = new Intent(this,lihatsemuakamera.class);
            startActivity(pin);
        }
        if (item.getItemId()==R.id.lihatpenyewa){
            Intent pin = new Intent(this,lihatpenyewa.class);
            startActivity(pin);
        }
        return true;
    }

    public void RefreshList(){
        // menampilkan data biodata
        SQLiteDatabase db = dm.getReadableDatabase();
        cursor = db.rawQuery("select * from Kameratable where status = '"+"non"+"'",null);
        cursor.moveToFirst();
        for (int cc=0; cc<cursor.getCount(); cc++){
            cursor.moveToPosition(cc);
            try {
                stockData.add(new Kamera(cursor.getString(0).toString(), cursor.getString(1).toString(), cursor.getString(2).toString()));
            }
            catch(Exception e){
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
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

    }

}
