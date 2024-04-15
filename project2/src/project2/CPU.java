package project2;

import project2.controllers.*;
import project2.views.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;


public final class CPU{

    public static void main(String[] args) {
    try{

        Scoreboard view = new Scoreboard();
        Controller controller = new Controller(view, "project2/output.dat");
        controller.runProcessor();
    }catch (IOException e) {
        System.err.println("Failed to initialize the Controller: " + e.getMessage());
    }

    }

}
