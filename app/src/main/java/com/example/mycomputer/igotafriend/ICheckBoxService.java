package com.example.mycomputer.igotafriend;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by My computer on 1/18/2018.
 */

public interface ICheckBoxService {
    void creatCheckBox(Context context, LinearLayout lineRight, LinearLayout lineLeft);

    void getAllInterstsCheck(LinearLayout lineRight, LinearLayout lineLeft, List<Interests>interests);

    void checkboxViewForVister(List<Interests> interests, LinearLayout lineRight, LinearLayout lineLeft);

    void diabableAll(boolean res, LinearLayout lineRight, LinearLayout lineLeft, TextView textView, TextView textView1);

    void setCheckBoxOfUsers(Context context, List<Interests> interests, LinearLayout lineRight, LinearLayout lineLeft);
}
