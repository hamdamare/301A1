package com.example.hamdamare.hamare301a1;
import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author hamdamare
 * @see SaveSubscription
 * @see EditSubscription
 */

public class MainActivity extends Activity {


    /**
     *
     * @param savedInstanceState
     * savesubcription initialize manager is called to retrieve the old subscription
     * @Override
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        SaveSubscription.initializeManager(this.getApplicationContext());


        //getListView() instead of findviewbyid() because of the error
        ListView listView1 = (ListView) findViewById(R.id.listview);


        Collection<Subscriptions> my_subscriptions = SubscriptionManager.getSubscriptionList().getSubscription();
        final ArrayList<Subscriptions> list = new ArrayList<Subscriptions>(my_subscriptions);
        final ArrayAdapter<Subscriptions> my_subscriptionArrayAdapter = new ArrayAdapter<Subscriptions>(this, android.R.layout.simple_list_item_1,list);
        listView1.setAdapter(my_subscriptionArrayAdapter);
        TextView edit_charge= findViewById(R.id.tcText);
        edit_charge.setText(String.format("$%.2f", totalCost(list)));



        SubscriptionManager.getSubscriptionList().addListener(new Listener() {

            /**
             * Override
             * this updates the subscription list after user input
             */
            @Override
            public void update() {
                list.clear();
                Collection<Subscriptions> subscription1 = SubscriptionManager.getSubscriptionList().getSubscription();
                list.addAll(subscription1);
                my_subscriptionArrayAdapter.notifyDataSetChanged();

                TextView edit_charge= findViewById(R.id.tcText);
                edit_charge.setText(String.format("$%.2f", totalCost(list)));


            }
        });





        //when item is clicked on for a long click it displays edit,return, or delete
        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            /**
             *
             * @param adapterView adapterView
             * @param view   view
             * @param position
             * @param id
             * @return true or false depending on user longclick
             */
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long id) {
                AlertDialog.Builder message = new AlertDialog.Builder(MainActivity.this);
                message.setMessage("Edit or remove this subscription");
                message.setCancelable(true);
                final int pos= position;



                //return just closes the message
                message.setPositiveButton("Return", new DialogInterface.OnClickListener() {
                    /**
                     *
                     * @param dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //new intent new view
                    }
                });




                //Editing the subscription from our subscription list
                message.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                    /**
                     *
                     * @param dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Subscriptions my_subscription_info = list.get(position);
                        editInfo(my_subscription_info,pos);
                    }
                });



                //removing subscription from out subscription list
                message.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    /**
                     *
                     * @param dialog
                     * @param which
                     */
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //delete the subscription
                        Subscriptions my_subscription = list.get(position);
                        String name = my_subscription.name;
                        SubscriptionManager.getSubscriptionList().removeSubscription(pos);
                    }
                });


                message.show();
                return false;
            }

        });



    }

    /**
     *
     * @param view to AddNewSubcription
     */
    //navigates to the Add_subscription  class
    public void done(View view){

        Intent intent = new Intent(this, AddNewSubscription.class);
        startActivity(intent);
    }


    /**
     *
     * @param my_subscription_info from subscriptions class
     * @param pos position of user click
     */
    //This function allows user to edit their subscription
    private void editInfo(Subscriptions my_subscription_info, Integer pos) {
        Intent intent = new Intent(MainActivity.this, EditSubscription.class);
        intent.putExtra("My_subscription",my_subscription_info);
        intent.putExtra("Pos",pos);
        startActivity(intent);

    }

    public Double totalCost(ArrayList<Subscriptions> list) {
        Double totalcost = 0.0;
        int i;
        for(i=0;i<list.size();i++) {
            double cost = list.get(i).getPrice();
            totalcost += cost;

        }
        return totalcost;
    }






    //on options selected thing here for action bar clicks
    //if this code doesnt work then implement this


    //if back is pressed

    /**
     * Override
     * onBackpressed
     *
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}
