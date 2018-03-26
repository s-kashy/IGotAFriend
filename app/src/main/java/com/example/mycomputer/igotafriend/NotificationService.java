package com.example.mycomputer.igotafriend;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.mycomputer.igotafriend.activities.ShowLocalProfileActivty;

/**
 * Created by My computer on 2/7/2018.
 */

public class NotificationService extends Service {
    private NotificationManager mManager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        mManager = (NotificationManager) this.getApplicationContext()
                .getSystemService(   this.getApplicationContext().NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(),
                ShowLocalProfileActivty.class);

        Notification notification = new Notification(R.drawable.ic_launcher_background,
                "See My App something for you", System.currentTimeMillis());

        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(
                this.getApplicationContext(), 0, intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//        notification.getSettingsText(this.getApplicationContext(), "SANBOOK",
//                "See My App something for you", pendingNotificationIntent);

        mManager.notify(0, notification);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }
}
