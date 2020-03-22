package com.example.task_mb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.task_mb.R;
import com.example.task_mb.datbade.SQLiteDBHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button Contact, Favorites, Delete;
    public static final int RequestPermissionCode = 1;

    private SQLiteDBHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new SQLiteDBHelper(this);
        Contact = findViewById(R.id.contact);
        Favorites = findViewById(R.id.favorites);
        Delete = findViewById(R.id.delete);

        Contact.setOnClickListener(this);
        Favorites.setOnClickListener(this);
        Delete.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.contact:

                EnableRuntimePermission();
                break;

            case R.id.favorites:

                Intent i_favorites = new Intent(this, FavouritesScreenActivity.class);
                startActivity(i_favorites);
                break;

            case R.id.delete:

                Intent i_delete = new Intent(this, DeleteScreenActivity.class);
                startActivity(i_delete);
                break;

            default:
                break;
        }

    }


    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
            Toast.makeText(MainActivity.this, "CONTACTS permission allows us to Access CONTACTS app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.READ_CONTACTS}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int resulted, String per[], int[] PResult) {

        switch (resulted) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent contact = new Intent(MainActivity.this,ContactsScreenActivity.class);
                    startActivity(contact);
                } else {

                    Toast.makeText(MainActivity.this, "Permission Canceled, Now your application cannot access CONTACTS.", Toast.LENGTH_LONG).show();

                }
                break;
        }

    }

}
