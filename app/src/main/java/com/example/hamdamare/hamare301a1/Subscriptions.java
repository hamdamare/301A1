package com.example.hamdamare.hamare301a1;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hamdamare on 2018-01-19.
 */



/**
 * @author hamdamare
 * @see java.io.Serializable
 * @see EditSubscription
 * @see AddNewSubscription
 */

public class Subscriptions implements Serializable {
    private static final long serialVersionUID = 1L;


    //Initializing the values for the constructors//
    public java.util.Date Date;
    public String name;
    public Double price;
    public String comment;
    public Double Totalcost;
    public int subscriptionsid;





    /**
     * @param Date date
     * @param name name
     * @param price price
     * @param comment comment
     *Constructor for the Subscriptions class
     */
    public Subscriptions (java.util.Date Date, String name, Double price, String comment) {
        this.Date = Date;
        this.name = name;
        this.price = price;
        this.comment = comment;
        this.Totalcost = Totalcost;
        int random = (int )(Math.random() * 500000000 + 1);
        this.subscriptionsid = random;

    }



    /**
     * @return this.Subscriptions_Id
     * The Get_id returns a unique id for each subscription added
     */
    public int getID() {
        return this.subscriptionsid;
    }




    /**
     * @param subscription subscription
     * @return subscription.Date
     */
    public Date getDate(Subscriptions subscription) {
        return subscription.Date;
    }



    /**
     * @return ("DATE: "+date_toString+"\n"+ "Name: "+name_str +" Price: "+ price_str.toString()+" \n "+" comment: " + comment_str)
     * use the toString function to print values of each log in the list
     */
    public String toString(){
        Date date_str= this.Date;
        String name_str = this.name;
        Double price_str = this.price;
        String comment_str= this.comment;
        Double Totalcost_str = this.Totalcost;


        //putting the date in a proper string format//
        String date_toString;
        SimpleDateFormat dateformatJava = new SimpleDateFormat("yyyy-MM-dd");
        date_toString = dateformatJava.format(date_str);

        return("DATE: "+date_toString+"\n"+ "Name: "+name_str +" Price: "+ price_str.toString()+" \n "+" comment: " + comment_str);
    }




    /**
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @return price
     */
    public Double getPrice() {
        return this.price;
    }

    /**
     *
     * @return comment
     */
    public String getComment() {
        return this.comment;
    }

    /**
     *
     * @return Date
     */
    public Date getDate(){
        return this.Date;
    }



    /**
     * @param comparesubscription comparesubscription
     * @return true or false depending on whether or not two subscriptions are equal
     */
    public boolean equals(Object comparesubscription) {
        if (comparesubscription != null && comparesubscription.getClass() == this.getClass()){
            return this.equals((Subscriptions )comparesubscription);
        }

        else {
            return false;
        }
    }



    /**
     * @param comparesubscription comparesubscription
     * @return true or false depending on if two subscriptions Unique id are the same
     */
    // REQUIREMENT: Checking if two subscriptions are equal//
    public boolean equals(Subscriptions comparesubscription) {
        if (comparesubscription != null){
            return false;
        } else {

            return getID()==comparesubscription.getID();
        }
    }



    /**
     * @return Subscriptions ID: "+getName()).hashCode()
     * if invoked on the same objet more than once during execution hashcode returns same integer
     */
    public int hashCode() {
        return ("Subscriptions ID: "+getName()).hashCode();
    }


}
