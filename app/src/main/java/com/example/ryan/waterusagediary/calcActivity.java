package com.example.ryan.waterusagediary;


import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class calcActivity extends AppCompatActivity{
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private  TextView tDate;
    private  ArrayList<DiaryEntry> DiaryEnteries;


    public static ArrayList<String> DE_Dates = new ArrayList<String>();
    public static ArrayList<String> Totals = new ArrayList<String>();
    //public static ArrayList<String> wholeEntry = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        DateFormat dateF = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        final String sDate = dateF.format(date);

        tDate = findViewById(R.id.date_display);
        tDate.setText(sDate);

        ////////Json file stuff
        LoadData();
        ///////////////

        ////////////// initial global variables



        //////////////////coding plus and minus buttons

        final Button P1 = findViewById(R.id.btnPlus);
        P1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView edt = (TextView) findViewById(R.id.edtAmount);
                int iCurr = Integer.parseInt(edt.getText().toString());
                iCurr++;


                edt.setText(String.valueOf(iCurr));
                UpdatecalcCurrTotal();


            }
        });

        final Button M1 = findViewById(R.id.btnMinus);
        M1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView edt = findViewById(R.id.edtAmount);
                int iCurr = Integer.parseInt(edt.getText().toString());
                iCurr--;
                if (iCurr<0){
                    iCurr = 0;
                }

                edt.setText(String.valueOf(iCurr));
                UpdatecalcCurrTotal();


            }
        });



        ///////////////////>


        Button exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openMainWindow = new Intent(calcActivity.this, MainActivity.class);
                startActivity(openMainWindow);

                Toast toast = Toast.makeText(getApplicationContext(),"Back to Main Screen", Toast.LENGTH_SHORT);
                toast.show();
            }
        });



        Button save = (Button) findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView t1 = findViewById(R.id.edtAmount);
                int shower = Integer.parseInt(t1.getText().toString());

                TextView t2 = findViewById(R.id.edtAmount2);
                int toilet = Integer.parseInt(t2.getText().toString());

                TextView t3 = findViewById(R.id.edtAmount3);
                int hygiene = Integer.parseInt(t3.getText().toString());

                TextView t4 = findViewById(R.id.edtAmount4);
                int laundry = Integer.parseInt(t4.getText().toString());

                TextView t5 = findViewById(R.id.edtAmount5);
                int dishes = Integer.parseInt(t5.getText().toString());

                TextView t6 = findViewById(R.id.edtAmount6);
                int drinking = Integer.parseInt(t6.getText().toString());

                TextView t7 = findViewById(R.id.edtAmount7);
                int cooking = Integer.parseInt(t7.getText().toString());

                TextView t8 = findViewById(R.id.edtAmount8);
                int cleaning = Integer.parseInt(t8.getText().toString());

                TextView t9 = findViewById(R.id.edtAmount9);
                int other = Integer.parseInt(t9.getText().toString());



                // assign variables to array and save to csv/.txt
                DiaryEnteries = new ArrayList<>();
                DiaryEntry tmp = new DiaryEntry(sDate,shower,toilet,hygiene,laundry,dishes,drinking,cooking,cleaning,other);
                DiaryEnteries.add(tmp);


                //DE_Dates.add("2018");
                int iTotal = shower+toilet+hygiene+laundry+dishes+drinking+cooking+cleaning+other;
                Totals.add(String.valueOf(iTotal));

                SaveData();



                Toast toast = Toast.makeText(getApplicationContext(),"Saved", Toast.LENGTH_SHORT);
                toast.show();

                Intent openDiaryPage = new Intent(calcActivity.this, diaryActivity.class);
                startActivity(openDiaryPage);
            }
        });





        //Dialog datePickerDialog = new DatePickerDialog(getApplicationContext(), calcActivity.this, 1, 1, 1);

        Button date_pick = (Button) findViewById(R.id.date_pick);

        date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar c = Calendar.getInstance();
                int cYear = c.get(Calendar.YEAR);
                int cMonth = c.get(Calendar.MONTH);
                int cDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialogue = new DatePickerDialog(calcActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, cYear, cMonth, cDay);

                dialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogue.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                String Sdate = day+"/"+month+"/"+year;
                tDate.setText(Sdate);

            }
        };


    }


    public void UpdatecalcCurrTotal(){
        TextView ed = (TextView) findViewById(R.id.edtAmount);
        int iCurr = Integer.parseInt(ed.getText().toString());

        TextView running = (TextView) findViewById(R.id.calcCurrTotal);

        running.setText(String.valueOf(iCurr));

    }

    public void SaveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(DiaryEnteries);
        editor.putString("task list", json);
        editor.apply();

    }

    public void LoadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        /*Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<DiaryEntry>>() {}.getType();
        DiaryEnteries = gson.fromJson(json, type);
        */
        if (DiaryEnteries==null){
            DiaryEnteries = new ArrayList<>();
        }



    }

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
