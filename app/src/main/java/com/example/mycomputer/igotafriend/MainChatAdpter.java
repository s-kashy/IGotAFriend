package com.example.mycomputer.igotafriend;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by My computer on 1/12/2018.
 */

public class MainChatAdpter extends ArrayAdapter<Chat> {
    private Context context;
    private List<Chat> objects;
    private String udi;

    public MainChatAdpter(@NonNull Context context, int resource, @NonNull List<Chat> objects, String udi) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.udi = udi;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

       Chat messages = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            convertView = layoutInflater.inflate(R.layout.chat_list_layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.msg = (TextView) convertView.findViewById(R.id.txName);
            viewHolder.name = (TextView) convertView.findViewById(R.id.txMessage);
            viewHolder.imageView = (de.hdodenhof.circleimageview.CircleImageView) convertView.findViewById(R.id.imageType);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (messages==null){

            return convertView;
        }
        if (!messages.getChat().isEmpty()) {
            viewHolder.msg.setText(messages.getChat().get(messages.getChat().size() - 1).getTextMsg());
            viewHolder.name.setText(messages.getChat().get(messages.getChat().size() - 1).getWriter());

        }

        if (messages.getVisterUdi().equals(udi)) {
            viewHolder.imageView.setImageResource(R.drawable.l);
        } else {
            viewHolder.imageView.setImageResource(R.drawable.v);
        }
        //dont understand the function of the drawable;
        return convertView;

    }

    public static class ViewHolder {
        public TextView msg;
        public TextView name;
        public de.hdodenhof.circleimageview.CircleImageView imageView;
    }


}
