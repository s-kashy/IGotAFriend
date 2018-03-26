package com.example.mycomputer.igotafriend.validetor.LocalValider;

import com.example.mycomputer.igotafriend.ProfileLocol;
import com.example.mycomputer.igotafriend.validetor.LocalValider.ILocalFiledValider;

/**
 * Created by My computer on 1/22/2018.
 */

public class WhatDoIOfferLocalValider implements ILocalFiledValider {
    @Override
    public String validate(ProfileLocol profileLocol) {
        String[] words = profileLocol.getWhatDoIOffer().split("\\s+");
        if (profileLocol.getWhatDoIOffer() == null || profileLocol.getWhatDoIOffer().equals("")||words.length<1) {
            return "The Edit Text Of What do i Ofer Must be 10 words";
        }

        return null;
    }
}
