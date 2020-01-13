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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class calcActivity extends AppCompatActivity{
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private  TextView tDate;
    private  ArrayList<DiaryEntry> DiaryEnteries;

    private float avgWater;
    private float totalWater;


    public static ArrayList<String> DE_Dates = new ArrayList<String>();
    public static ArrayList<String> Totals = new ArrayList<String>();
    //public static ArrayList<String> wholeEntry = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        DateFormat dateF = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        final String[] sDate = {dateF.format(date)};

        tDate = findViewById(R.id.date_display);
        tDate.setText(sDate[0]);

        ////////Json file stuff
        LoadData();

        setAverage();






        Button exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openMainWindow = new Intent(view.getContext(), MainActivity.class);
                startActivity(openMainWindow);

                Toast toast = Toast.makeText(getApplicationContext(),"Back to Main Screen", Toast.LENGTH_SHORT);
                toast.show();
            }
        });



        Button save = (Button) findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Date = tDate.getText().toString();

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
                DiaryEntry tmp = new DiaryEntry(Date,shower,toilet,hygiene,laundry,dishes,drinking,cooking,cleaning,other);
                DiaryEnteries.add(tmp);


                //DE_Dates.add("2018");
                int iTotal = shower+toilet+hygiene+laundry+dishes+drinking+cooking+cleaning+other;
                Totals.add(String.valueOf(iTotal));

                SaveData();



                Toast toast = Toast.makeText(getApplicationContext(),"Saved at position: "+ String.valueOf(DiaryEnteries.size()-1), Toast.LENGTH_SHORT);
                toast.show();

                Intent openDiaryPage = new Intent(view.getContext(), diaryActivity.class);
                openDiaryPage.putExtra("Item_Selected", DiaryEnteries.size()-1);

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

        final Button P2 = findViewById(R.id.btnPlus2);
        P2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView edt = (TextView) findViewById(R.id.edtAmount2);
                int iCurr = Integer.parseInt(edt.getText().toString());
                iCurr++;

                edt.setText(String.valueOf(iCurr));
                UpdatecalcCurrTotal();
            }
        });

        final Button M2 = findViewById(R.id.btnMinus2);
        M2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView edt = findViewById(R.id.edtAmount2);
                int iCurr = Integer.parseInt(edt.getText().toString());
                iCurr--;
                if (iCurr<0){
                    iCurr = 0;
                }

                edt.setText(String.valueOf(iCurr));
                UpdatecalcCurrTotal();
            }
        });

        final Button P3 = findViewById(R.id.btnPlus3);
        P3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView edt = (TextView) findViewById(R.id.edtAmount3);
                int iCurr = Integer.parseInt(edt.getText().toString());
                iCurr++;

                edt.setText(String.valueOf(iCurr));
                UpdatecalcCurrTotal();
            }
        });

        final Button M3 = findViewById(R.id.btnMinus3);
        M3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView edt = findViewById(R.id.edtAmount3);
                int iCurr = Integer.parseInt(edt.getText().toString());
                iCurr--;
                if (iCurr<0){
                    iCurr = 0;
                }

                edt.setText(String.valueOf(iCurr));
                UpdatecalcCurrTotal();
            }
        });

        final Button P4 = findViewById(R.id.btnPlus4);
        P4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView edt = (TextView) findViewById(R.id.edtAmount4);
                int iCurr = Integer.parseInt(edt.getText().toString());
                iCurr++;

                edt.setText(String.valueOf(iCurr));
                UpdatecalcCurrTotal();
            }
        });

        final Button M4 = findViewById(R.id.btnMinus4);
        M4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView edt = findViewById(R.id.edtAmount4);
                int iCurr = Integer.parseInt(edt.getText().toString());
                iCurr--;
                if (iCurr<0){
                    iCurr = 0;
                }

                edt.setText(String.valueOf(iCurr));
                UpdatecalcCurrTotal();
            }
        });

        final Button P5 = findViewById(R.id.btnPlus5);
        P5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView edt = (TextView) findViewById(R.id.edtAmount5);
                int iCurr = Integer.parseInt(edt.getText().toString());
                iCurr++;

                edt.setText(String.valueOf(iCurr));
                UpdatecalcCurrTotal();
            }
        });

        final Button M5 = findViewById(R.id.btnMinus5);
        M5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView edt = findViewById(R.id.edtAmount5);
                int iCurr = Integer.parseInt(edt.getText().toString());
                iCurr--;
                if (iCurr<0){
                    iCurr = 0;
                }

                edt.setText(String.valueOf(iCurr));
                UpdatecalcCurrTotal();
            }
        });

        final Button P6 = findViewById(R.id.btnPlus6);
        P6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView edt = (TextView) findViewById(R.id.edtAmount6);
                int iCurr = Integer.parseInt(edt.getText().toString());
                iCurr++;

                edt.setText(String.valueOf(iCurr));
                UpdatecalcCurrTotal();
            }
        });

        final Button M6 = findViewById(R.id.btnMinus6);
        M6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView edt = findViewById(R.id.edtAmount6);
                int iCurr = Integer.parseInt(edt.getText().toString());
                iCurr--;
                if (iCurr<0){
                    iCurr = 0;
                }

                edt.setText(String.valueOf(iCurr));
                UpdatecalcCurrTotal();
            }
        });

        final Button P7 = findViewById(R.id.btnPlus7);
        P7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView edt = (TextView) findViewById(R.id.edtAmount7);
                int iCurr = Integer.parseInt(edt.getText().toString());
                iCurr++;

                edt.setText(String.valueOf(iCurr));
                UpdatecalcCurrTotal();
            }
        });

        final Button M7 = findViewById(R.id.btnMinus7);
        M7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView edt = findViewById(R.id.edtAmount7);
                int iCurr = Integer.parseInt(edt.getText().toString());
                iCurr--;
                if (iCurr<0){
                    iCurr = 0;
                }

                edt.setText(String.valueOf(iCurr));
                UpdatecalcCurrTotal();
            }
        });

        final Button P8 = findViewById(R.id.btnPlus8);
        P8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView edt = (TextView) findViewById(R.id.edtAmount8);
                int iCurr = Integer.parseInt(edt.getText().toString());
                iCurr++;

                edt.setText(String.valueOf(iCurr));
                UpdatecalcCurrTotal();
            }
        });

        final Button M8 = findViewById(R.id.btnMinus8);
        M8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView edt = findViewById(R.id.edtAmount8);
                int iCurr = Integer.parseInt(edt.getText().toString());
                iCurr--;
                if (iCurr<0){
                    iCurr = 0;
                }

                edt.setText(String.valueOf(iCurr));
                UpdatecalcCurrTotal();
            }
        });

        final Button P9 = findViewById(R.id.btnPlus9);
        P9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView edt = (TextView) findViewById(R.id.edtAmount9);
                int iCurr = Integer.parseInt(edt.getText().toString());
                iCurr++;

                edt.setText(String.valueOf(iCurr));
                UpdatecalcCurrTotal();
            }
        });

        final Button M9 = findViewById(R.id.btnMinus9);
        M9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView edt = findViewById(R.id.edtAmount9);
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





    }

    public void setAverage(){
        avgWater = 0;
        totalWater = 0;

        for (int i =0; i<DiaryEnteries.size(); i++){
            avgWater += DiaryEnteries.get(i).Total;
        }
        totalWater = avgWater;
        avgWater = avgWater / DiaryEnteries.size();

        TextView lblAvg = (TextView)findViewById(R.id.runningAVG);
        String sAvg = String.format("%.2f", avgWater);

        lblAvg.setText("Average Consumption\n" + sAvg + "L");
    }

    public void UpdatecalcCurrTotal(){
        int iCurr = 0;

        TextView tv = (TextView) findViewById(R.id.edtAmount);
        iCurr += Integer.parseInt(tv.getText().toString());

        tv = (TextView) findViewById(R.id.edtAmount2);
        iCurr += Integer.parseInt(tv.getText().toString());

        tv = (TextView) findViewById(R.id.edtAmount3);
        iCurr += Integer.parseInt(tv.getText().toString());

        tv = (TextView) findViewById(R.id.edtAmount4);
        iCurr += Integer.parseInt(tv.getText().toString());

        tv = (TextView) findViewById(R.id.edtAmount5);
        iCurr += Integer.parseInt(tv.getText().toString());

        tv = (TextView) findViewById(R.id.edtAmount6);
        iCurr += Integer.parseInt(tv.getText().toString());

        tv = (TextView) findViewById(R.id.edtAmount7);
        iCurr += Integer.parseInt(tv.getText().toString());

        tv = (TextView) findViewById(R.id.edtAmount8);
        iCurr += Integer.parseInt(tv.getText().toString());

        tv = (TextView) findViewById(R.id.edtAmount9);
        iCurr += Integer.parseInt(tv.getText().toString());

        TextView running = (TextView) findViewById(R.id.calcCurrTotal);

        running.setText("Running Total: "+ String.valueOf(iCurr)+ "L");

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
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<DiaryEntry>>() {}.getType();
        DiaryEnteries = gson.fromJson(json, type);

        if (DiaryEnteries==null){
            DiaryEnteries = new ArrayList<>();
            Toast toast = Toast.makeText(getApplicationContext(),"Diary Entries null", Toast.LENGTH_SHORT);
            toast.show();
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
