package com.example.todo;

/**
 * Created by hamza on 2017-12-26.
 */

public class listItem {
    private String item;
    private String user;

    public listItem(){
        //empty constructor required for firebase
    }

    public listItem(String item, String user){
        this.item = item;
        this.user = user;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }



}
