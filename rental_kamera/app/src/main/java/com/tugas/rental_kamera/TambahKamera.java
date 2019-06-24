package com.tugas.rental_kamera;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TambahKamera extends AppCompatActivity {

    DataBase dm;
    EditText kodem,namam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kamera);

        dm=new DataBase(this);

        kodem = (EditText)findViewById(R.id.kodeKamera);
        namam = (EditText)findViewById(R.id.namaKamera);
    }

    public void save(View view) {
        if(kodem.getText().toString().isEmpty()||namam.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Perhatikan Inputan",Toast.LENGTH_LONG).show();
        }
        else {
            SQLiteDatabase db = dm.getWritableDatabase();
            db.execSQL("insert into Kameratable values ('" + kodem.getText().toString() + "','" + namam.getText().toString() + "','" + "Available" + "')");
            MainActivity.coba.adddata();
            startActivity(new Intent(this, beranda_admin.class));
        }
    }
    public void onBackPressed() {
        startActivity(new Intent(this,beranda_admin.class));
    }
}
