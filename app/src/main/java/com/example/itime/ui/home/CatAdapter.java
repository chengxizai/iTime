package com.example.itime.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.itime.R;
import com.example.itime.TimeActivity;

import java.util.List;

public class CatAdapter extends ArrayAdapter<Cat> {

    private int resourceId;

    public CatAdapter(Context context, int resource, List<Cat> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cat cat = getItem(position);//获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ((ImageView) view.findViewById(R.id.image_0)).setImageResource(cat.getImageId());
        ((TextView) view.findViewById(R.id.name_0)).setText(cat.getName());

       // Intent intent=new Intent(getContext(), TimeActivity.class);
       // String i=String.valueOf(position);
      //  intent.putExtra("delete_position",i);
        return view;
    }
}