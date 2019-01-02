package kz.coursereminder.structure;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("id", 1);
        NotificationHelper notificationHelper = new NotificationHelper(context);
//        NotificationCompat.Builder builder = notificationHelper.getChannel1Notification();
//        notificationHelper.getManager().notify(id, builder.build());

    }
}
