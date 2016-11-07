package com.example.dragos.basketballdragcode;


public class DayDetails {

    private String dayOfTheWeek;
    private String price;
    private String location;
    private String time;

    public DayDetails() {}

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getDayOfTheWeek() {return dayOfTheWeek;}
    public void setDayOfTheWeek(String dayOfTheWeek) {this.dayOfTheWeek = dayOfTheWeek;}

}
