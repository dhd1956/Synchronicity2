package com.mind.oceanic.the.synchronicity2.note;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mind.oceanic.the.synchronicity2.R;
import com.mind.oceanic.the.synchronicity2.db.SynchronicityDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dave on 2/22/16.
 */
public class ContactsActivity extends Activity {

    List<ContentResolver> contacts;
    SynchronicityDataSource datasource;

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;


    ListView lstContacts;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.contacts);
        lstContacts = (ListView) findViewById(R.id.lst_contacts);
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        lstContacts.setAdapter(adapter);
        Log.i("dolphin", "in contacts");

//        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, mobileArray);
        ArrayAdapter adapter = new ArrayAdapter<String>(ContactsActivity.this,R.layout.contacts);
        lstContacts.setAdapter(adapter);
        setContactsList();
        Log.i("dolphin", "nonStop");
    }

    @Override
    public void onBackPressed() {
        prepareReturnValues("");
    }

    public void setContactsList() {

        Log.i("dolphin", "in setContactslist");
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                Log.i("dolphin", "mdarker  " + id);
                String name = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Log.i("dolphin", "mdarker  " + name);
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    //Query phone here.  Covered next
                }

                lstContacts.setAdapter(adapter);
                listItems.add(name);
                Log.i("dolphin", "mdarkereven  " + name);
            }
            // dd oct 2016 - datasource, order by name
        }
        lstContacts.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View view,
                                            int position, long id) {
                        String name = "";
                        Log.i("dolphin","name is as="+name);
                        name = listItems.get(position);
                        prepareReturnValues(name);
                    }
                });
    }

    protected void prepareReturnValues(String name) {
        Intent intent=new Intent();

        if (name == null){
            name = "";
        }
        intent.putExtra("ContactName", name);
        setResult(6, intent);
        Log.i("dolphin", "vvvverbid=" + name);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem i) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = i.getItemId();

        switch (i.getItemId()) {

            case R.id.menu_cancel:
                prepareReturnValues("");
                break;

            default:
                Log.i("dolphin","default ending");
                break;
        }

        return super.onOptionsItemSelected(i);
    }

}
