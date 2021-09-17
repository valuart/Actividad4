package com.example.actividad4;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvContactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1000);
        }
        Access();
    }

    public void Access(){
        Uri contactos = Uri.parse("content://contacts/people");
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(contactos, null, null, null, null);

        String id = null;
        String nombre = null;

        if(c!= null && c.getCount()>0) {
            StringBuilder sb = new StringBuilder();

            while (c.moveToNext()) {
                id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                nombre = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                sb.append(id + " " + nombre);
            }
            tvContactos.setText(sb.toString());
        }else{
            tvContactos.setText("Sin datos");
        }

    }
}