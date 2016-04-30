package demo.benjanic.com.infs2605;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.Wearable;

import java.text.SimpleDateFormat;

import demo.benjanic.com.common.Reminder;
import demo.benjanic.com.common.WearHelper;

public class MainActivity extends AppCompatActivity {


    /**
     * This is what is first executed when an Activity is launched
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find and set a toolbar to the activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Create an Intent to open an new Activity (EditNoteAddActivity)
        final Intent openEdit = new Intent(this, AddNoteActivity.class);

        //Locate the floating action button (FAB) we defined in R.layout.activity_main
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        //Set a listener for click events on the FAB
        //I.e when the FAB is clicked, the 'onClick(View view)' method is executed
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start the activity
                startActivity(openEdit);
            }
        });
    }

    /**
     * Called whenever the Activity is 'refocused' e.g when the app is first opened, or when the
     * user returns to this activity from the AddNote activity
     */
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI(){
        //Find the parent and get rid of all the notes in the view
        LinearLayout noteDisplay = (LinearLayout) findViewById(R.id.note_display);
        noteDisplay.removeAllViews();

        //Loop through it
        for (Reminder reminder : WearHelper.getSavedData(this)) {
            if (reminder != null) {
                //If the note exists, inflate a note
                View view = getLayoutInflater().inflate(R.layout.note_holder, null);

                //Find the title/body and set the text of the note
                TextView title = (TextView) view.findViewById(R.id.title);
                title.setText(reminder.getTitle());


                TextView body = (TextView) view.findViewById(R.id.body);
                body.setText(reminder.getBody());

                SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy hh:mm aa");

                TextView date = (TextView) view.findViewById(R.id.date);
                date.setText(format.format(reminder.getDate()));

                //Add the note to the parent
                noteDisplay.addView(view);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void delete(View view) {
        LinearLayout parent = (LinearLayout) view.getParent();
        TextView findTitle = (TextView) parent.findViewById(R.id.title);
        TextView findMessage = (TextView) parent.findViewById(R.id.body);

        WearHelper.removeNote(findTitle.getText().toString(), findMessage.getText().toString(),this);

        updateUI();
    }

}
