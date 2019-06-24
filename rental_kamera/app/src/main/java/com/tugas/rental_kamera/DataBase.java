package com.tugas.rental_kamera;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Kamera.db";

    private static final int DATABASE_VERSION = 1;

    public DataBase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Kameratable(kode text primary key, nama text not null,status text not null)");
        db.execSQL("CREATE TABLE penyewa(nomor auto_increment primary key, nama text not null,kodem text not null,awal text not null,akhir text not null,FOREIGN KEY (kodem) references tableKamera(kode) on update cascade on delete restrict)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
