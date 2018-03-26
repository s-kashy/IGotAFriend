package com.example.mycomputer.igotafriend.validetor.LocalValider;

import com.example.mycomputer.igotafriend.ProfileLocol;

import java.util.Arrays;
import java.util.List;

/**
 * Created by My computer on 1/22/2018.
 */

public class ValiderOfLocal implements IProfileValidator {
    private List<ILocalFiledValider> listValditor;
    public ValiderOfLocal(){
        listValditor= Arrays.asList(new InterestsLocalValider(),//
                new AboutMeLocalValider(),//
                new WhatDoIOfferLocalValider());//
    }


    @Override
    public String profileValeder(ProfileLocol profileLocol) {
        for( ILocalFiledValider profileValidator:listValditor){
         String res=profileValidator.validate(profileLocol);
         if (res!=null){
             return res;
         }
        }
return null;
    }
}
