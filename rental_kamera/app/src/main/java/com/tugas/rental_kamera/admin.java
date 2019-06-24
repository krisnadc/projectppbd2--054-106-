package com.tugas.rental_kamera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class admin extends AppCompatActivity {
    EditText user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        user = (EditText) findViewById(R.id.edit_user);
        pass = (EditText) findViewById(R.id.edit_pass);
    }

    public void beradm(View view) {
        if(user.getText().toString().equals("admin")&& pass.getText().toString().equals("admin1")){
            Intent pin = new Intent(this,beranda_admin.class);
            startActivity(pin);
        }
        else{
            Toast.makeText(getApplicationContext(), "User & Password SALAH!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
    }

}
