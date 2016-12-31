package com.mind.oceanic.the.synchronicity2.match;

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
public class HelpMatchKeywordsActivity extends Activity {

    TextView tvIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        tvIntro = (TextView) findViewById(R.id.tv_intro);

        tvIntro.setText(Html.fromHtml("<p><b><u>Match Synchronistic Events</u></b></p> " +
                "As an aid to discovering meaningful coincidents, words within each " +
                        "Event Details are matched to other Events and presented to you." +
                        "<br><br><b>Ignore From Now On<br></b>"+
                "Word matches that are of no values can be ignored from now on by selecting this menu item." +
        "<br><br><b>Ignore Reset</b><br>" +
                "In case you wish to re-evaluate all matches, this menu item resets the Ignore From Now On list." +
                "<br><br><b>Match These Events</b><br>" +
                        "If a meaningful coincidence is discovered involving two Events, this menu item allows these Events to be linked." +
        "<br><br><b>Skip</b><br>" +
                        "A match was found, but it has been determined that there is no actual coincidence. This menu item lets you skip this match and continue on."
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




