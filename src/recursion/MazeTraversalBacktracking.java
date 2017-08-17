/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursion;

import java.util.Random;

/**
 *
 * @author markoc
 */
public class MazeTraversalBacktracking {
    private String[][] map;
    private int startX,startY,endX,endY; // variables used to store coordinates of S and E
    private final String corridor = " ", brick = "#"; // represents objects in array
    private final String backtrack = "-", track = "+"; // represents tracks while solving
    private final int[] dirs = {0,1,2,3}; // used to calculate direction from current position
    private final int[] X = {0,1,0,-1}; // used with combination dirs
    private final int[] Y = {-1,0,1,0}; // used with combination dirs
    private final boolean[] bool = {true,true,true,false,true,true,true}; 
    private boolean exit = false; // when exit is set, change to true
    private int turnCount; // counts turns
    
    /*
    * Method used for importing a map
    */
    public void setMap(String[][] map, int startY, int startX, int endY, int endX){
        this.map = map;
        this.startY = startY;
        this.startX = startX;
        this.endX = endX;
        this.endY = endY;
    }
    /*
    * Recursive method that solves a maze
    */
    public boolean solve(int currY, int currX, boolean start){
        /*
        * Each time the method is called, 
        * first display the map but without indicators S and E
        */
        displayMap(false);
        /*
        * If we reached the end of the maze, the maze is solved.
        * return true to end recursion
        */
        if(currX==this.endX && currY==this.endY){
            return true;
        }
        /*
        * Only happens once, at the beginning. 
        * And marks the starting position as +
        */
        if(start){
            currX=this.startX; 
            currY=this.startY; 
            this.map[currY][currX] = this.track;
            start=false;
            /*
            * Recursive call
            */
            if(solve(currY, currX, start))
                return true;
        }
        else 
            /*
            * Happens after first recursive call until maze is solved
            */
        {
            /*
            * Finds a corridor that can be visited
            */
            for(int dir : this.dirs){
                int newCurrY = currY+Y[dir];
                int newCurrX = currX+X[dir];
                
                if(possibleMove(newCurrY,newCurrX)){
                    
                    this.map[newCurrY][newCurrX] = this.track;
                    
                    if(solve(newCurrY,newCurrX, start))
                        return true;
                    this.map[newCurrY][newCurrX] = this.backtrack;
                }
            }
        }
        
        return false;
    }
    /*
    * If a desired next coordinate is within map range and represents a
    * corridor that was not yet visited return true.
    */
    private boolean possibleMove(int y, int x){
        if(y>=0 && y<map.length
                && x>=0 && x<map[0].length
                && this.map[y][x]==" "){
            return true;
        }
        return false;
    }
    
    /*
    * Display the map, indicator argument determines if S and E are displayed
    */
    public void displayMap(boolean indicators){
        for(int y=0;y<map.length;y++){
            for(int x=0;x<map[0].length;x++){
                System.out.printf("%2s",(startY==y && startX==x && indicators)? "S" : (endY==y && endX==x && indicators)? "E" : map[y][x]);
            }
            System.out.printf("%n");
        }
        System.out.println();
    }
    
    public void mazeGenerator(int Y, int X){
        if(Y<5 || X<5)
            throw new IllegalArgumentException();
        
        int startY, startX;
        this.map = new String[Y][X];
        initializeMap();
        
        Random r = new Random();
        /*
        * Find starting point of the maze at random
        * Border columns and rows are ignored while maze is being built,
        * and are used only while positioning the entrance(start) and exit(E)
        *
        * First get a random row
        */
        startY = r.nextInt(Y-2)+1;
        /*
        * If start is on second row or on second last row
        * choose between columns >1 and <lenght-2
        */
        
        if(startY == 1 || startY == Y-2){
            startX = r.nextInt(X-2)+1;
        }
        else /*
            * If start is between second and second last row
            * choose between second or last column
            */
            startX = (r.nextBoolean())? 1 : X-2;
        /*
        * Save coordinates from where to start building the maze
        */
        this.startX = startX;
        this.startY = startY;
        /*
        * Update variables to create entrance(S)
        */
        if(startY==1){
            this.startY--;
        }
        else if(startX==1){
            this.startX--;
        }
        else if(startY==map.length-2){
            this.startY++;
        }
        else if(startX==map[0].length-2){
            this.startX++;
        }
        
        /*
        * Loop while maze does not fit function conditions, which
        * forces the maze to be recreated from the same S position
        */
        while(reGenMaze()){
            initializeMap();
            createCorridors(startY, startX);
        }
        
        /*
        * Create entrance
        */
        map[this.startY][this.startX] = this.corridor;
    }
    /*
    * Function initializes a empty map
    */
    private void initializeMap(){
        /*
        * Populate array with bricks
        */
        
        for(int y=0;y<this.map.length;y++){
            for(int x=0;x<this.map[0].length;x++){
                this.map[y][x] = this.brick;
            }
        }
        
        /*
        * Reset dependent variables
        */
        this.exit = false;
        this.endX = 0;
        this.endY = 0;
        this.turnCount = 0;
    }
    
    private boolean createCorridors(int y, int x){
        this.turnCount++;
        /*
        * Mark field at coordinates as corridor
        */
        map[y][x] = this.corridor;
        /*
        * If more than 40 percent of map is made, create exit
        */
        if(!exit && this.turnCount>map.length*map[0].length*0.2)
        createExit(y,x);
        /*
        * If neighbours of each directions current position are not
        * corridors then create a corridor in first such direction.
        * bool variable makes a 1/7 posibility not to take a valid direction.
        */
        Random r = new Random();
        for(int dir : this.dirs){
            int newY = y+Y[dir];
            int newX = x+X[dir];
            if(canBuildCorridor(y,x,newY,newX,dir) && bool[r.nextInt(bool.length)]){
                if(createCorridors(newY,newX))
                    return true;
            }
        }
        
        if(y==this.startY && x==this.startX){
            return true;
        }
        return false;
    }
    
    /*
    * return false if its a possibility to build parallel corridors, 
    * go out of the area otherwise return true and build a corridor
    */
    private boolean canBuildCorridor(int oldY, int oldX, int Y, int X, int direction){
        /*
        * If the coordinates are in allowed area 0<pos && pos<lastField
        * the for loop checks if there are already built corridors around that
        * position. If there are: return false, parallel corridors are not allowed
        */
        if(Y>0 && Y<map.length-1 && X>0 && X<map[0].length-1 && this.map[Y][X]==this.brick){
            /*
            * List through all possible directions
            */
            for(int dir : dirs){
                /*
                * Create new coordinates with given directions
                */
                int newY = Y+this.Y[dir];
                int newX = X+this.X[dir];
                /*
                * If corridor at new coordinates equals coordinates at old position
                * do nothing. Otherwise return false
                */
                if(map[newY][newX]==this.corridor){
                    if(newY==oldY && newX==oldX){
                    //do nothing 
                    }else
                        return false;
                        
                }
            }
            return true;
        }
        return false;
    }
    /*
    * Exit is created when location is next to the border
    */
    private void createExit(int y, int x){
        
        if(endY>0 || endX>0){
            this.map[endY][endX]=this.corridor;
            this.exit = true;
        }
        else if(y==1){endY=y-1;endX=x;}
        else if (y==map.length-2){endY=y+1;endX=x;}
        else if (x==1){endY=y;endX=x-1;}
        else if (x==map[0].length-2){endY=y;endX=x+1;}
    }
    /*
    * this function returns true if maze has
    * less than 40% of fields converted to corridors
    */
    private boolean reGenMaze(){
        int count = 0;
        for(int y=0;y<map.length-1;y++){
            for(int x=0;x<map[0].length-1;x++){
                if(map[y][x]==this.corridor)
                    count++;
            }
        }
        if(count<(map.length*map[0].length*0.4)){
            return true;
        }
        return false;
    }
}
