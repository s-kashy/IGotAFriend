package com.example.mycomputer.igotafriend.validetor.AccountInfoValider;

import com.example.mycomputer.igotafriend.AccountInfo;

import org.w3c.dom.ls.LSException;

import java.util.Arrays;
import java.util.List;

/**
 * Created by My computer on 1/22/2018.
 */

public class ValiderAccountInfo implements IAccountInfoValidter {
    private List<IAccountInfoFieldValider> iAccountInfoFieldValiders;

    public ValiderAccountInfo() {
        iAccountInfoFieldValiders = Arrays.asList(new AgeValidter()//
                , new CityValider()//
                 ,new PhotoValider()
                , new GenderValider());//
//                , new NameValider());

    }

    @Override
    public String validate(AccountInfo accountInfo) {

        for (IAccountInfoFieldValider iAccount : iAccountInfoFieldValiders) {
            String res = iAccount.validate(accountInfo);
            if (res != null) {
                return res;
            }
        }
        return null;
    }
}
