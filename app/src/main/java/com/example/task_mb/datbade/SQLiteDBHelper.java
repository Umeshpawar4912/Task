package com.example.task_mb.datbade;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.task_mb.model.ContactModel;


public class SQLiteDBHelper extends SQLiteOpenHelper {

    Context context;

    // Database Information
    static final String DB_NAME = "MIND_BROWSER.DB";
    // database version
    static final int DB_VERSION = 1;


    public SQLiteDBHelper(Context context) {


        super(context, DB_NAME, null, DB_VERSION);

        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContactModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ContactModel.TABLE_NAME);

        onCreate(db);
    }

    public long insert(String NAME, String NUMBER, String STATUS, String IMAGE) {

        SQLiteDatabase database;
        database = this.getWritableDatabase();

        ContentValues contentValue = new ContentValues();

        contentValue.put(ContactModel.PERSON_NAME, NAME);
        contentValue.put(ContactModel.CONTACT_NUMBER, NUMBER);
        contentValue.put(ContactModel.STATUS, STATUS);
        contentValue.put(ContactModel.IMG, IMAGE);


        long rowInserted = database.insert(ContactModel.TABLE_NAME, null, contentValue);
        database.close();
        return rowInserted;


    }

    public Cursor IsNameExists(String name,String number  ) {

        SQLiteDatabase db = this.getWritableDatabase();
        //String selectQuery = "SELECT * FROM " + ContactModel.TABLE_NAME + " WHERE " + ContactModel.PERSON_NAME + " = '" + name + "'";
        //String selectQuery = "SELECT  NAME FROM  CONTACT_LIST" + " WHERE " + ContactModel.PERSON_NAME + " = '" + name + "' ;";

        String selectQuery = "SELECT  * FROM " + ContactModel.TABLE_NAME + " WHERE " + ContactModel.PERSON_NAME + " = '" + name + "'" +
                " AND " + ContactModel.CONTACT_NUMBER + " = '" + number + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;

    }

    public Cursor getAllContacts() {

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM  CONTACT_LIST"+ " WHERE " + ContactModel.STATUS+ " = '" + "1" + "'"+
                " OR " + ContactModel.STATUS + " = '" + "2" + "'";;
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor getFevContacts() {

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM  CONTACT_LIST"+ " WHERE " + ContactModel.STATUS+ " = '" + "2" + "'";;
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor getdelContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM  CONTACT_LIST"+ " WHERE " + ContactModel.STATUS+ " = '" + "3" + "'";;
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public void updateStatus(String name,String status,String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + ContactModel.TABLE_NAME + " SET " + ContactModel.STATUS + " = " +
                "'" + status + "'" + " WHERE " + ContactModel.PERSON_NAME+ " = '" + name + "'"+
                " AND " + ContactModel.ID + " = '" + id + "'";
        db.execSQL(query);
        db.close();
    }

}
