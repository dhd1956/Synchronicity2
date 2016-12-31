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
public class HelpSynchListActivity extends Activity {

    TextView tvIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        tvIntro = (TextView) findViewById(R.id.tv_intro);

        tvIntro.setText(Html.fromHtml("<p><b><u>Maintain Synchronicity</u></b></p> " +
                "When you already have an idea of a meaningful coincidence, you can enter a reference to it, the analysis about it and the associated Events. <br><br>" +
                "<br><b>Deleting Items</b><br> " +
                "From any list press and hold an item on the list to be prompted to delete that item.<br><br>" +
                "Delete from web site is independent from Smart Phone. "));
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




