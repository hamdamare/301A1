package com.example.hamdamare.hamare301a1;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/*
Used Abram Hindle to help me with Saving Subscriptions
Also worked with Hamse Mare
 */

/**
 * Created by hamdamare on 2018-01-19.
 */

/**
 * @author hamdamare
 * @see SubscriptionListManager
 * @see AddNewSubscription
 *
 */
public class SaveSubscription {


    static final String prefFile = "Subscription";
    static final String slKey = "Subscription1";

    Context context;
    static private SaveSubscription savesubscription = null;



    /**
     * @param context context
     */
    public SaveSubscription(Context context) {
        this.context = context;
    }



    /**
     * @param context context
     */
    public static void initializeManager(Context context) {
        if (savesubscription == null) {
            if (context == null) {
                throw new RuntimeException("ERROR");
            }

            savesubscription = new SaveSubscription(context);

        }
    }


    /**
     *
     * @return SaveSubscription
     */
    public static SaveSubscription getManager() {
        if (savesubscription == null) {
            throw new RuntimeException("ERROR");
        }
        return savesubscription;
    }

    /**
     * @return new SubscriptionListManager
     * @throws ClassNotFoundException ClassNotFoundException
     * @throws IOException IOException
     * load the Subscription list from shared preference
     */
    public SubscriptionListManager loadSubscriptionList() throws ClassNotFoundException, IOException {
        SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
        String subscriptionlistdata = settings.getString(slKey, "");
        if (subscriptionlistdata.equals("")) {
            return new SubscriptionListManager();
        } else {
            return subscriptionListFromString(subscriptionlistdata);
        }

    }

    /**

     * @param subscriptionlistdata subscriptionlistdata
     * @return (SubscriptionListManager) InputStream.readObject()
     * @throws ClassNotFoundException  ClassNotFoundException
     * @throws IOException  IOException
     * returning subscriptionlist from serialized data
     */
    static public SubscriptionListManager subscriptionListFromString(String subscriptionlistdata) throws ClassNotFoundException, IOException {
        ByteArrayInputStream ByteInput = new ByteArrayInputStream(Base64.decode(subscriptionlistdata, Base64.DEFAULT));
        ObjectInputStream InputStream = new ObjectInputStream(ByteInput);
        return (SubscriptionListManager) InputStream.readObject();
    }


    /**
     * @param subscription_list subscription_list
     * @return Base64.encodeToString(Bytes, Base64.DEFAULT)
     * @throws IOException IOException
     * Writing data to be Serialized
     */
    static public String subscriptionListToString(SubscriptionListManager subscription_list) throws IOException {
        ByteArrayOutputStream ByteOutput = new ByteArrayOutputStream();
        ObjectOutputStream OutputStream = new ObjectOutputStream(ByteOutput);
        OutputStream.writeObject(subscription_list);
        OutputStream.close();
        byte Bytes[] = ByteOutput.toByteArray();
        return Base64.encodeToString(Bytes, Base64.DEFAULT);
    }



    /**
     * @param subscription_list subscription_list
     * @throws IOException IOException
     * saving the subscription list
     */
    public void saveSubscriptionlist(SubscriptionListManager subscription_list) throws IOException {
        SharedPreferences settings = context.getSharedPreferences(prefFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(slKey, subscriptionListToString(subscription_list));
        editor.commit();

    }
}


