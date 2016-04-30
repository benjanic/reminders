package demo.benjanic.com.infs2605;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

import demo.benjanic.com.common.Reminder;
import demo.benjanic.com.common.WearHelper;

public class AddNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note); //Set view to layout
    }

    /**
     * Called when the app decides to add options to the toolbar
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //When the Save button is pressed
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            showDatePickerDialog();

        }
        return super.onOptionsItemSelected(item);
    }

    public void showTimePickerDialog(final int year, final int month, final int day) {
        final Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                saveReminderAndClose(year, month, day, hour, minute);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.show();
    }


    public void showDatePickerDialog() {
        final Calendar mcurrentTime = Calendar.getInstance();
        int year = mcurrentTime.get(Calendar.YEAR);
        final int month = mcurrentTime.get(Calendar.MONTH);
        int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                showTimePickerDialog(year, monthOfYear, dayOfMonth);
            }
        }, year, month, day);
        mDatePicker.show();
    }

    public void saveReminderAndClose(int year, int month, int day, int hour, int minute) {
        EditText titleText = (EditText) findViewById(R.id.title_edit_text);
        EditText bodyText = (EditText) findViewById(R.id.body_edit_text);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);


        Date reminderDate = calendar.getTime();
        //Create a new Reminder
        Reminder reminder = new Reminder(titleText.getText().toString(), bodyText.getText().toString(), reminderDate);
        String saveKey = WearHelper.saveData(reminder, getApplicationContext());

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(getApplicationContext(), AlarmReciever.class);
        i.putExtra(Reminder.EXTRA_SAVE_KEY, saveKey);
        i.setAction("com.demo.SHOW_REMINDER");

        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);
        am.setExact(AlarmManager.RTC_WAKEUP, reminderDate.getTime(), pi);

        finish();
    }


}
