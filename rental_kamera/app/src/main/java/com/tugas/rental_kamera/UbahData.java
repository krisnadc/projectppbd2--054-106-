package com.tugas.rental_kamera;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UbahData extends AppCompatActivity {

    DataBase dm;
    EditText kodem,namam;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_data);

        dm=new DataBase(this);

        kodem = (EditText)findViewById(R.id.kodeKamera1);
        namam = (EditText)findViewById(R.id.namaKamera1);

        kodem.setEnabled(false);
        SQLiteDatabase db = dm.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM Kameratable WHERE kode = '" +
                getIntent().getStringExtra("kode") + "'",null);
        cursor.moveToFirst();
        if (cursor.getCount()>0)
        {
            cursor.moveToPosition(0);
            kodem.setText(cursor.getString(0).toString());
            namam.setText(cursor.getString(1).toString());
        }
    }

    public void save(View view) {
        if(kodem.getText().toString().isEmpty()||namam.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Perhatikan Inputan",Toast.LENGTH_LONG).show();
        }
        else {
            SQLiteDatabase db = dm.getWritableDatabase();
            db.execSQL("Update Kameratable set nama = '"+namam.getText().toString()+"' where kode = '"+ getIntent().getStringExtra("kode")+"'");
            startActivity(new Intent(this, lihatsemuakamera.class));
        }
    }
    public void onBackPressed() {
        startActivity(new Intent(this,beranda_admin.class));
    }
}
