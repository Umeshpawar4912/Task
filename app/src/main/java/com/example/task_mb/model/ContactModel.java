package com.example.task_mb.model;

public class ContactModel {


    public static final String TABLE_NAME = "CONTACT_LIST";

    // Table columns

    public static final String ID = "ID";
    public static final String PERSON_NAME = "NAME";
    public static final String CONTACT_NUMBER = "NUMBER";
    public static final String STATUS = "STATUS";
    public static final String IMG = "IMAGE";


    // Creating table query
    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "("+ ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + PERSON_NAME+ " TEXT, "
            + CONTACT_NUMBER+ " TEXT, "
            + STATUS+ " TEXT, "
            + IMG + " TEXT);";
    //###################################################################################


    private String c_id="";
    private String c_name="";
    private String c_number="";
    private String c_status="";
    private String c_url="";


    public String getC_url() {
        return c_url;
    }

    public void setC_url(String c_url) {
        this.c_url = c_url;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_number() {
        return c_number;
    }

    public void setC_number(String c_number) {
        this.c_number = c_number;
    }

    public String getC_status() {
        return c_status;
    }

    public void setC_status(String c_status) {
        this.c_status = c_status;
    }

}
