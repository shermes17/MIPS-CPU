package project2;

import project2.controllers.*;
import project2.views.*;

import java.io.IOException;


public final class CPU{

    public static void main(String[] args) {
    try{
        ScoreBoard view = new ScoreBoard();
        Controller controller = new Controller(view, "output.dat");
        controller.runProcessor();
    }catch (IOException e) {
        System.err.println("Failed to initialize the Controller: " + e.getMessage());
    }

    }

}
