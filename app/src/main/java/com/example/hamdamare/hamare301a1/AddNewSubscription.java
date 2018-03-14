package com.example.hamdamare.hamare301a1;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hamdamare
 * @see SaveSubscription
 * @see SubscriptionManager
 * @see MainActivity
 */

public class AddNewSubscription extends AppCompatActivity {
    //Adds user input to our array list

    private static final String TAG = "MyMessage";

    /**
     * Override
     * @param savedInstanceState savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_subscription);
        SaveSubscription.initializeManager(this.getApplicationContext());
    }


    /**
     * Override
     *printing a subscription message when onStart()
     */

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Starting ..... ");

    }

    /**
     * @param view
     * Adding a new subscription if valid subscription was enterd
     */
    public void addSubscription(View view) {
        //Show error message when input is not in correct format
        if (!valid()) {
            error();
            return;
        }

        EditText name = (EditText) (findViewById(R.id.name));
        EditText date = (EditText) (findViewById(R.id.date));
        EditText price = (EditText) (findViewById(R.id.price));
        EditText comment = (EditText) (findViewById(R.id.comment));

        String pricestr = price.getText().toString();
        String datestr = date.getText().toString();
        String commentstr = comment.getText().toString();
        String namestr = name.getText().toString();



        double Price = Double.parseDouble(pricestr);



        //checking date format
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date subscriptiondate = new Date();
        try {
            subscriptiondate = format.parse(datestr);

        } catch (ParseException e) {
            date.setError("DATE yyyy-mm-dd FORMAT");
        }

        java.sql.Date subdate = new java.sql.Date(subscriptiondate.getTime());


        Log.d(TAG, "Adding the Subscription");
        final ProgressDialog progressDialog = new ProgressDialog(AddNewSubscription.this, R.style.Theme_AppCompat_DayNight);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Adding Subscription .....");
        progressDialog.show();

        SubscriptionManager list = new SubscriptionManager();

        list.addSubscriptions(subdate, namestr, Price, commentstr);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                        done();
                    }
                }, 1200);
    }

    /**
     * Once done we navigate back MainActivity
     */
    private void done() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     *  shows the error message
     */
    public void error() {
        Button addbutton = (Button) (findViewById(R.id.AddButton));
        Toast.makeText(getBaseContext(),"Subscription Update Failed",Toast.LENGTH_LONG).show();
        //we enable the button because our error message was shown
        //user should new re input subscription
    }

    /**
     *
     * @return false or true depending on validity of user input
     */
    private boolean valid() {
        //initially valid is true
        boolean valid = true;
        EditText name = (EditText) (findViewById(R.id.name));
        EditText date = (EditText) (findViewById(R.id.date));
        EditText price = (EditText) (findViewById(R.id.price));
        EditText comment = (EditText)(findViewById(R.id.comment));

        //change these values to string in order to see if its empty
        //using the is empty funtions empty
        //dont have to check if comment is empty

        String Name = name.getText().toString();
        String Price = price.getText().toString();
        String Date = date.getText().toString();
        String Comment = comment.getText().toString();

        //checking the date format
        DateFormat dates = new SimpleDateFormat("yyyy-mm-dd");

        try {
            Date Dates = dates.parse(Date);

        } catch (ParseException e) {
            valid = false;
            date.setError("Date yyy-mm-dd FORMAT");
            return valid;
        }

        //checks if user actually enterd a price
        if (Price.isEmpty()) {
            valid = false;
            price.setError("Enter a price (CAD)");

        }


        //checks if price is not negative
        if(Price.contains("-")) {
            valid = false;
            price.setError("Enter a non negative price");
        }

        //Checks if user actually enterd a name

        if(Name.isEmpty()) {
            valid = false;
            name.setError("Enter a name");

        }

        //name up to 20 chars
        if(Name.length() > 20){
            valid = false;
            name.setError("Enter up to only 20 characters");

        }

        if(Comment.length() >30){
            valid = false;
            comment.setError("Enter up to only 30 characters");
        }
        return valid;




    }


}

