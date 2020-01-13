package com.example.ryan.waterusagediary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class diaryActivity extends AppCompatActivity{

    public  ArrayList<DiaryEntry> DiaryEnteries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        setUpEntry();

        Button main = (Button) findViewById(R.id.btnMain);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openMainWindow = new Intent(view.getContext(), MainActivity.class);
                startActivity(openMainWindow);

                Toast toast = Toast.makeText(getApplicationContext(),"Back to Main Screen", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        Button calc = (Button) findViewById(R.id.btnC);
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openCalc = new Intent(view.getContext(), calcActivity.class);
                startActivity(openCalc);

                Toast toast = Toast.makeText(getApplicationContext(),"Go to Calculator", Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        Button prev = (Button) findViewById(R.id.btnPrev);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast toast = Toast.makeText(getApplicationContext(),"Loading Previous", Toast.LENGTH_SHORT);
                toast.show();
            }
        });


        Button next = (Button) findViewById(R.id.btnNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast toast = Toast.makeText(getApplicationContext(),"Loading Next", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
/////////////////////////////////////////////////////////////////////
    private void setUpEntry() {
        Intent diaryIntent = getIntent();
        // gives which day was selected
        String item_selected = diaryIntent.getStringExtra("Item_Selected");
         LoadData();

        //array item to load into variables
        PopulateScreen(Integer.parseInt(item_selected));

    }

    private void PopulateScreen(int iPos) {
        TextView textv = (TextView) findViewById(R.id.lblDate);
        if (DiaryEnteries!=null) {
            DiaryEntry current = DiaryEnteries.get(0);
            textv.setText(current.date);
        }
        textv.setText("None");



    }

    public void LoadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken< ArrayList<DiaryEntry> >() {}.getType();
        DiaryEnteries = gson.fromJson(json, type);

//        if (DiaryEnteries == null){
//            Log.d("myPrint", "Null DiaryEntries");
//            DiaryEnteries = new ArrayList<>();
//        }


    }

    //@Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    //@Override
    protected void onResume(){
        super.onResume();
    }

}