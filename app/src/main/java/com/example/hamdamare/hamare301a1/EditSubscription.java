package com.example.hamdamare.hamare301a1;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hamdamare
 * @see SubscriptionManager
 * @see MainActivity
 *
 *
 *This class is responsible for replacing old user input with the new edited user input, when user clicks edit
 * we navigate to this page and update the listview content with this new information
 */

public class EditSubscription extends AppCompatActivity {


    public static int position;

    /**
     * Override
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        Subscriptions subscription = (Subscriptions) intent.getSerializableExtra("My_subscription");
        position = intent.getIntExtra("Pos",1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subscription);



        printSubscription(subscription,position);



    }

    /**
     *
     * @param subscription subscription
     * @param position position
     *sets the user inputted text(The old subscription info) on the editinfo activity
     *so they can edit what they enterd
     */
    @SuppressLint("SetTextI18n")
    private void printSubscription(Subscriptions subscription, int position) {

        String Oname = subscription.name;
        Double Oprice = subscription.price;
        Date Odate = subscription.Date;
        String Ocomment = subscription.comment;


        SimpleDateFormat dateformatJava = new SimpleDateFormat("yyyy-MM-dd");
        String date_to_string = dateformatJava.format(Odate);





        EditText price = (EditText) findViewById(R.id.price);
        price.setText(Oprice.toString(), TextView.BufferType.EDITABLE);

        EditText comment = (EditText) findViewById(R.id.comment);
        comment.setText(String.valueOf(Ocomment));

        EditText name = (EditText) findViewById(R.id.name);
        name.setText(Oname);

        EditText date = (EditText) findViewById(R.id.date);
        date.setText(date_to_string);



    }

    /**
     *
     * @param view view
     */

    public void editSubscription(View view) {

        //REQUIREMENT:show error message when input is not correct format
        if (!valid()) {
            error();
            return;
        }


        EditText name = (EditText) (findViewById(R.id.name));
        EditText date = (EditText) (findViewById(R.id.date));
        EditText price = (EditText) (findViewById(R.id.price));
        EditText comment = (EditText) (findViewById(R.id.comment));

        //converting user input to string
        String nameS = name.getText().toString();
        String commentS = comment.getText().toString();
        String priceS = price.getText().toString();
        String dateS = date.getText().toString();


        double Price = Double.parseDouble(priceS);

        //check date format
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date subscription_date = new Date();
        try {
            subscription_date = format.parse(dateS);
        } catch (ParseException e) {
            date.setError("DATE yyyy-mm-dd FORMAT");
        }


        //changing java.util to java.sql
        java.sql.Date sub_date = new java.sql.Date(subscription_date.getTime());



        final ProgressDialog progressDialog = new ProgressDialog(EditSubscription.this,R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Editing Your Subscriptions .....");
        progressDialog.show();

        SubscriptionManager.getSubscriptionList().removeSubscription(position);
        SubscriptionManager list = new SubscriptionManager();
        list.addSubscriptions(sub_date,nameS,Price,commentS);


        //delay the navigation to screen with a message
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                        done();
                    }
                }, 3000);

    }


    /**
     * shows the error message
     */
    public void error(){
        Button add_subscription_button = (Button) (findViewById(R.id.AddButton));
        Toast.makeText(getBaseContext(),"Subscription Update Failed",Toast.LENGTH_LONG).show();

    }

    /**
     *
     * @return true or false depending on validity of user input
     */
    private boolean valid() {
        //No comment needed here
        boolean valid = true;

        EditText name = (EditText) (findViewById(R.id.name));
        EditText date= (EditText) (findViewById(R.id.date));
        EditText price = (EditText) (findViewById(R.id.price));
        EditText comment = (EditText)(findViewById(R.id.comment));



        String Name=name.getText().toString();
        String Date = date.getText().toString();
        String Price = price.getText().toString();
        String Comment = comment.getText().toString();





        DateFormat dates= new SimpleDateFormat("yyyy-mm-dd");
        //DATE CHECK
        try {
            Date Dates = dates.parse(Date);
        } catch (ParseException e) {
            valid = false;
            date.setError("DATE yyyy-mm-dd FORMAT");
            return valid;
        }

        if (Name.isEmpty()){
            valid=false;
            name.setError("Enter a Name");
        }
        if(Name.length() >20){
            valid = false;
            name.setError("Enter only up to 20 characters");
        }

        if (Price.isEmpty()){
            valid = false;
            price.setError("Enter a Price");
        }

        if(Comment.length() > 30){
            valid = false;
            comment.setError("Enter only up to 30 characters");
        }
        return valid;
    }


    /**
     * When done we return to MainActivity
     */
    private void done() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
