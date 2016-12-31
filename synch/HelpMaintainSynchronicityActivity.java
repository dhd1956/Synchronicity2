package com.mind.oceanic.the.synchronicity2.synch;

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
public class HelpMaintainSynchronicityActivity extends Activity {

    TextView tvIntro;
    TextView tvContent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        tvIntro = (TextView) findViewById(R.id.tv_intro);
        tvContent1 = (TextView) findViewById(R.id.tv_contents1);

        tvIntro.setText(Html.fromHtml("<p><b><u>Maintain Synchronicity</u></b></p> " +
                "When you already have an idea of a meaningful coincidence, you can enter a reference to it, the analysis about it and the associated Events. <br>" +
                "<br><b>New Synchronistic Connection</b><br> " +
                "The date the Synchronicity was discovered, along with a short defining summary is entered here. Most importantly, the Analysis of the Events and what ws learned about the pattern or meaning you have found is entered. <br>" +
                "<br><b>Link Life Event</b><br> " +
                "For this Synchronicity, the list of Events can be reviewed and one selected and associated. <br>" +
                "<br><b>New Life Event</b><br> " +
                "For this Synchronicity, a new Event can be recorded and associated." +
                "<br><br><b>Upload</b><br> " +
                 "This Synchronicity and associated Events are uploaded to 'TheOceanicMind.COM'." +
                " It is immediately available at that web site. Note that an initial Download needs " +
                " to take place first in order to establish the link between the web site and your Smart Phone.  <br><br>"));
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




