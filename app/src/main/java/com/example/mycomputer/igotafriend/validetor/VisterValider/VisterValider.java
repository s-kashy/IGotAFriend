package com.example.mycomputer.igotafriend.validetor.VisterValider;

import com.example.mycomputer.igotafriend.SearchPreferernce;

import java.util.Arrays;
import java.util.List;

/**
 * Created by My computer on 1/22/2018.
 */

public class VisterValider implements IVisterValider {
    private List<IFieldVisterValider> iFieldVisterValiders;

    public VisterValider() {
        iFieldVisterValiders = Arrays.asList(new IntrerestValider()//
                , new CityValider()//
                , new EndDateValider()//
                , new StartDateValider()//
        );
    }

    @Override
    public String validate(SearchPreferernce searchPreferernce) {

        for (IFieldVisterValider visterValider : iFieldVisterValiders) {
            String res = visterValider.validate(searchPreferernce);
            if (res != null) {
                return res;
            }
        }
        return null;
    }
}
