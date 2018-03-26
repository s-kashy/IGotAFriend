package com.example.mycomputer.igotafriend;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by My computer on 1/13/2018.
 */

public class ChatPersonalAdpter extends ArrayAdapter<ChatMessage> {
    private Context context;
    private List<ChatMessage> objects;

    public ChatPersonalAdpter(@NonNull Context context, int resource, @NonNull List<ChatMessage> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ChatMessage chat = getItem(position);
        ViewHelp viewHelp;
        if (convertView == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            convertView = layoutInflater.inflate(R.layout.personl_chat_layout, parent, false);
            viewHelp = new ViewHelp();
            viewHelp.msg = (TextView) convertView.findViewById(R.id.txMessage);
            viewHelp.auther = (TextView) convertView.findViewById(R.id.txAuther);
            convertView.setTag(viewHelp);
        } else {
            viewHelp = (ViewHelp) convertView.getTag();
        }
        viewHelp.msg.setText(chat.getTextMsg().toString());
        viewHelp.auther.setText(chat.getWriter().toString());

        return convertView;

    }

    public static class ViewHelp {
        TextView msg;
        TextView auther;

    }
}
