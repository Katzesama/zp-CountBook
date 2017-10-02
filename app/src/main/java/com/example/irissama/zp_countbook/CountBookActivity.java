package com.example.irissama.zp_countbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * The main activity, main page
 */
public class CountBookActivity extends AppCompatActivity {

    public static final String FILENAME = "file.sav";
    protected ListView oldCountersList;
    protected ArrayList<Counter> counters = new ArrayList<Counter>();
    private ArrayAdapter<Counter> adapter;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_bookactivity);

        /*
        set up the View for main page
         */
        title = (TextView) findViewById(R.id.oldCounterTitle);
        oldCountersList = (ListView) findViewById(R.id.oldCounterlist);

        /*
        create a button to jump to AddCounterActivity,
        where user can add a counter
         */
        Button add = (Button) findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addc = new Intent(CountBookActivity.this, AddCounterActivity.class);
                startActivity(addc);
            }
        });

        /* create a context menu for each counter in the list*/
       registerForContextMenu(oldCountersList);
    }

    /*
    add the options to the context menu
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
        menu.add(0, v.getId(), 0, "Detail");
    }

    /*
    define actions for the options in context menu
     */
    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int ItemClicked = info.position;

        if (item.getTitle() == "Edit") {
            //jump to EditCounterActivity, where user can edit the existing counter
            Toast.makeText(this, "Editing", Toast.LENGTH_SHORT).show();
            Intent editc = new Intent(CountBookActivity.this, EditCounterActivity.class);
            editc.putExtra("itemposition", ItemClicked);
            startActivity(editc);
        } else if (item.getTitle() == "Delete"){
            //Delete the select counter from list
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
            counters.remove(ItemClicked);
            adapter.notifyDataSetChanged();
            saveInFile();
            title.setText("Number of Counters : " + counters.size());

        } else if (item.getTitle() == "Detail") {
            //jump to DetailCounterActivity, user can see deatils of the selected counter
            Intent display = new Intent(CountBookActivity.this, DetailCounterActivity.class);
            display.putExtra("ip", ItemClicked);
            startActivity(display);

        } else {
            return false;
        }
        return true;

    }

    /*
     * show the existing list of counters and summary of counters number
     * on every start of the main activity
     */
   @Override
    protected void onStart(){
       super.onStart();
       loadFromFile();
       adapter = new ArrayAdapter<Counter>(this, R.layout.list_item, counters);
       oldCountersList.setAdapter(adapter);
       title.setText("Number of Counters : " + counters.size());

    }

    //load the existing counters from file
   protected void loadFromFile() {
        try{
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>() {}.getType();
            counters = gson.fromJson(in, listType);

            fis.close();

        } catch (FileNotFoundException e) {
            counters = new ArrayList<Counter>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    //write counters to a file
    protected void saveInFile(){
        try{
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(counters, writer);
            writer.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
