package kz.coursereminder.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import kz.coursereminder.R;

public class NotificationHelper extends ContextWrapper {

    /**
     * ChannelID
     */
    public static final String channel1ID = "channel1ID";
    /**
     * ChannelName
     */
    public static final String channel1Name = "Channel 1";

    /**
     * NotificationManager
     */
    private NotificationManager manager;

    /**
     * Constructor
     * @param base context
     */
    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    /**
     * Create channel 1 if android os is oreo and above
     */
    @TargetApi(Build.VERSION_CODES.O)
    public void createChannel() {
        NotificationChannel channel =  new NotificationChannel(channel1ID, channel1Name,
                NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        getManager().createNotificationChannel(channel);
    }

    /**
     * Getter of notificationManager, create one if null
     * @return manager
     */
    public NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    /**
     * Create a new NotificationCompat.Builder
     * @param title title of notification
     * @param message message of notification
     * @return a new NotificationCompat.Builder
     */
    public NotificationCompat.Builder getChannel1Notification(String title, String message) {
        return new NotificationCompat.Builder(getApplicationContext(), channel1ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notifications_none_black_24dp);
    }

}
