package com.adafruit.bluefruit.le.connect.app.Database;

/**
 * Created by IT001 on 23-Jun-16.
 */
public class Student {
    // Labels table name
    public static final String TABLE = "Student";


    // Labels Table Columns names
    public static final String KEY_ID = "id";
    public static final String KEY_name = "name";
    public static final String KEY_data = "data";
    public static final String KEY_image = "image";

    //byte imageInByte[]
    // property help us to keep data
    public int student_ID;
    public String name;
    public String data;
    public byte imageInByte[];

}