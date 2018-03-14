package com.example.hamdamare.hamare301a1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/*
Used Abram Hindle to help me with the subscription list manager
Also worked with Hamse mare
 */


/**
 * Created by hamdamare on 2018-01-19.
 */


/**
 * @author hamdamare
 * @see java.io.Serializable
 */

public class SubscriptionListManager  implements Serializable {

    private static final long serialVersionUID = 6673446047991058932L;
    ;
    protected ArrayList<Subscriptions> subscriptionlist = null;

    protected transient ArrayList<Listener> listener = null;


    //constructor for the Subscription_list
    public SubscriptionListManager() {
        subscriptionlist = new ArrayList<Subscriptions>();
        listener = new ArrayList<Listener>();
    }



    /**
     *
     * @return listener
     */
    private ArrayList<Listener> getListener() {
        if (listener == null) {
            listener = new ArrayList<Listener>();
        }
        return listener;
    }



    /**
     * @return subscriptionlist
     */
    public Collection<Subscriptions> getSubscription() {
        return subscriptionlist;
    }

    //adds the subscription and updates the listener
    public void addSubscription(Subscriptions subscription) {
        subscriptionlist.add(subscription);
        notifyListener();
    }


    //updates the listener
    public void notifyListener() {
        for (Listener listener : listener) {
            listener.update();
        }
    }

    //removing subscription and updating the listener
    public void removeSubscription(int subscription) {
        subscriptionlist.remove(subscription);
        notifyListener();
    }


    //replace subscriptions withthe new edit info subscription
    public void replace(Subscriptions Oldsubscription, Subscriptions NewSubscription) {
        subscriptionlist.remove(Oldsubscription);
        subscriptionlist.add(NewSubscription);
        notifyListener();
    }



    /**
     * @return
     * returns the size of the subscription list
     */
    public static int size() {
        return SubscriptionListManager.size();
    }


    //adding a listener
    public void addListener(Listener l) {
        getListener().add(l);
    }

    //empty subscription exception
    public class emptySubscriptionException extends Exception {
        private static final long serialVersionUID = -2417935479828663701L;
    }
}

