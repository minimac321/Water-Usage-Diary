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

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class diaryActivity extends AppCompatActivity{

    public  ArrayList<DiaryEntry> DiaryEnteries;

    private float avgWater;
    private float totalWater;
    private int item_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        setUpEntry();

        setAverage();

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

                if (item_selected == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Cannot load Previous", Toast.LENGTH_SHORT);
                    toast.show();
                }else if(item_selected <= DiaryEnteries.size()){
                    PopulateScreen(--item_selected);
                }



            }
        });


        Button next = (Button) findViewById(R.id.btnNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (item_selected == DiaryEnteries.size()-1) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Cannot load Next", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    PopulateScreen(++item_selected);
                }
            }
        });

    }
/////////////////////////////////////////////////////////////////////
    private void setUpEntry() {
        Intent diaryIntent = getIntent();
        // gives which day was selected
        item_selected = diaryIntent.getIntExtra("Item_Selected", 0);

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
            a1.setText("Total: " + String.valueOf(current.shower) + " Litres");

            TextView a2 = (TextView) findViewById(R.id.edtAmount2);
            a2.setText("Total: " + String.valueOf(current.toilet) + " Litres");

            TextView a3 = (TextView) findViewById(R.id.edtAmount3);
            a3.setText("Total: " + String.valueOf(current.hygiene) + " Litres");

            TextView a4 = (TextView) findViewById(R.id.edtAmount4);
            a4.setText("Total: " + String.valueOf(current.laundry) + " Litres");

            TextView a5 = (TextView) findViewById(R.id.edtAmount5);
            a5.setText("Total: " + String.valueOf(current.dishes) + " Litres");

            TextView a6 = (TextView) findViewById(R.id.edtAmount6);
            a6.setText("Total: " + String.valueOf(current.drinking) + " Litres");

            TextView a7 = (TextView) findViewById(R.id.edtAmount7);
            a7.setText("Total: " + String.valueOf(current.cooking) + " Litres");

            TextView a8 = (TextView) findViewById(R.id.edtAmount8);
            a8.setText("Total: " + String.valueOf(current.cleaning) + " Litres");

            TextView a9 = (TextView) findViewById(R.id.edtAmount9);
            a9.setText("Total: " + String.valueOf(current.other) + " Litres");

            TextView total = (TextView) findViewById(R.id.diaryCurrTotal);
            total.setText("Total: "+ String.valueOf(current.Total)+" Litres");

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

    public void setAverage(){
        avgWater = 0;
        totalWater = 0;

        for (int i =0; i<DiaryEnteries.size(); i++){
            avgWater += DiaryEnteries.get(i).Total;
        }
        totalWater = avgWater;
        avgWater = avgWater / DiaryEnteries.size();

        TextView lblAvg = (TextView)findViewById(R.id.lblRunningAvg);
        String sAvg = String.format("%.2f", avgWater);

        lblAvg.setText("Average Consumption\n" + sAvg + "L");
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
