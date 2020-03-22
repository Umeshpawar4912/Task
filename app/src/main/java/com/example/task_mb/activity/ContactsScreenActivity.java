package com.example.task_mb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import com.example.task_mb.R;
import com.example.task_mb.adapter.ContactListAdapter;
import com.example.task_mb.datbade.SQLiteDBHelper;
import com.example.task_mb.model.ContactModel;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class ContactsScreenActivity extends AppCompatActivity {

    Cursor cursor;
    Cursor existCursor;
    String name = "", phonenumber = "";
    RecyclerView recyclerView;
    TextView emptyrview;
    ArrayList<ContactModel> contactModels = new ArrayList<>();
    ContactListAdapter contactListAdapter;
    private SQLiteDBHelper database;
    public ACProgressFlower dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_screen);
        setTitle("Contact List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        database = new SQLiteDBHelper(this);

        emptyrview = findViewById(R.id.emptyrview);
        recyclerView = findViewById(R.id.recyclerView);

        Checkcontacts();

    }

    private void setupnewList() {
        Cursor cursorEmp = database.getAllContacts();

        if (cursorEmp.getCount() > 0) {
            emptyrview.setVisibility(View.GONE);
            getAllContactList(cursorEmp);

        } else {
            recyclerView.setVisibility(View.GONE);
            emptyrview.setVisibility(View.VISIBLE);
        }
    }

    private void Checkcontacts() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                showPrgressDialoge("Please Wait");
            }// End of onPreExecute method

            @Override
            protected Void doInBackground(Void... params) {
                GetContactsList();

                return null;
            }// End of doInBackground method

            @Override
            protected void onPostExecute(Void result) {
                setupnewList();
                progressDialogeDissmiss();

            }//End of onPostExecute method
        }.execute((Void[]) null);

    }

    private void getAllContactList(Cursor cursor) {

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


            contactListAdapter = new ContactListAdapter(getApplicationContext(), contactModels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(contactListAdapter);
            contactListAdapter.notifyDataSetChanged();


        }
    }

    public void GetContactsList() {

        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        while (cursor.moveToNext()) {

            String abcd = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String strNew = abcd.replaceAll("'", "");

            name = strNew;

            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            String image_uri = cursor.getString(cursor.
                    getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));


            //database.insert(name, phonenumber, "1", "");
            existCursor = database.IsNameExists(name, phonenumber);
            if (existCursor.getCount() > 0) {
            } else {
                database.insert(name, phonenumber, "1", image_uri);
            }


            existCursor.close();
            //StoreContacts.add(name + " "  + ":" + " " + phonenumber);
        }

        cursor.close();

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

    public void showPrgressDialoge(String progress_msg) {

        dialog = new ACProgressFlower.Builder(ContactsScreenActivity.this)
                .direction(ACProgressConstant.PIE_AUTO_UPDATE)
                .themeColor(Color.WHITE)
                .textSize(17)
                .text(progress_msg)
                // .text("Please wait..")
                .fadeColor(Color.DKGRAY).build();
        dialog.show();
    }


    public void progressDialogeDissmiss() {
        dialog.dismiss();
    }
}