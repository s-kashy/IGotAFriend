package com.example.mycomputer.igotafriend;

import com.example.mycomputer.igotafriend.activities.ShowLocalProfileActivty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by My computer on 1/17/2018.
 */

public class SearchPreferernce implements Serializable{
    private List<Interests> listOfInterests;
    private String startDate;
    private String endDate;
    private String city;
    //in the page of the serch
    public SearchPreferernce(){
        listOfInterests=new ArrayList<>();
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getCity() {
        return city;
    }

    public void setStartDate(String startDate) {

        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setCity(String city) {
        this.city = city;
    }



    public void addInterests(Interests interests){
        listOfInterests.add(interests);

    }

    public List<Interests> getListOfInterests() {
        return listOfInterests;
    }

    public void setListOfInterests(List<Interests> listOfInterests) {
        this.listOfInterests = listOfInterests;
    }
    public void clearInterset(){
        listOfInterests.clear();
    }
}