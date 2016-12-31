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
public class HelpEventNotesActivity extends Activity {

    TextView tvIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        tvIntro = (TextView) findViewById(R.id.tv_intro);

        tvIntro.setText(Html.fromHtml("<p><b><u>Reminders</u></b></p> " +
                        "<b>Assign Contacts</b><br>" +
                "A list of your smart phone's contacts is provided and you can select one or enter " +
                        "a Reminder's contact freeform." +
                "<br><b>Create Calendar Event</b><br>" +
        "This brings relevant information for the Event into the Smart Phone's calendar App where " +
                "you can use the calendar features directly to complete the calendar entry." +
        "<br><b>Create Outstanding Reminders</b><br>" +
                "An Action can have a reciprocating Action. Perhaps you are borrowing a book. " +
                "At a later event involving the same contact, " +
                "you may have forgotten about the book. This menu item searches for such things and " +
                "prompts you to return the book." +
                "<br><b>Create Action</b><br>" +
        "There are predefined Actions, but if you think of more that have a subsequent Action that you wish to track, " +
                "this is where you enter it." +
                "Name: is the name of the Action. Such as \"Borrow\"" +
        "Applies To: this is the reciprocal Action such as \"Return\" with applies to \"Borrow\"." +
                "<br><b>Create Item</b><br>" +
        "You can create all sorts of things that you lend or borrow or perform some other reciprocal action on. "

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




