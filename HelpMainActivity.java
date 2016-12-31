package com.mind.oceanic.the.synchronicity2;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by dave on 2/6/16.
 */
public class HelpMainActivity extends Activity {

    TextView tvIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        tvIntro = (TextView) findViewById(R.id.tv_intro);

        tvIntro.setText(Html.fromHtml("<p><b><u>Synchronicity Tracker Main Menu</u></b></p> " +
"<b>Maintain Events</b><br>" +
                        "Use this to record any events that you are involved with on a daily basis. " +
                        "<br>You can create Reminders that are connected to your phone's Contacts." +
                        "<br>You can send Reminders to your phone's Calendar app." +
                        "<br><br><b>Maintain Synchroncity</b><br>" +
                        "When you have experienced a meaningful coincidence and want to record it, " +
                        "this is where you go. Events are connected to these synchronistic items and you provide an analysis here." +
                        "<br><br><b>Match Synchronistic Events</b><br>" +
                        "You can sift through Events after creating them to determine synchronicities." +
                        "<br><br><b>Download From Website</b><br>" +
                        "This menu item is used after the \"Download\" function on the \"TheOceanicMind.com\" website" +
                        " has been selected. " +
                        "All the Synchronicities and associated Events for your userId on that site are downloaded. " +
                        "You enter the " +
                        "userId here and this information is downloaded into your Smart Phone."
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




