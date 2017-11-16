package com.adafruit.bluefruit.le.connect.app.Database;

/**
 * Created by IT001 on 23-Jun-16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class StudentRepo { //declaration de la classe pour le repository etudiant
    private DBHelper dbHelper; //utilisation du package dbHelper

    public StudentRepo(Context context) { //constructeur
        dbHelper = new DBHelper(context);
    }

    public int insert(Student student) { //methode d'insertion d'un nouvel étudiant

        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Student.KEY_data, student.data);
        values.put(Student.KEY_name, student.name);
        values.put(Student.KEY_image, student.imageInByte);


        // Inserting Row
        long student_Id = db.insert(Student.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) student_Id;
    }

    public void delete(int student_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Student.TABLE, Student.KEY_ID + "= ?", new String[] { String.valueOf(student_Id) });
        db.close(); // Closing database connection
    }

    public void update(Student student) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Student.KEY_data,student.data);
        values.put(Student.KEY_name, student.name);
        values.put(Student.KEY_image, student.imageInByte);


        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Student.TABLE, values, Student.KEY_ID + "= ?", new String[] { String.valueOf(student.student_ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<Student> getStudentList() {
        //Open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Student.KEY_ID + "," +
                Student.KEY_name + "," +
                Student.KEY_image + "," +
                Student.KEY_data +
                " FROM " + Student.TABLE;

        //Student student = new Student();
        ArrayList<Student> studentList = new ArrayList<Student>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();

                //HashMap<String, String> student = new HashMap<String, String>();
                student.student_ID =cursor.getInt(cursor.getColumnIndex(Student.KEY_ID));
                student.name =cursor.getString(cursor.getColumnIndex(Student.KEY_name));
                student.data  =cursor.getString(cursor.getColumnIndex(Student.KEY_data));
                student.imageInByte  =cursor.getBlob(cursor.getColumnIndex(Student.KEY_image));

                //student.put("id", cursor.getString(cursor.getColumnIndex(Student.KEY_ID)));
                //student.put("name", cursor.getString(cursor.getColumnIndex(Student.KEY_name)));
                //student.put("data", cursor.getString(cursor.getColumnIndex(Student.KEY_data)));
                //student.put("image", cursor.getString(cursor.getColumnIndex(Student.KEY_image)));
                //byte[] image = cursor.getBlob(1);
                studentList.add(student);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return studentList;
    }

    public Student getStudentById(int Id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Student.KEY_ID + "," +
                Student.KEY_name + "," +
                Student.KEY_image + "," +
                Student.KEY_data +
                " FROM " + Student.TABLE
                + " WHERE " +
                Student.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Student student = new Student();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst()) {
            do {
                student.student_ID =cursor.getInt(cursor.getColumnIndex(Student.KEY_ID));
                student.name =cursor.getString(cursor.getColumnIndex(Student.KEY_name));
                student.data  =cursor.getString(cursor.getColumnIndex(Student.KEY_data));
                student.imageInByte  =cursor.getBlob(cursor.getColumnIndex(Student.KEY_image));


            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return student;
    }
}