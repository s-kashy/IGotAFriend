package com.example.mycomputer.igotafriend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by My computer on 1/9/2018.
 */

public class ProfileLocol implements Serializable {

    private float rate;
    private String basicInfo;
    private int numCritics = 0;
    private String whatDoIOffer;
    private List<Interests> listOfInterests;

    public ProfileLocol() {
        listOfInterests = new ArrayList<>();
    }

    public List<Interests> getListOfInterests() {
        return listOfInterests;
    }

    public void setListOfInterests(List<Interests> listOfInterests) {
        this.listOfInterests = listOfInterests;
    }

    public void setBasicInfo(String basicInfo) {
        this.basicInfo = basicInfo;
    }

    public void setWhatDoIOffer(String whatDoIOffer) {
        this.whatDoIOffer = whatDoIOffer;
    }

    public String getBasicInfo() {

        return basicInfo;
    }

    public String getWhatDoIOffer() {
        return whatDoIOffer;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public void setNumCritics(int numCritics) {
        this.numCritics += numCritics;
    }

    public float getRate() {
        return rate;
    }

    public int getNumCritics() {
        return numCritics;
    }

    public void clearIntrest() {
        listOfInterests.clear();
    }

    public void addIntrest(Interests interests) {
        listOfInterests.add(interests);
    }

    public void addRate(float rate) {
        this.rate += rate;
        this.numCritics += 1;
    }

}

