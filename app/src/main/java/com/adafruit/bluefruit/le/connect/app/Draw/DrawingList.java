package com.adafruit.bluefruit.le.connect.app.Draw;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.adafruit.bluefruit.le.connect.app.Database.Student;
import com.adafruit.bluefruit.le.connect.app.Database.StudentRepo;
import com.adafruit.bluefruit.le.connect.R;

import java.util.ArrayList;

public class DrawingList extends Activity implements View.OnClickListener{

    ListView mListView;
    static int position_dataBase;
    Bitmap bitmap2;
    Context context;
    byte[] imageFromDatabase;
    String nameFromDatabase;
    ImageView mImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //on definit la page xml pour la vision
        setContentView(R.layout.drawing_liste);
        context = this;

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        StudentRepo repo = new StudentRepo(this);

        ArrayList<Student> studentList =  repo.getStudentList(); //base de données

        ArrayList<String> nom = new ArrayList<String>(); //arrayliste de noms
        ArrayList<Bitmap> images = new ArrayList<Bitmap>(); //arrayliste de noms

        for(int i = 0; i< studentList.size(); i++){
            nameFromDatabase = studentList.get(i).name;
            nom.add(nameFromDatabase);

            imageFromDatabase =  studentList.get(i).imageInByte;
            bitmap2 = BitmapFactory.decodeByteArray(imageFromDatabase, 0, imageFromDatabase.length);

            images.add(bitmap2);
        }

        mListView=(ListView) findViewById(R.id.listView);
        mListView.setAdapter(new CustomAdapter(this, nom, images));

    }

    public void onClick(View view) {

    }

    public static void showChoosenDrawingFromDatabase(int position){
        position_dataBase = position;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        System.out.println("back");
        Intent intent = new Intent(DrawingList.this, MainActivityDraw.class);
        Bundle b = new Bundle();
        b.putInt("key", position_dataBase); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        intent.putExtra("caller", "DrawingList");
        startActivity(intent);

        //String caller     = getIntent().getStringExtra("caller");
        //Class callerClass = Class.forName(caller);


        startActivity(intent);
        finish();

        //Intent myIntent = new Intent(getApplicationContext(), MainActivityBle.class);
        //startActivityForResult(myIntent, 0);
        return true;

    }

}
