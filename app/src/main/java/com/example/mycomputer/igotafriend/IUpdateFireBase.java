package com.example.mycomputer.igotafriend;

import android.view.View;

import java.util.function.Consumer;

/**
 * Created by My computer on 1/21/2018.
 */

public interface IUpdateFireBase <T>{
    void update(T t, Consumer<Boolean> callback);
    //void updateProfile(AccountInfo accountInfo,Consumer<Boolean>callBack);
}
