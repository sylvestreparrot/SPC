package com.adafruit.bluefruit.le.connect.app.Draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Point{
    float x;
    float y;
    String color;

    Point(float x, float y, String color){
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public String getColor(){
        return this.color;
    }

    public void setX(float x){
        this.x = x;
    }

    public void setY(float y){
        this.y = y;
    }

    public void setColor(String color){
        this.color = color;
    }
}

public class TouchView extends View {

    static String messageEnvoye = "";


    private float x;
    private float y;

    static boolean drawFromDatabase = false;
    static boolean eraseAll = false;

    static String colorPainting = "#000000";

    public static List<Float> gridX = new ArrayList<>(); //array to store our X points 
    public static List<Float> gridY = new ArrayList<>(); // array to store our Y point 
    public static List<String> colorValue = new ArrayList<>(); // array to store our Y point  

    public static List<Point> pointToSend = new ArrayList<Point>(); // array to store our Y point  

    public static ArrayList<Point> finalOutputStringToDraw = new ArrayList<Point>();

    Paint drawPaint; //painting brush for the grid 

    Paint drawPaintGrid; //painting brush for the drawing 

    public static String inputString;
    public static String nameOfDrawing;

    public TouchView(Context context) {
        super(context);

    }

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        drawPaint = new Paint(Paint.DITHER_FLAG);
        drawPaint.setColor(Color.parseColor(colorPainting));
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeWidth(3);
        setWillNotDraw(false);

        drawPaintGrid = new Paint(Paint.DITHER_FLAG);
        drawPaintGrid.setColor(Color.parseColor("#000000"));
        drawPaintGrid.setStyle(Paint.Style.FILL);
        drawPaintGrid.setStrokeJoin(Paint.Join.ROUND);
        drawPaintGrid.setStrokeWidth(1);
        setWillNotDraw(false);
    }

    @Override
    public void onSizeChanged(int w, int h, int width, int height) {
        super.onSizeChanged(w, h, width, height);
    }

    public static void setColorOfBrush(String newColor){
        colorPainting = newColor;
    }

    public static void paintFromDatabase(ArrayList<Point> finalOutputString){
        /*Point pointDataBase;

        finalOutputStringToDraw.clear();
        System.out.println(finalOutputString.size());

        for(int l = 0; l < finalOutputString.size(); l++){
            float xDataBase = finalOutputString.get(l).getX();
            float yDataBase = finalOutputString.get(l).getY();
            String colorDataBase = finalOutputString.get(l).getColor();

            pointDataBase = new Point(xDataBase,yDataBase,colorDataBase);
            finalOutputStringToDraw.add(pointDataBase);
        }
        drawFromDatabase = true;
        eraseAll = false;*/

        Point pointDataBase;

        gridX.clear();
        gridY.clear();
        colorValue.clear();

        finalOutputStringToDraw.clear();
        System.out.println(finalOutputString.size());

        for(int l = 0; l < finalOutputString.size(); l++){

            float xDataBase = finalOutputString.get(l).getX();
            float yDataBase = finalOutputString.get(l).getY();
            String colorDataBase = finalOutputString.get(l).getColor();

            gridX.add(xDataBase);
            gridY.add(yDataBase);
            colorValue.add(colorDataBase);

            //pointDataBase = new Point(xDataBase,yDataBase,colorDataBase);
            //finalOutputStringToDraw.add(pointDataBase);
        }
        //drawFromDatabase = true;
        //eraseAll = false;
    }

    public static void eraseAll(){
        gridX.clear();
        gridY.clear();
        colorValue.clear();
    }


    public static String sendToBluefruit(){
        System.out.println("sendToBluefruit");

        String messageFinal = "";
        String messagePoint = "";

        String messageX;
        String messageY;
        String messageColor;

        System.out.println(gridX.size());

        for(int nbPoint = 0; nbPoint< gridX.size(); nbPoint++) {

            for(int i = 0; i< 22; i++){
                if((i*50 < gridX.get(nbPoint)) && (gridX.get(nbPoint) < (i+1)*50)){
                    for(int j = 0; j< 28; j++){
                        if((j*50 < gridY.get(nbPoint)) && (gridY.get(nbPoint) < (j+1)*50)){

                            StringBuilder sbX = new StringBuilder();
                            sbX.append("");
                            sbX.append(i);

                            StringBuilder sbY = new StringBuilder();
                            sbY.append("");
                            sbY.append(j);

                            messageX = sbX.toString();
                            messageY = sbY.toString();
                            messageColor = colorValue.get(nbPoint);

                            messagePoint = "("+messageX + "," + messageY + "," + messageColor + ")";
                            System.out.println("messagePoint =  " + messagePoint);

                        }
                    }
                }
            }
            messageFinal = messageFinal + messagePoint;
        }

        System.out.println("message final" + messageFinal);

        ArrayList<String> sub = new ArrayList<String>();
        ArrayList<String> newSub = new ArrayList<String>();

        Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(messageFinal);
        while (m.find()){
            sub.add(m.group());
        }

        for(int i = 0; i< sub.size(); i ++){
                if(newSub.contains(sub.get(i))){

                }
                else{
                    newSub.add(sub.get(i));
                }
        }

        for(int i = 0; i< newSub.size(); i ++){
            messageEnvoye  = messageEnvoye + newSub.get(i);
        }
        //System.out.println(" message envoyé  = "+messageEnvoye);

        return messageEnvoye;

    }

    public static void sendPoint(String name){
        Point send;

        Gson gson = new Gson();

        for(int nbPoint = 0; nbPoint < colorValue.size(); nbPoint++){
            send = new Point(gridX.get(nbPoint), gridY.get(nbPoint), colorValue.get(nbPoint));
            pointToSend.add(send);

        }
        inputString = gson.toJson(pointToSend);
        nameOfDrawing = name;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i = 0; i< 22; i++){
            canvas.drawLine(50*i, 0, 50*i,1400, drawPaintGrid);
        }
        for(int i = 0; i< 28; i++){
            canvas.drawLine(0,50*i, 1200, 50*i, drawPaintGrid);
        }

        if(drawFromDatabase == false){
            for(int nbPoint = 0; nbPoint< gridX.size(); nbPoint++){
                drawPaint.setColor(Color.parseColor(colorValue.get(nbPoint)));
                for(float i = 0; i< 22; i++){
                    if((i*50 < gridX.get(nbPoint)) && (gridX.get(nbPoint) < (i+1)*50)){
                        for(float j = 0; j< 28; j++){
                            if((j*50 < gridY.get(nbPoint)) && (gridY.get(nbPoint) < (j+1)*50)){
                                canvas.drawRect((i*50)+2,(j*50)+2, ((i+1)*50)-2, ((j+1)*50)-2, drawPaint);
                            }
                        }
                    }
                }
            }
        }
        else{
            //blank everything
            for(int nbPoint = 0; nbPoint< finalOutputStringToDraw.size(); nbPoint++){
                drawPaint.setColor(Color.parseColor(finalOutputStringToDraw.get(nbPoint).getColor()));

                for(float i = 0; i< 22; i++){
                    if((i*50 < finalOutputStringToDraw.get(nbPoint).getX()) && (finalOutputStringToDraw.get(nbPoint).getX() < (i+1)*50)){
                        for(float j = 0; j< 28; j++){
                            if((j*50 < finalOutputStringToDraw.get(nbPoint).getY()) && (finalOutputStringToDraw.get(nbPoint).getY() < (j+1)*50)){
                                canvas.drawRect((i*50)+2,(j*50)+2, ((i+1)*50)-2, ((j+1)*50)-2, drawPaint);
                            }
                        }
                    }
                }
            }

        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(drawFromDatabase == false) {
            x = event.getX();
            y = event.getY();

            gridX.add(x);
            gridY.add(y);
            colorValue.add(colorPainting);
        }
        else{

        }

        invalidate();
        return true;
    }
}
