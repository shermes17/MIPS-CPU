package models;

/**
 *
 * The Program Counter contains the address for the current instruction being executed
 * The PC is updated by the Controller after every cycle
 * The PC updates the view object after each cycle
 *
 */

public class PC {

    private int address;
    // view object

    public void PC(){
        address = 0x0; // address starts at 0
        // update view
    }

    public int getAddress(){
        return address;
    }

    public void setAddress(int newAddress){
        this.address = newAddress;
        // update program counter in controller
    }


}
