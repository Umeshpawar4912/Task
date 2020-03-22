package com.example.task_mb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.task_mb.R;
import com.example.task_mb.adapter.fevratesListAdapter;
import com.example.task_mb.datbade.SQLiteDBHelper;
import com.example.task_mb.model.ContactModel;

import java.util.ArrayList;

public class FavouritesScreenActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView emptyrview;
    ArrayList<ContactModel> contactModels = new ArrayList<>();
    fevratesListAdapter fevListAdapter;


    private SQLiteDBHelper database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_screen);
        setTitle("Favorite Contacts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = new SQLiteDBHelper(this);

        emptyrview =findViewById(R.id.fevemptyrview);
        recyclerView =findViewById(R.id.fevrecyclerView);

        Cursor cursorEmp = database.getFevContacts();

        if (cursorEmp.getCount() > 0) {
            emptyrview.setVisibility(View.GONE);
            getFevContactList(cursorEmp);

        }
        else {
            recyclerView.setVisibility(View.GONE);
            emptyrview.setVisibility(View.VISIBLE);

        }

    }

    private void getFevContactList(Cursor cursor) {

        contactModels.clear();

        if (cursor.moveToFirst()) {

            do {

                String cID = cursor.getString(cursor.getColumnIndex(ContactModel.ID));
                String cName = cursor.getString(cursor.getColumnIndex(ContactModel.PERSON_NAME));
                String cNumber = cursor.getString(cursor.getColumnIndex(ContactModel.CONTACT_NUMBER));
                String cStatus = cursor.getString(cursor.getColumnIndex(ContactModel.STATUS));
                String cIMG = cursor.getString(cursor.getColumnIndex(ContactModel.IMG));

                ContactModel contactModel = new ContactModel();

                contactModel.setC_id(cID);
                contactModel.setC_name(cName);
                contactModel.setC_number(cNumber);
                contactModel.setC_status(cStatus);
                contactModel.setC_url(cIMG);

                contactModels.add(contactModel);


            } while (cursor.moveToNext());


            fevListAdapter = new fevratesListAdapter(getApplicationContext(), contactModels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(fevListAdapter);
            fevListAdapter.notifyDataSetChanged();


        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
