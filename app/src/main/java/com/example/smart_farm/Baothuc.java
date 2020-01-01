package com.example.smart_farm;

public class Baothuc {
    public String Year,Month,Day,Hour,Minute,StsBaothuc;
    public Baothuc(String year, String month, String day, String hour, String minute,String stsBaothuc) {
        Year = year;
        Month = month;
        Day = day;
        Hour = hour;
        Minute = minute;
        StsBaothuc=stsBaothuc;
    }

    public String getStsBaothuc() {
        return StsBaothuc;
    }

    public void setStsBaothuc(String stsBaothuc) {
        StsBaothuc = stsBaothuc;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public String getMinute() {
        return Minute;
    }

    public void setMinute(String minute) {
        Minute = minute;
    }
}
