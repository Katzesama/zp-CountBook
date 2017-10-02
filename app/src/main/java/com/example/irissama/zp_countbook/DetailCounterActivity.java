package com.example.irissama.zp_countbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * New activity to show the detail of selected counter
 */
public class DetailCounterActivity extends CountBookActivity {
    TextView dname, dinitial, dcurrent, ddate, dcomment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_counter);

        Intent fmain = getIntent();
        final int itempo = fmain.getIntExtra("ip", 0);

        loadFromFile();
        /*
        get the selected counter
         */
        Counter display = counters.get(itempo);

        /*
        set up the fields and get attributes from the counter
        set Text into the field to display
         */
        dname = (TextView) findViewById(R.id.name);
        dinitial = (TextView) findViewById(R.id.initial);
        dcurrent = (TextView) findViewById(R.id.current);
        ddate = (TextView) findViewById(R.id.date);
        dcomment = (TextView) findViewById(R.id.comment);

        String dn = "Name: " + display.getName();
        String di = "Initial value: " + display.getInitial_v().toString();
        String dc = "Current value: " + display.getCurrent_v().toString();
        String dd = "Last Modify on: " + display.getDate();
        String dco = "Comment: " + display.getComment();
        dname.setText(dn);
        dinitial.setText(di);
        dcurrent.setText(dc);
        ddate.setText(dd);
        dcomment.setText(dco);
    }
}
