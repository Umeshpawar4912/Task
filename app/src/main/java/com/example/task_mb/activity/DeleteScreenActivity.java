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
import com.example.task_mb.adapter.ContactListAdapter;
import com.example.task_mb.adapter.delListAdapter;
import com.example.task_mb.datbade.SQLiteDBHelper;
import com.example.task_mb.model.ContactModel;

import java.util.ArrayList;

public class DeleteScreenActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView emptyrview;
    ArrayList<ContactModel> contactModels = new ArrayList<>();
    delListAdapter del_ListAdapter;


    private SQLiteDBHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_screen);
        setTitle("Deleted Contact List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = new SQLiteDBHelper(this);

        emptyrview = findViewById(R.id.delemptyrview);
        recyclerView = findViewById(R.id.delrecyclerView);

        Cursor cursorEmp = database.getdelContacts();

        if (cursorEmp.getCount() > 0) {
            emptyrview.setVisibility(View.GONE);
            getDeleteContactList(cursorEmp);

        } else {
            recyclerView.setVisibility(View.GONE);
            emptyrview.setVisibility(View.VISIBLE);

        }

    }

    private void getDeleteContactList(Cursor cursor) {

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


            del_ListAdapter = new delListAdapter(getApplicationContext(), contactModels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(del_ListAdapter);
            del_ListAdapter.notifyDataSetChanged();


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
