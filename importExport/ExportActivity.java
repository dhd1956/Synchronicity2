package com.mind.oceanic.the.synchronicity2.importExport;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

import com.mind.oceanic.the.synchronicity2.R;
import com.mind.oceanic.the.synchronicity2.db.SynchronicityDataSource;
import com.mind.oceanic.the.synchronicity2.model.Event;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.List;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by dave on 3/21/16.
 */
public class ExportActivity extends Activity {

    SynchronicityDataSource datasource;
    Event event;
    private List<Event> events;

    ListView lstEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        datasource = new SynchronicityDataSource(this);

        lstEvents = (ListView) findViewById(R.id.lst_events);

        Log.i("dolphin", "in export");
        events = datasource.findAllEvents();
        Log.i("dolphin","mid export");
        ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(ExportActivity.this,
                android.R.layout.simple_list_item_1, events);
        lstEvents.setAdapter(adapter);

        dbToText();

    }
protected void exportToSD(){
    try {
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();

        if (sd.canWrite()) {
            String currentDBPath = "/data/" + getPackageName() + "/databases/yourdatabasename";
            String backupDBPath = "backupname.db";
            File currentDB = new File(currentDBPath);
            File backupDB = new File(sd, backupDBPath);

            if (currentDB.exists()) {
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        }
    } catch (Exception e) {

    }

}
    public static String DB_FILEPATH = "/data/data/{package_name}/databases/database.db";

    /**
     * Copies the database file at the specified location over the current
     * internal application database.
     * */
    public boolean importDatabase(String dbPath) throws IOException {

        // Close the SQLiteOpenHelper so it will commit the created empty
        // database to internal storage.

        datasource = new SynchronicityDataSource(this);
        datasource.close();
        File newDb = new File(dbPath);
        File oldDb = new File(DB_FILEPATH);
        if (newDb.exists()) {
            FileUtils.copyFile(new FileInputStream(newDb), new FileOutputStream(oldDb));
            // Access the copied database so SQLiteHelper will cache it and mark
            // it as created.
            datasource.close();
            return true;
        }
        return false;
    }

    protected void dbToText(){

        File root = android.os.Environment.getExternalStorageDirectory();
//        tv.append("\nExternal file system root: "+root);

        // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder

//        File dir = new File (root.getAbsolutePath() + "/Downloads");
//        dir.mkdirs();
//        File file = new File(dir, "/myData.txt");
        File file = new File(Environment.getExternalStorageDirectory(), "wordlist.txt");
        String str="";
//        File file = new File("/home/dave/robots.txt");

        OutputStream out = null;
        Log.i("dolphin","export topp="+events.get(0).getEventSummary());
        Log.i("dolphin","export path="+file);

//        try {
//            FileOutputStream fos = new FileOutputStream(file);
//            out = new BufferedOutputStream(new FileOutputStream(file));

        Log.i("dolphin","export fu = new="+events.get(0).getEventSummary());
        try {
            FileOutputStream f = new FileOutputStream(file);
            Log.i("dolphin","export f = new="+events.get(0).getEventSummary());
            PrintWriter pw = new PrintWriter(f);

                for (int i=0;i<events.size();i++) {
                    str = events.get(i).getEventDate() + " " + events.get(i).getEventSummary() + " " + events.get(i).getEventDetails() + "\n";
                    Log.i("dolphin","export summ="+events.get(i).getEventSummary());
                    pw.printf(str);
                }
                ;
                pw.flush();
                pw.close();

                f.close();

        } catch (IOException e) {
            Log.i("dolphin","ioexception encounted");
        } finally {

                if (out != null) {
//                out.close();
            }
        }


    }
//    protected void textToDb(){
//        File file = new File("C:/robots.txt");
//
//        try (FileInputStream fis = new FileInputStream(file)) {
//
//            System.out.println("Total file size to read (in bytes) : "+ fis.available());
//
//            int content;
//            while ((content = fis.read()) != -1) {
//                // convert to char and display it
//                System.out.print((char) content);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
