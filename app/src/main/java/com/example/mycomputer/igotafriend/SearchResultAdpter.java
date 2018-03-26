package com.example.mycomputer.igotafriend;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by My computer on 1/12/2018.
 */

public class SearchResultAdpter extends ArrayAdapter<AccountInfo> {
    public Context context;
    public List<AccountInfo> objects;


    public SearchResultAdpter(@NonNull Context context, int resource, @NonNull List<AccountInfo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AccountInfo accountInfo = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            convertView = layoutInflater.inflate(R.layout.search_layout, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.txName);
            viewHolder.numOfCritecs = (TextView) convertView.findViewById(R.id.numOfCritecs);
            viewHolder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(accountInfo.getDispalyName());
        if (accountInfo.getProfileLocol().getNumCritics() != 0) {
            viewHolder.numOfCritecs.setText(String.valueOf(accountInfo.getProfileLocol().getNumCritics()));
        }

        viewHolder.ratingBar.setRating(accountInfo.getProfileLocol().getRate());
        return convertView;


    }

    public static class ViewHolder {
        public RatingBar ratingBar;
        public TextView numOfCritecs;
        public TextView name;
    }


}
