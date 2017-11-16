package com.adafruit.bluefruit.le.connect.app.Draw;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adafruit.bluefruit.le.connect.app.MainActivity;
import com.adafruit.bluefruit.le.connect.app.UartActivity;
import com.facebook.stetho.Stetho;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.adafruit.bluefruit.le.connect.app.Database.Student;
import com.adafruit.bluefruit.le.connect.app.Database.StudentRepo;
import com.adafruit.bluefruit.le.connect.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivityDraw extends AppCompatActivity implements android.view.View.OnClickListener{

    Button red, blue, yellow, green, black, gray, purple, orange, brown, gold, argent, khaki, magenta, royalblue, turquoise, coral;
    private int _Student_Id=0;
    View canvasView;
    final Context context = this;
    byte imageInByte[];
    int value;

    String messageEnvoyé;

    public void onClick(View view) {

        if (view== findViewById(R.id.red)){ //action sur le bouton add
            TouchView.setColorOfBrush("#ff0000");
        }
        else if (view== findViewById(R.id.blue)){ //action sur le bouton add
            TouchView.setColorOfBrush("#0000ff");
        }
        else if (view== findViewById(R.id.yellow)){ //action sur le bouton add
            TouchView.setColorOfBrush("#ffff00");
        }
        else if (view== findViewById(R.id.green)){ //action sur le bouton add
            TouchView.setColorOfBrush("#008000");
        }
        else if (view== findViewById(R.id.black)){ //action sur le bouton add
            TouchView.setColorOfBrush("#000000");
        }
        else if (view== findViewById(R.id.gray)){ //action sur le bouton add
            TouchView.setColorOfBrush("#a9a9a9");
        }
        else if (view== findViewById(R.id.purple)){ //action sur le bouton add
            TouchView.setColorOfBrush("#800080");
        }
        else if (view== findViewById(R.id.orange)){ //action sur le bouton add
            TouchView.setColorOfBrush("#ffa500");
        }
        else if (view== findViewById(R.id.brown)){ //action sur le bouton add
            TouchView.setColorOfBrush("#a52a2a");
        }
        else if (view== findViewById(R.id.gold)){ //action sur le bouton add
            TouchView.setColorOfBrush("#ffd700");
        }
        else if (view== findViewById(R.id.argent)){ //action sur le bouton add
            TouchView.setColorOfBrush("#c0c0c0");
        }
        else if (view== findViewById(R.id.khaki)){ //action sur le bouton add
            TouchView.setColorOfBrush("#f0e68c");
        }
        else if (view== findViewById(R.id.magenta)){ //action sur le bouton add
            TouchView.setColorOfBrush("#ff00ff");
        }
        else if (view== findViewById(R.id.royalblue)){ //action sur le bouton add
            TouchView.setColorOfBrush("#4169e1");
        }
        else if (view== findViewById(R.id.turquoise)){ //action sur le bouton add
            TouchView.setColorOfBrush("#40e0d0");
        }
        else if (view== findViewById(R.id.coral)){ //action sur le bouton add
            TouchView.setColorOfBrush("#ff7f50");
        }

    }


    public void sendToDatabase(){
        //--------------------------------------------------------------------------------------
        StudentRepo repo = new StudentRepo(this); //déclaration du repository de l'étudiant
        Student student = new Student(); //nouveau etudiant
        student.data= TouchView.inputString; //déclaration en string
        student.name=TouchView.nameOfDrawing;
        student.imageInByte = imageInByte;
        student.student_ID=_Student_Id;
        _Student_Id = repo.insert(student);

        Toast.makeText(this,"New Student Insert", Toast.LENGTH_SHORT).show(); //pop up
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Stetho.initializeWithDefaults(this);

        setContentView(R.layout.activity_main_draw);
        canvasView = findViewById(R.id.activity_main_layout);

        Bundle b = getIntent().getExtras();
        value = -1; // or other values
        if(b != null)
            value = b.getInt("key");

        String caller     = getIntent().getStringExtra("caller");
        System.out.println("yo");
        if(caller!= null ){
            if(caller.equals("DrawingList") == true){
                Gson gson = new Gson();

                StudentRepo repo = new StudentRepo(this);
                Student student = new Student();

                student = repo.getStudentById(value+1);

                Type type = new TypeToken<ArrayList<Point>>() {}.getType();

                ArrayList<Point> finalOutputString = gson.fromJson(student.data, type);

                TouchView.paintFromDatabase(finalOutputString);
            }
        }


        red = (Button) findViewById(R.id.red);
        red.setOnClickListener(this);

        blue = (Button) findViewById(R.id.blue);
        blue.setOnClickListener(this);

        yellow = (Button) findViewById(R.id.yellow);
        yellow.setOnClickListener(this);

        green = (Button) findViewById(R.id.green);
        green.setOnClickListener(this);

        black = (Button) findViewById(R.id.black);
        black.setOnClickListener(this);

        gray = (Button) findViewById(R.id.gray);
        gray.setOnClickListener(this);

        purple = (Button) findViewById(R.id.purple);
        purple.setOnClickListener(this);

        orange = (Button) findViewById(R.id.orange);
        orange.setOnClickListener(this);

        brown = (Button) findViewById(R.id.brown);
        brown.setOnClickListener(this);

        gold = (Button) findViewById(R.id.gold);
        gold.setOnClickListener(this);

        argent = (Button) findViewById(R.id.argent);
        argent.setOnClickListener(this);

        khaki = (Button) findViewById(R.id.khaki);
        khaki.setOnClickListener(this);

        magenta = (Button) findViewById(R.id.magenta);
        magenta.setOnClickListener(this);

        royalblue = (Button) findViewById(R.id.royalblue);
        royalblue.setOnClickListener(this);

        turquoise = (Button) findViewById(R.id.turquoise);
        turquoise.setOnClickListener(this);

        coral = (Button) findViewById(R.id.coral);
        coral.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_draw, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,DrawingList.class);
            startActivity(intent);
            return true;
        }

        else if (id == R.id.action_erase) {
            TouchView.setColorOfBrush("#FFFFFF");
            return true;
        }

        else if (id == R.id.action_erase_all) {
            TouchView.eraseAll();
            return true;
        }
        else if (id == R.id.turnoff) {
            messageEnvoyé = "turnoff";
            UartActivity.turnoff = true;

            Intent intent = new Intent(MainActivityDraw.this, UartActivity.class);
            intent.putExtra("caller", messageEnvoyé);
            startActivity(intent);

            return true;
        }
        else if (id == R.id.action_send_bluefruit) {
            messageEnvoyé = TouchView.sendToBluefruit();
            System.out.println(" message envoyé  = " + messageEnvoyé);


            Intent intent = new Intent(MainActivityDraw.this, UartActivity.class);
            intent.putExtra("caller", messageEnvoyé);
            startActivity(intent);

            return true;
        }
        else if (id == R.id.action_add) {
            LayoutInflater li = LayoutInflater.from(context);
            View promptsView = li.inflate(R.layout.custom, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    context);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    System.out.println("fin");
                                    System.out.println(userInput.getText());
                                    TouchView.sendPoint(userInput.getText().toString());

                                    Bitmap bitmap;
                                    View v1 = findViewById(R.id.activity_main_layout);// get ur root view id
                                    v1.setDrawingCacheEnabled(true);
                                    bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                                    v1.setDrawingCacheEnabled(false);

                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                                    imageInByte = stream.toByteArray();

                                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
                                    File f = new File(Environment.getExternalStorageDirectory()
                                            + File.separator + "test.jpg");
                                    try {
                                        f.createNewFile();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    FileOutputStream fo = null;
                                    try {
                                        fo = new FileOutputStream(f);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        fo.write(bytes.toByteArray());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        fo.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                    sendToDatabase();
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
