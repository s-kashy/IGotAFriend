package com.example.mycomputer.igotafriend.validetor.VisterValider;

import com.example.mycomputer.igotafriend.SearchPreferernce;

/**
 * Created by My computer on 1/22/2018.
 */

public class EndDateValider implements IFieldVisterValider {
    @Override
    public String validate(SearchPreferernce searchPreferernce) {
        if (searchPreferernce.getEndDate()==null||searchPreferernce.getEndDate().equals("")){
            return "A End Date Must Be Submit";
        }
        return null;
    }
}
