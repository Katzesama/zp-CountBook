package com.example.irissama.zp_countbook;

import android.text.Editable;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Irissama on 28/9/2017.
 */

/**
 * represents a counter object
 */
public class Counter {
    private String name;
    private Date date;
    private Integer current_v;
    private Integer initial_v;
    private String comment;
    private String displaydate;

    /*
     * set up a counter with its attributes set
     */
    public Counter (String name, Integer initial, String comment, Integer current) {
        this.name = name;
        initial_v = initial;
        this.comment = comment;
        date = new Date();
        displaydate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        this.current_v = current;
    }

    /*
    functions that returns counter's attributes
     */
   public String getName(){
        return name;
    }

    public Integer getCurrent_v(){
        return current_v;
    }

    public Integer getInitial_v(){
        return initial_v;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return displaydate;
    }

    /*
    the function returns the string to adapter to show
     */
    @Override
    public String toString(){
        return name + " has " + current_v.toString() + '\n'
                + "Created on " + displaydate;
    }

}
