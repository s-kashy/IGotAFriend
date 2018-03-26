package com.example.mycomputer.igotafriend;

/**
 * Created by My computer on 1/13/2018.
 */

public enum Gender {
    MALE{
        @Override
        public String toString(){
            return "Male";
        }
    },
    FEMALE{
        @Override
        public String toString (){
            return  "Female";
        }
    },
    UNDEFINE{
        @Override
        public String toString(){
            return "undefine";
        }
    }
}
