package com.example.hamdamare.hamare301a1;
import java.io.IOException;
import java.sql.Date;

/*
Used Abram Hindle to help me with the subscription manager
Also worked with Hamse Mare
 */



/**
 * Created by hamdamare on 2018-01-19.
 */

/**
 * @author hamdamare
 * @see MainActivity
 * @see SaveSubscription
 */


public class SubscriptionManager {
    private static SubscriptionListManager savesubscriptionlist = null;


    /**
     * @return saveSubscriptionList
     * used to get subscription list at the start of MainActvity
     */
    static public SubscriptionListManager getSubscriptionList() {
        if (savesubscriptionlist == null) {
            try {
                savesubscriptionlist = SaveSubscription.getManager().loadSubscriptionList();
                savesubscriptionlist.addListener(new Listener() {
                    @Override
                    public void update() {
                        saveSubscripionList();
                    }
                });



            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("ERROR");
            }
            catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("ERROR");

            }
        }

        return savesubscriptionlist;
    }


    static public void saveSubscripionList() {
        try {
            SaveSubscription.getManager().saveSubscriptionlist(getSubscriptionList());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("ERROR");
        }
    }

    //Adding a subscription to subscriptionlist
    public void adddSubscription(Subscriptions subscription) {
        getSubscriptionList().addSubscription(subscription);
    }


    //adding a subscripton from add subscription
    public void addSubscriptions(Date date, String name, Double price, String comment) {
        Subscriptions subscription = new  Subscriptions(date, name, price, comment);
        adddSubscription(subscription);

    }

}
