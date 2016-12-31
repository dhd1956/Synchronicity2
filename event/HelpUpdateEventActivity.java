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
public class HelpUpdateEventActivity extends Activity {

    TextView tvIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        tvIntro = (TextView) findViewById(R.id.tv_intro);

        tvIntro.setText(Html.fromHtml("<p><b><u>Record Event</u></b></p> " +
                "This menu item displays the date prepopulated to today's date. " +
                        "The Event Summary describes the event in a few word. " +
                        "The Event Details contains a write up of what the Event is about." +
        "<br><br><b>Reminders</b><br>" +
        "Since you are entering Events as they occur, you can tie into the Smart Phone's " +
                "contact list and calendar for reminders that you associate to that event."
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




