package com.example.irissama.zp_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * New activity that allows user to edit the selected counter
 */
public class EditCounterActivity extends CountBookActivity {
    Integer count;
    TextView currente;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_counter);

        /*
        get the counter's position
         */
        Intent fmain = getIntent();
        final int itempo = fmain.getIntExtra("itemposition", 0);

        loadFromFile();
        /*
        get the counter's current value for further use
        */
        count = counters.get(itempo).getCurrent_v();

        /*
        set up the editable attributes of counter
         */
        final EditText namee = (EditText) findViewById(R.id.name);
        namee.setText(counters.get(itempo).getName());
        currente = (TextView) findViewById(R.id.current);
        currente.setText(count.toString());
        final EditText initiale = (EditText) findViewById(R.id.initial);
        initiale.setText(counters.get(itempo).getInitial_v().toString());
        final EditText commente = (EditText) findViewById(R.id.comment);
        commente.setText(counters.get(itempo).getComment());

        /*
        create a save button to save the counter
        create a increase button to increment current value by 1
        create a decrease button to decrease current value by 1
        create a reset button to reset current value to intial value
         */
       final Button saveButton = (Button) findViewById(R.id.saveButton);
        Button incrButton = (Button) findViewById(R.id.increase);
        Button decrButton = (Button) findViewById(R.id.decrease);
        Button resetButton = (Button) findViewById(R.id.reset);

        incrButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                count += 1;
                currente.setText(count.toString());
            }
        });

        decrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*to check if the current value will drop to negative*/
                if (count == 0){
                    Toast.makeText(EditCounterActivity.this,
                            "Current value cannot be negative.", Toast.LENGTH_SHORT).show();
                } else{
                    count -= 1;
                    currente.setText(count.toString());
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = counters.get(itempo).getInitial_v();
                currente.setText(count.toString());
            }
        });

        /*
          update the counter to file
          and check the counter's edited attributes
          refuse the invalid values
         */
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = namee.getText().toString();
                int initialv = counters.get(itempo).getInitial_v();
                String initial = initiale.getText().toString();
                try {
                    int initialc = Integer.parseInt(initial);
                    if (initialc >= 0 && name.trim().length() != 0) {
                        if (initialc != initialv) {
                            count = initialc;
                        }

                        counters.set(itempo, new Counter(name, initialc,
                                commente.getText().toString(), count));
                        saveInFile();
                        Toast.makeText(EditCounterActivity.this, "Edited.",
                                Toast.LENGTH_SHORT).show();

                    } else if (name.trim().length() == 0) {
                        Toast.makeText(EditCounterActivity.this,
                                "Name filed cannot be empty.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditCounterActivity.this,
                                "Initial value cannot be negative.", Toast.LENGTH_SHORT).show();
                    }
                } catch(NumberFormatException e) {
                    Toast.makeText(EditCounterActivity.this,
                            "Invalid input.", Toast.LENGTH_SHORT).show();
                    Log.d("", "Invalid input");
                }


            }
        });
    }


}
