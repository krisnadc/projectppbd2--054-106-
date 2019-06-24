package com.tugas.rental_kamera;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class KameraSewa extends AppCompatActivity {

    DataBase dm;
    EditText nama,sampai,awal;
    SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dm = new DataBase(this);
        setContentView(R.layout.activity_kamera_sewa);
        nama = (EditText)findViewById(R.id.editText);
        sampai = (EditText)findViewById(R.id.editText2);
        awal = (EditText)findViewById(R.id.editText3);
        dateFormatter = new SimpleDateFormat("y/M/d");

        sampai.setEnabled(false);
        awal.setEnabled(false);
    }

    public void sewa(View view) {
        SQLiteDatabase db = dm.getWritableDatabase();
        String penyewa = nama.getText().toString();
        db.execSQL("Update Kameratable set status = '"+"non"+"' where kode = '"+ getIntent().getStringExtra("idnya")+"'");
        db.execSQL("insert into penyewa(nama,kodem,awal,akhir) values ('"+penyewa+"','"+getIntent().getStringExtra("idnya")+"','"+awal.getText().toString()+"','"+sampai.getText().toString()+"')");
        MainActivity.coba.adddata();
        startActivity(new Intent(this,MainActivity.class));
    }

    public void end(View view) {
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */

                sampai.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }

    public void start(View view) {
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */

                awal.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
    }
}
