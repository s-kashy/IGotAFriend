package com.example.mycomputer.igotafriend.validetor.VisterValider;

import com.example.mycomputer.igotafriend.SearchPreferernce;

/**
 * Created by My computer on 1/22/2018.
 */

public class StartDateValider implements IFieldVisterValider {
    @Override
    public String validate(SearchPreferernce searchPreferernce) {
       if (searchPreferernce.getStartDate()==null||searchPreferernce.equals("")){
           return "A Starting Date Must Be Submit";
       }
       return null;
    }
}
