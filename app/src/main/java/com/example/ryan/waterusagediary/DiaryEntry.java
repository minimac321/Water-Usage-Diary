package com.example.ryan.waterusagediary;

import android.support.annotation.NonNull;

import java.util.Comparator;

public class DiaryEntry implements Comparable<DiaryEntry> {
    public String date;
    public float Total;
    public int shower;
    public int toilet;
    public int hygiene;
    public int laundry;
    public int dishes;
    public int drinking;
    public int cooking;
    public int cleaning;
    public int other;




    public DiaryEntry(String date, int shower, int toilet, int hygiene, int laundry, int dishes,
                      int drinking, int cooking, int cleaning, int other){
        this.date = date;
        this.shower = shower;
        this.toilet = toilet;
        this.hygiene = hygiene;
        this.laundry = laundry;
        this.dishes = dishes;
        this.drinking = drinking;
        this.cooking = cooking;
        this.cleaning = cleaning;
        this.other = other;
        this.Total = shower+toilet+hygiene+laundry+dishes+drinking+cooking+cleaning;

    }

    public String toString(){
        return ("Date: "+date+"/nTotal water used in day: "+Total+" litres");
    }

    public String getDate(){
        return this.date;
    }


    //To Sort Diary Entries but doesnt work right, entries with "08/10/20" vs "4/10/20"
    @Override
    public int compareTo(@NonNull DiaryEntry o) {
        return this.date.compareTo(o.getDate());
    }
}
