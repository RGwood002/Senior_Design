package com.example.server_test;

public class food_item {
    private String food;
    private String date;

    public food_item(String food, String date){
        this.food = food;
        this.date = date;

    }

    public String getFood(){
        return food;
    }

    public String getDate(){
        return date;
    }


}
