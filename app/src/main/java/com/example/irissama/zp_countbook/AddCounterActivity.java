package com.example.irissama.zp_countbook;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddCounterActivity extends CountBookActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counter);

        final EditText names = (EditText) findViewById(R.id.name);
        final EditText intial = (EditText) findViewById(R.id.initial);
        final EditText comment = (EditText) findViewById(R.id.comment);

        final Button saveButton = (Button) findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String initials = intial.getText().toString();
                String name = names.getText().toString();
                try {
                    int initialv = Integer.parseInt(initials);
                    if (initialv >= 0 && name.trim().length() != 0) {
                        counters.add(new Counter(name, initialv,
                                comment.getText().toString(), initialv));
                        Toast.makeText(AddCounterActivity.this, "Added.", Toast.LENGTH_SHORT).show();
                        saveInFile();
                    } else if (name.trim().length() == 0) {
                        Toast.makeText(AddCounterActivity.this,
                                "Name filed cannot be empty.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddCounterActivity.this,
                                "Initial value cannot be negative.", Toast.LENGTH_SHORT).show();
                    }
                } catch(NumberFormatException e) {
                    Toast.makeText(AddCounterActivity.this,
                            "Invalid input.", Toast.LENGTH_SHORT).show();
                        Log.d("", "Invalid input");
                }
            }
        });




    }
}
