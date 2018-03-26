package com.example.mycomputer.igotafriend.validetor.LocalValider;

import com.example.mycomputer.igotafriend.ProfileLocol;
import com.example.mycomputer.igotafriend.validetor.LocalValider.ILocalFiledValider;

/**
 * Created by My computer on 1/22/2018.
 */

public class InterestsLocalValider implements ILocalFiledValider {
    @Override
    public String validate(ProfileLocol profileLocol) {
       if (profileLocol.getListOfInterests().size()==0||profileLocol.getListOfInterests().isEmpty()){
           return "The List of Interest must be fill with One Intrest a lest";
       }
        return null;
    }
}
