package com.mind.oceanic.the.synchronicity2.event;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mind.oceanic.the.synchronicity2.R;

/**
 * Created by dave on 2/6/16.
 */
public class HelpEventListActivity extends Activity {

    TextView tvIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        tvIntro = (TextView) findViewById(R.id.tv_intro);

        tvIntro.setText(Html.fromHtml("<p><b><u>Maintain Events</u></b></p> " +
                "Events may be considered synchronistic or not. If you know ahead of time of an event or series of events" +
                " that you consider to be meaningfully coinincidental, you can use the \"Maintain Synchronicity\" to record the" +
                " events linked to the synchronictic analysis involved. The synchronicity may become apparent after the fact." +
                " This is where \"Maintain Events\" comes in handy. Events can be entered and reviewed at a later date for meaningful " +
                "coincidences.<br><br>" +
                "<b>Show Unlinked Only</b><br>" +
                "For the list of Events displayed, some may not be linked to synchronicities." + " This filter shows only these Events so they can be linked if the synchronicity is determined." +
                "<br><br><b>Show All</b><br>" +
                "This resets the list so that all Events are displayed."
        ));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem i) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = i.getItemId();

        switch (i.getItemId()) {

            case R.id.menu_return:
                finish();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(i);
    }
    public void doReturn(View view){
        finish();
    }

}




