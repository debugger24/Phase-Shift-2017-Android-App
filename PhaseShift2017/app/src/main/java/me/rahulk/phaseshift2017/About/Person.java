package me.rahulk.phaseshift2017.About;

/**
 * Created by rahulkumar on 03/08/17.
 */

public class Person {
    private String name;
    private String mobileNumber;
    private String social;
    private int pic;

    public Person(String name, String mobileNumber, String social, int pic) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.social = social;
        this.pic = pic;
    }

    public String getName() {
        return this.name;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public String getSocial() {
        return this.social;
    }

    public int getPic() {
        return this.pic;
    }
}
