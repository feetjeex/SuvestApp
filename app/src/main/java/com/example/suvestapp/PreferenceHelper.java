package com.example.suvestapp;

import java.io.Serializable;

/** PreferenceHelper
 *
 * A Class which is used to store preferences about which screenshots should be displayed (such as the retailer
 * or the color) to the user.
 */

public class PreferenceHelper implements Serializable {

    private String type, color, retailer, format;

    /** PreferenceHelper
     *
     * Constructor of the Class.
     */
    public PreferenceHelper(String type, String color, String retailer, String format) {

        this.type = type;
        this.color = color;
        this.retailer = retailer;
        this.format = format;
    }

    /** preferenceHelperResetter
     *
     * Resets all fields except for the type.
     */
    public void preferenceHelperResetter () {

        // Method used to reset all fields of a PreferenceHelper object
        this.color = "none";
        this.retailer = "none";
        this.format = "none";
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
