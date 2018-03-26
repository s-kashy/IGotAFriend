package com.example.mycomputer.igotafriend;

/**
 * Created by My computer on 1/10/2018.
 */

public enum Interests {
    SPORT {
        @Override
        public String toString() {
            return "Sports";
        }
    }, NATURE {
        @Override
        public String toString() {
            return "Nature";
        }
    },WALKING_TOURS{
        @Override
        public String toString(){
            return "Walking Tours";
        }
    },Art_GALLERIES{
        @Override
        public String toString (){
           return "Art Galleries";
        }
    },RELIGION{
        @Override
        public String toString(){
            return "Religious Sites";
        }
    },LOCAL_FOOD{
        public String toString(){
            return "Local Food";
        }
    },SHOPPING{
        public String toString(){
            return "Shopping";
        }
    },NIGHT_LIFE{
        @Override
        public String toString(){
            return "Night Life";
        }
    }
}





