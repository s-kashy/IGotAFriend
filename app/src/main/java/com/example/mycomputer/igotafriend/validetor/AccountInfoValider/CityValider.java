package com.example.mycomputer.igotafriend.validetor.AccountInfoValider;

import com.example.mycomputer.igotafriend.AccountInfo;

/**
 * Created by My computer on 1/22/2018.
 */

public class CityValider implements IAccountInfoFieldValider {
    @Override
    public String validate(AccountInfo accountInfo) {
       if (








               accountInfo.getOriginCity()==null||accountInfo.getOriginCity().equals("")){
           return "City Must Be Choosen ";
       }
       return null;
    }
}
