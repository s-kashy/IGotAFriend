package com.example.mycomputer.igotafriend;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by My computer on 1/18/2018.
 */

public class CheckBoxService implements ICheckBoxService {
    @Override
    public void creatCheckBox(Context context, LinearLayout lineRight, LinearLayout lineLeft) {
        CheckBox checkBox;
        ArrayList<Interests> listOfInterests = InterestsList.getAllInterests();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams//
                (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < listOfInterests.size(); i++) {
            checkBox = new CheckBox(context);
            checkBox.setLayoutParams(layoutParams);
            checkBox.setTag(listOfInterests.get(i));

            checkBox.setId(i + 1);

            checkBox.setText(listOfInterests.get(i).toString());
            if (i % 2 == 0) {
                layoutParams.setMargins(0, 0, 0, 0);
                lineLeft.addView(checkBox);

            } else {
                layoutParams.setMargins(0, 0, 100, 0);
                lineRight.addView(checkBox);

            }

        }
    }

    @Override
    public void getAllInterstsCheck(LinearLayout lineRight, LinearLayout lineLeft, List<Interests>interests) {
        int i = 0, j = 0;
        while (j < lineRight.getChildCount() && i < lineLeft.getChildCount()) {
            if (i < lineLeft.getChildCount()) {

                CheckBox checkBox = (CheckBox) lineLeft.getChildAt(i);

                if (checkBox.isChecked()) {

                    Interests interest = (Interests) checkBox.getTag();
                    lineLeft.getChildAt(i).setTag(interest);
                    interests.add(interest);

                }
            }
            if (j < lineRight.getChildCount()) {

                CheckBox checkBox1 = (CheckBox) lineRight.getChildAt(j);
                if (checkBox1.isChecked()) {
                    Interests interest = (Interests) checkBox1.getTag();
                    lineRight.getChildAt(j).setTag(interests);
                 interests.add(interest);
                }
            }
            i++;
            j++;
        }
    }

    @Override
    public void checkboxViewForVister(List<Interests> interests, LinearLayout lineRight, LinearLayout lineLeft) {

        int  i = 0, j = 0;

        for (i = 0; i < interests.size(); i++) {
            for (j = 0; j < lineLeft.getChildCount(); j++) {
                if (interests.get(i).equals(lineLeft.getChildAt(j).getTag())) {
                    CheckBox checkBox = (CheckBox) lineLeft.getChildAt(j);
                    checkBox.setChecked(true);
                    break;
                }
            }
        }

        for (i = 0; i < interests.size(); i++) {
            for (j = 0; j < lineRight.getChildCount(); j++) {
                if (interests.get(i).equals(lineRight.getChildAt(j).getTag())) {
                    ((CheckBox) lineRight.getChildAt(j)).setChecked(true);
                    break;

                }
            }
        }
    }

                @Override public void diabableAll ( boolean res, LinearLayout
                lineRight, LinearLayout lineLeft, TextView txBasicInfoLocal, TextView txLocalOfer){
                    int i = 0, j = 0;

                    while (i < lineRight.getChildCount() && j < lineLeft.getChildCount()) {
                        if (i < lineRight.getChildCount()) {
                            lineRight.getChildAt(i++).setEnabled(res);

                        }
                        if (j < lineLeft.getChildCount()) {
                            lineLeft.getChildAt(j++).setEnabled(res);
                        }
                    }
                    txBasicInfoLocal.setEnabled(res);
                    txLocalOfer.setEnabled(res);
                }

                @Override public void setCheckBoxOfUsers (Context
                context, List < Interests > interests, LinearLayout lineRight, LinearLayout lineLeft)
                {

                    CheckBox checkBox;
                    //   TextView txtView = (TextView) ((Activity)context).findViewById(R.id.txBasicInfoLocal);
                    for (int i = 0; i < interests.size(); i++) {
                        if (interests.get(i).equals(lineLeft.getChildAt(i).getTag()) && i < lineLeft.getChildCount()) {
                            checkBox = new CheckBox(context);
                            checkBox.setChecked(true);
                        } else if (interests.get(i).equals(lineRight.getChildAt(i).getTag()) && i < lineRight.getChildCount()) {
                            checkBox = new CheckBox(context);
                            checkBox.setChecked(true);
                        }
                    }


                }
            }
