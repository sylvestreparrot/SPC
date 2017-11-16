package com.adafruit.bluefruit.le.connect.app.Draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adafruit.bluefruit.le.connect.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    ArrayList<String> result;
    Context context;
    ArrayList<Bitmap> imageId;

    private static LayoutInflater inflater=null;

    public CustomAdapter(DrawingList mainActivity, ArrayList<String> nom, ArrayList<Bitmap> image) {
        // TODO Auto-generated constructor stub
        result=nom;
        context=mainActivity;
        imageId=image;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.program_list, null);
        holder.tv=(TextView) rowView.findViewById(R.id.textView1);
        holder.img=(ImageView) rowView.findViewById(R.id.imageView1);
        holder.tv.setText(result.get(position));
        holder.img.setImageBitmap(imageId.get(position));
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked "+result.get(position), Toast.LENGTH_LONG).show();
                DrawingList.showChoosenDrawingFromDatabase(position);
            }
        });
        return rowView;
    }

}