package com.example.mycomputer.igotafriend.validetor.AccountInfoValider;

import com.example.mycomputer.igotafriend.AccountInfo;

/**
 * Created by My computer on 1/22/2018.
 */

public class PhotoValider implements IAccountInfoFieldValider {
    @Override
    public String validate(AccountInfo accountInfo) {

        if (accountInfo.getPhotoUri()==null||accountInfo.getPhotoUri().equals("")){
            return "Must Have A Picture To build A Profile";
        }
        return null;
    }
}
