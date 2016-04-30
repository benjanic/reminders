package demo.benjanic.com.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Ben on 23/04/2016.
 */
public class WearHelper {

    public static String saveData(Reminder reminder, Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();

        String id = assignUniqueID(context);
        //Put the set into SharedPreferences
        editor.putString(id, reminder.getSaveString());
        editor.commit();

        return id;
    }

    /**
     * Find the first available ID to save the reminder as
     *
     * @return ID
     */
    public static String assignUniqueID(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        int i = 0;

        while (sharedPref.getString("reminder-" + i, null) != null) {
            i++;
        }
        return "reminder-" + i;
    }

    public static ArrayList<Reminder> getSavedData(Context context) {
        ArrayList<Reminder> reminders = new ArrayList<>();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        //Get all SharedPreferences
        Map<String, ?> keys = sharedPref.getAll();


        //Loop through it
        for (Map.Entry<String, ?> entry : keys.entrySet()) {

            if (entry.getKey().contains("reminder-")) {
                //Get the note from the map
                String saveData = (String) entry.getValue();

                if (saveData != null) {
                    Reminder reminder = new Reminder(saveData);
                    reminders.add(reminder);
                }
            }

        }

        return reminders;
    }

    public static void removeNote(String title, String body, Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        //Get all SharedPreferences
        Map<String, ?> keys = sharedPref.getAll();


        //Loop through it
        for (Map.Entry<String, ?> entry : keys.entrySet()) {

            if (entry.getKey().contains("reminder-")) {
                //Get the note from the map
                String saveData = (String) entry.getValue();

                if (saveData != null) {
                    Reminder reminder = new Reminder(saveData);

                    if (title.equals(reminder.getTitle()) && body.equals(reminder.getBody())) {
                        editor.remove(entry.getKey());
                        editor.commit();
                    }
                }
            }

        }
    }
}
