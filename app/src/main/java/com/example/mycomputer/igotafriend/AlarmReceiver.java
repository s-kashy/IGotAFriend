package com.example.mycomputer.igotafriend;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.mycomputer.igotafriend.activities.ShowLocalProfileActivty;

import java.io.Serializable;

/**
 * Created by My computer on 2/5/2018.
 */

public class AlarmReceiver extends BroadcastReceiver  {


    @Override
    public void onReceive(Context context, Intent intent) {

        AccountInfo accountInfo = (AccountInfo) intent.getSerializableExtra("key");

        Intent intent1 = new Intent(context, ShowLocalProfileActivty.class);
        intent1.putExtra("key", "rate");
        intent1.putExtra("local", accountInfo);
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent1, 0);
        Notification n = new Notification.Builder(context).setContentTitle("Hello Form I Got a Friend").setContentText("Subject").setSmallIcon(R.drawable.ic_launcher_background)//

                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})//
                .setContentIntent(pIntent).setAutoCancel(true).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);

    }

}

