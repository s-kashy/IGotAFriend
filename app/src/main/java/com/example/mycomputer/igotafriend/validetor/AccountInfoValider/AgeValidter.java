package com.example.mycomputer.igotafriend.validetor.AccountInfoValider;

import com.example.mycomputer.igotafriend.AccountInfo;

/**
 * Created by My computer on 1/22/2018.
 */

public class AgeValidter implements IAccountInfoFieldValider {
    @Override
    public String validate(AccountInfo accountInfo) {

        if (accountInfo.getAge().equals("")||Integer.parseInt(accountInfo.getAge().toString())<=0){
            return "Age must be filled ";
        }
        return null;
    }
}
