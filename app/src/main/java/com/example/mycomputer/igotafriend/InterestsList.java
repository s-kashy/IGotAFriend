package com.example.mycomputer.igotafriend;

import java.util.ArrayList;

/**
 * Created by My computer on 1/11/2018.
 */

public class InterestsList {

    public static ArrayList<Interests> interestsList = null;

    public static ArrayList<Interests> getAllInterests() {
        if (interestsList == null) {
            interestsList = new ArrayList<>();
            for (Interests interests : Interests.values()) {
                interestsList.add(interests);
            }

        }
        return interestsList;
    }
}