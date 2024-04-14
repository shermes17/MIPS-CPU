package project2.models;

import project2.views.*;
import project2.controllers.*;


/**
 *
 * The Program Counter contains the address for the current instruction being executed
 * The PC is updated by the Controller after every cycle
 * The PC updates the view object after each cycle
 *
 */

public class PC {

    private int address;

    public void PC(){
        address = 0; // address starts at 0
    }

    public int getAddress(){
        return address;
    }

    public void setAddress(int newAddress){
        this.address = newAddress;
    }


}
