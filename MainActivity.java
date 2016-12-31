package com.mind.oceanic.the.synchronicity2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mind.oceanic.the.synchronicity2.importExport.DownloadJSON;
import com.mind.oceanic.the.synchronicity2.event.EventListActivity;
import com.mind.oceanic.the.synchronicity2.match.MatchKeywordsActivity;
import com.mind.oceanic.the.synchronicity2.synch.SynchListActivity;

/**
 * Created by dave on 2/6/16.
 */
public class MainActivity extends Activity {

    TextView tvIntro;
    TextView tvContents1;
    TextView tvContents2;
    TextView tvContents3;
    TextView tvFinalInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvIntro = (TextView) findViewById(R.id.tv_intro);
        tvContents1 = (TextView) findViewById(R.id.tv_contents1);
        tvContents2 = (TextView) findViewById(R.id.tv_contents2);
        tvContents3 = (TextView) findViewById(R.id.tv_contents3);
        tvFinalInfo = (TextView) findViewById(R.id.tv_finalInfo);

        tvIntro.setText(Html.fromHtml("<p>Welcome to the Synchronicity Tracker.</p> " +
                        "Look for Help in the top righthand menu. On some phones this menu is not visible but " +
                        "is invoked by long pressing the back button. (Left of the middle physical button)<br><br>" +
                        "\n" +
                        "For the complete privacy policy please see:\n" +
                        "\n "));
        String linkText = "<a href='http://theoceanicmind.com/PrivacyPolicy/policy'>Privacy Policy</a> web page.";
        tvContents1.setText(Html.fromHtml(linkText));
        tvContents1.setMovementMethod(LinkMovementMethod.getInstance());
        tvContents2.setText(Html.fromHtml(  "<br></p>" +
                        "This app facilitates the tracking of events and assisting " +
                "in discovering synchronicity. It is a bit of a time machine in that you can set the date forward " +
                        "or backward and 'connect the dots' between events at any time." +
                "There is a companion site at:"));
        String linkText2 = "<a href='http://TheOceanicMind.com'>www.theoceanicmind.com</a>";
        tvContents3.setText(Html.fromHtml(linkText2));
        tvContents3.setMovementMethod(LinkMovementMethod.getInstance());
        tvFinalInfo.setText(Html.fromHtml(
                " where you can upload and download synchronistic events. " +
                        "This site provides a forum to showcase your " +
                        "synchronicities and see what " +
                        "other people have.<br><br>" +
                        "If you are upgrading your phone " +
                        "you can use this site to retain and transfer your synchronistic information to your new phone. " +
                        "For support or general comments, please email: admin@TheOceanicMind.com. "));

    }


    protected void maintainEvents() {

    Intent intent5 = new Intent(MainActivity.this, EventListActivity.class);
    intent5.putExtra("EventSource","EventList");
    startActivityForResult(intent5, 5);
}
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    public void openMaintainEvents(View view){
        maintainEvents();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem i) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = i.getItemId();

        switch (i.getItemId()) {

//            case R.id.action_settings:
//                Intent intent = new Intent(this, HttpMainActivity.class);
//                startActivity(intent);
//                break;

            case R.id.menu_maintain_synchronicity:
                Intent intent4 = new Intent(MainActivity.this, SynchListActivity.class);
                startActivityForResult(intent4, 4);
                break;

            case R.id.menu_maintain_events:
                maintainEvents();
                break;

            case R.id.menu_match_events:
                Intent intent6 = new Intent(MainActivity.this, MatchKeywordsActivity.class);
                startActivityForResult(intent6, 6);
                break;

            case R.id.menu_import:
                Intent intent7 = new Intent(MainActivity.this, DownloadJSON.class);
                Log.i("dolphinp","intent7");
                startActivity(intent7);
                break;

            case R.id.menu_help:
                Intent intent = new Intent(this, HelpMainActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(i);
    }

    /**
     * Created by dave on 8/13/15.
     */
    public static class SettingsActivity {
    }
}


