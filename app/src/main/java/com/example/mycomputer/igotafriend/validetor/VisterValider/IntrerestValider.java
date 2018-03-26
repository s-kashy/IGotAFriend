package com.example.mycomputer.igotafriend.validetor.VisterValider;

import com.example.mycomputer.igotafriend.SearchPreferernce;

/**
 * Created by My computer on 1/22/2018.
 */

public class IntrerestValider implements IFieldVisterValider {
    @Override
    public String validate(SearchPreferernce searchPreferernce) {
        if (searchPreferernce.getListOfInterests().size()==0){
            return "Must Choose One Intrest At List!!";
        }
        return null;
    }
}
