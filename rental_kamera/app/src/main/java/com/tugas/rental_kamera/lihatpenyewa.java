package com.tugas.rental_kamera;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class lihatpenyewa extends AppCompatActivity {
    TextView namap,kam,awalnya,sampainnya;
    DataBase dm;
    Cursor cursor;
    String n="Nama:\n";String k="Kamera\n";String a="Awal Sewa\n";String s="Akhir Sewa\n";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihatpenyewa);
        namap = (TextView)findViewById(R.id.textView3);
        kam = (TextView)findViewById(R.id.textView4);
        awalnya = (TextView)findViewById(R.id.textView5);
        sampainnya = (TextView)findViewById(R.id.textView6);
        dm = new DataBase(this);
        SQLiteDatabase db = dm.getReadableDatabase();
        cursor = db.rawQuery("select a.nama,b.nama,a.awal,a.akhir from penyewa a,Kameratable b on (a.kodem = b.kode)",null);
        cursor.moveToFirst();
        for (int cc=0; cc<cursor.getCount(); cc++) {
            cursor.move(cc);
            n+=cursor.getString(0).toString()+"\n";
            k+=cursor.getString(1).toString()+"\n";
            a+=cursor.getString(2).toString()+"\n";
            s+=cursor.getString(3).toString()+"\n";
        }
        namap.setText(n);
        kam.setText(k);
        awalnya.setText(a);
        sampainnya.setText(s);
    }
    public void onBackPressed() {
        startActivity(new Intent(this,beranda_admin.class));
    }
}
