/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recursion;

/**
 *
 * @author markoc
 */
public class MazeTraversalBacktracking1 {
    private String[][] maze;
    private int startLocX, startLocY;
    private final int[] dir = {0,1,2,3}; //numbers in order: U, R, D, L
    private final int[] X = {0,1,0,-1};
    private final int[] Y = {-1,0,1,0};
    public void setMaze(String[][] maze, int startLocX, int startLocY){
        this.maze = maze;
        this.startLocX = startLocX;
        this.startLocY = startLocY;
    }
    
    public void startMaze(){
        if(mazeTraversal(maze, this.startLocX, this.startLocY, 0)){
            System.out.printf("%n%s%n", "The exit has been found! Congratulations!");
        }
        else{
            System.out.printf("%n%s%n", "The maze does not have an exit. Shame!");
        }
    }
    
    private boolean mazeTraversal(String[][] maze, int x, int y, int direction){
        if(y==maze.length-1 || y==maze[0].length-1 
                && x!=startLocX && y!=startLocY 
                && maze[x][y]=="."){
            return true;
        }
        else if(possibleMove(maze,x,y,direction)){ //TODO: delete ... ker mora sprobati vsak move vse smeri
            if(move(maze,x,y,direction))
                return true;
        }
        else{
            for(int d : dir){
                if(direction!=d && possibleMove(maze, x, y, d)){
                    if(move(maze,x,y,d))
                        return true;
                }
            }
        }
        return false;
    }
    
    private boolean move(String[][] maze, int x, int y, int d){
        maze[y+Y[d]][x+X[d]] = "+";
        System.out.printf("%n%s%n", "One step closer!");
        displayMaze();
        if(mazeTraversal(maze, x+X[d], y+Y[d], d))
            return true;
        maze[y+Y[d]][x+X[d]] = "-";
        System.out.printf("%n%s%n", "Dead-end! Backing up...");
        displayMaze();
        return false;
    }
    
    private boolean possibleMove(String[][] maze, int x, int y, int d){
        if(y+Y[d]>=0 && x+X[d]>=0 && maze[y+Y[d]][x+X[d]]==" "){
            System.out.printf("%n%s %s%n","You can move", (d==0)?"up":(d==1)?"right":(d==2)?"down":"left");
            return true;
        }
        return false;
    }
    
    private void displayMaze(){
        for(int y=0;y<maze.length;y++){
            for(int x=0;x<maze[0].length;x++){
                System.out.printf("%s", maze[y][x]);
            }
            System.out.println();
        }
    }
    
}
