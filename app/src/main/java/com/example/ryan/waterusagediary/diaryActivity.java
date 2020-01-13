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
                SaveData();

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
                SaveData();

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
        int item_selected = diaryIntent.getIntExtra("Item_Selected", 0);

        Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(item_selected), Toast.LENGTH_SHORT);
        toast.show();

        LoadData();

        toast = Toast.makeText(getApplicationContext(), "Size of Diary Entry: "+ String.valueOf(DiaryEnteries.size()), Toast.LENGTH_SHORT);
        toast.show();

        //array item to load into variables
        PopulateScreen(item_selected);

    }

    public void LoadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken< ArrayList<DiaryEntry> >() {}.getType();
        DiaryEnteries = gson.fromJson(json, type);


        if (DiaryEnteries == null){
            Toast toast = Toast.makeText(getApplicationContext(), "Diary Entries array is null", Toast.LENGTH_SHORT);
            toast.show();
            DiaryEnteries = new ArrayList<>();
        }


    }

    private void PopulateScreen(int iPos) {

        if (DiaryEnteries!=null) {
            DiaryEntry current = DiaryEnteries.get(iPos);

            TextView date = (TextView) findViewById(R.id.lblDate);
            date.setText(current.date);

            TextView a1 = (TextView) findViewById(R.id.edtAmount);
            a1.setText(String.valueOf(current.shower));

            TextView a2 = (TextView) findViewById(R.id.edtAmount2);
            a2.setText(String.valueOf(current.toilet));

            TextView total = (TextView) findViewById(R.id.diaryCurrTotal);
            total.setText("Running Total: "+ String.valueOf(current.Total)+"L");

        }


    }

    public void SaveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(DiaryEnteries);
        editor.putString("task list", json);
        editor.apply();
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
