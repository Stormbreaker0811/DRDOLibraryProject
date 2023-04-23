package com.example.drdolibraryproject.gettersetter;

public class Canteens {
    private String uri;
    private String name;
    private String month;
    public Canteens(){
        //Constructor needed do not delete..
    }

    public Canteens(String uri, String name, String month){
        this.uri = uri;
        this.name = name;
        this.month = month;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
