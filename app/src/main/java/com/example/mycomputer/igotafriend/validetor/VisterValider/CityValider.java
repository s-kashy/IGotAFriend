package com.example.mycomputer.igotafriend.validetor.VisterValider;

import com.example.mycomputer.igotafriend.SearchPreferernce;

/**
 * Created by My computer on 1/22/2018.
 */

public class CityValider implements IFieldVisterValider {
    @Override
    public String validate(SearchPreferernce searchPreferernce) {
        if (searchPreferernce.getCity()==null||searchPreferernce.getCity().equals("")){
            return "A City Must Be Submit";
        }
        return null;
    }
}
