package com.example.itime.ui.home;

import android.content.Context;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class CatSaver {
    Context context;
    ArrayList<Cat> cats=new ArrayList<>();

    public CatSaver(Context context) {
        this.context = context;
    }

    public ArrayList<Cat> getCats() {
        return cats;
    }

    public void save()
    {
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(context.openFileOutput("Serializable.txt",Context.MODE_PRIVATE));
            outputStream.writeObject(cats);
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Cat> load(){
        try{
            ObjectInputStream inputStream = new ObjectInputStream(context.openFileInput("Serializable.txt"));
            cats = (ArrayList<Cat>) inputStream.readObject();
            inputStream.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return cats;
    }
}
