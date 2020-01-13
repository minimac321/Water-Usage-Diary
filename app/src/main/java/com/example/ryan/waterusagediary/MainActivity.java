package com.example.ryan.waterusagediary;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    private  ArrayList<DiaryEntry> DiaryEnteries;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lv = (ListView) findViewById(R.id.main_lv);
        avg = findViewById(R.id.lblAvg);

        //Intent openSecondWindow;

        //Intent open_DiaryScreen;

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






        lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                Toast toast = Toast.makeText(getApplicationContext(), "Selected Item", Toast.LENGTH_SHORT);
                toast.show();

                Intent open_diaryAct = new Intent(MainActivity.this, diaryActivity.class);
                // sends info to other intent to be recieved
                open_diaryAct.putExtra("Item_Selected", position);
                startActivity(open_diaryAct);



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //
            }
        });





    }

    public void PopulateLV(){

//        //String[] myEntries = new String[DiaryEnteries.size()];
//        /*for (int i =0; i<DiaryEnteries.size(); i++){
//            myEntries[i] = DiaryEnteries.get(i).toString();
//        }*/

        String[] myEntries = new String[3];
        myEntries[0] = "2018/06/10";
        myEntries[1] = "2017/03/04";
        myEntries[2] = "2015/01/01";


        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, calcActivity.DE_Dates);
        lv.setAdapter(myAdapter);

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

//    public void setAverage(){
//        //
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
