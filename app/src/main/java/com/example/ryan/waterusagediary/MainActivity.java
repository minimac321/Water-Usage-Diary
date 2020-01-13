package com.example.ryan.waterusagediary;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private TextView avg;
    private String[] myEntries;


    private  ArrayList<DiaryEntry> DiaryEnteries;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lv = (ListView) findViewById(R.id.main_lv);
        avg = findViewById(R.id.lblAvg);


        Button nextWinButton = (Button) findViewById(R.id.btncalc);
        nextWinButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent openSecondWindow = new Intent(view.getContext(), calcActivity.class);
                startActivity(openSecondWindow);

                Toast toast = Toast.makeText(getApplicationContext(),"Calculator", Toast.LENGTH_SHORT);
                toast.show();

            }

        });

        LoadData();
        PopulateLV();

        Toast toast = Toast.makeText(getApplicationContext(), "Length of myEntries " + String.valueOf(myEntries.length), Toast.LENGTH_SHORT);
        toast.show();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Toast toast = Toast.makeText(getApplicationContext(), myEntries[position], Toast.LENGTH_SHORT);
                toast.show();

                Intent open_diaryAct = new Intent(view.getContext(), diaryActivity.class);
                // sends info to other intent to be recieved
                open_diaryAct.putExtra("Item_Selected", position);
                startActivity(open_diaryAct);


            }
        });

    }

    public void LoadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<DiaryEntry>>() {}.getType();
        DiaryEnteries = gson.fromJson(json, type);


        if (DiaryEnteries==null){
            DiaryEnteries = new ArrayList<>();
        }

    }

    public void PopulateLV(){
        if (DiaryEnteries==null) {
            DiaryEntry tmp = new DiaryEntry("06/10/2018", 1, 1, 0, 3, 0, 0, 0, 0, 1);
            DiaryEnteries.add(tmp);
            tmp = new DiaryEntry("03/11/2017", 2, 2, 0, 0, 2, 0, 0, 0, 2);
            DiaryEnteries.add(tmp);
            tmp = new DiaryEntry("28/01/2015", 3, 3, 0, 0, 1, 0, 0, 0, 3);
            DiaryEnteries.add(tmp);
        }


        myEntries = new String[DiaryEnteries.size()];
        for (int i =0; i<DiaryEnteries.size(); i++){
            myEntries[i] = DiaryEnteries.get(i).date;
        }

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, myEntries);
        lv.setAdapter(myAdapter);

        SaveData();

    }

    public void SaveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(DiaryEnteries);
        editor.putString("task list", json);
        editor.apply();

    }

//    public void setAverage(){
//        avg.setText("0");
//    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }


}
