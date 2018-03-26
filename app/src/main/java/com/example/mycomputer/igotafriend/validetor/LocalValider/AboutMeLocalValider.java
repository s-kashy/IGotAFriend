package com.example.mycomputer.igotafriend.validetor.LocalValider;

import com.example.mycomputer.igotafriend.ProfileLocol;

/**
 * Created by My computer on 1/22/2018.
 */

public class AboutMeLocalValider implements ILocalFiledValider {
    @Override
    public String validate(ProfileLocol profileLocol) {
        String[] words = profileLocol.getBasicInfo().split("\\s+");
        if (profileLocol.getWhatDoIOffer() == null || profileLocol.getBasicInfo().equals("")||words.length<1) {
            return "The Edit Text Of basic info Must be 10 words";
        }

        return null;
    }
}
