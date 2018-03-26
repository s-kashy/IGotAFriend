package com.example.mycomputer.igotafriend;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.ByteArrayOutputStream;

/**
 * Created by My computer on 1/9/2018.
 */

public class Helper {
    public static byte[] changeToByteArray(Bitmap bitmap){
        ByteArrayOutputStream byt=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byt);
        return byt.toByteArray();
    }
    public static Bitmap changeFormArrayToBitmap(byte[]bitmap){
        return BitmapFactory.decodeByteArray(bitmap,0,bitmap.length);
    }

}
