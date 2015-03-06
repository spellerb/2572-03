/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment_03;

import java.util.Objects;

/**
 *
 * @author Barry Speller
 */
public class Address {
    private String street;
    private String city;
    private String state;
    private String zip;

    /**
     * Constructs an Address
     * @param street
     * @param city
     * @param state
     * @param zip 
     */
    public Address(String street, String city, String state, String zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    /**
     * Returns street (String) portion of the address
     * 
     * @return street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Returns the address' city (String)
     * 
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the state in which the Address is located
     * 
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * Returns the zip code in which the Address is located
     * @return 
     */
    public String getZip() {
        return zip;
    }

    /**
     *  Sets the street where the Address is located
     * 
     * @param street 
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Sets the city where the Address is located
     * 
     * @param city 
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Sets the state where the Address is located
     * 
     * @param state 
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Sets the zip where the Address is located
     * @param zip 
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.street);
        hash = 71 * hash + Objects.hashCode(this.city);
        hash = 71 * hash + Objects.hashCode(this.state);
        hash = 71 * hash + Objects.hashCode(this.zip);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Address other = (Address) obj;
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.zip, other.zip)) {
            return false;
        }
        return true;
    }

    /**
     * Returns the Address as a single string 
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "Address{" + "street=" + street + ", city=" + city + ", state=" + state + ", zip=" + zip + '}';
    }    
}
