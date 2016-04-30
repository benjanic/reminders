package demo.benjanic.com.infs2605;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import demo.benjanic.com.common.Reminder;

/**
 * Created by Ben on 28/04/2016.
 */
public class AlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String saveString = intent.getStringExtra(Reminder.EXTRA_SAVE_KEY);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        String saveData = sharedPref.getString(saveString, null);

        if(saveData != null) {
            Reminder reminder = new Reminder(saveData);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_add_white)
                            .setContentTitle(reminder.getTitle())
                            .setContentText(reminder.getBody());

            NotificationManager mNotifyMgr =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            mNotifyMgr.notify(1, mBuilder.build());
        }
    }
}
