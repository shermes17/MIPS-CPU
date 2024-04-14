package project2.views;

import project2.controllers.*;
public class ScoreBoard  {

    public Controller controller;
    private int numReads;
    private int numWrites;
    private int numCycles;


   public void Scoreboard(Controller controller){
       this.controller = controller;

       this.numWrites = 0;
       this.numReads = 0;
       this.numCycles = 0;

    }


    public void addReads(){
       numReads++;
    }
    public void addWrites(){
       numWrites++;
    }









}