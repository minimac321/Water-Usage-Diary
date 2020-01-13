package com.example.ryan.waterusagediary;

public class DiaryEntry {
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
}
