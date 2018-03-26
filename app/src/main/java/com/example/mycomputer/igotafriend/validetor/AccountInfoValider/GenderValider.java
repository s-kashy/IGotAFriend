package com.example.mycomputer.igotafriend.validetor.AccountInfoValider;

import com.example.mycomputer.igotafriend.AccountInfo;
import com.example.mycomputer.igotafriend.Gender;

/**
 * Created by My computer on 1/22/2018.
 */

public class GenderValider implements IAccountInfoFieldValider {
    @Override
    public String validate(AccountInfo accountInfo) {
        if (accountInfo.getType().equals("")||accountInfo.getType() == Gender.UNDEFINE ) {
            return "Please Choose A Gender";
        }
        return null;
    }
}
