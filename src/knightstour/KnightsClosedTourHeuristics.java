package java.knightstour;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author markoc
 */
import java.util.Random;

public final class KnightsClosedTourHeuristics {
    //-------DECLARATION
    //board
    int[][] board;
    int[][] accessibility;
    String placeHolder = "·";
    String placeHolderVisited = "+";
    int lowestAccessibilityValue;
    int size = 8;
    //knight possible moves
    int[] horizontal    = {-1, -2, -2, -1,  1,  2, 2, 1};
    int[] vertical      = { 2,  1, -1, -2, -2, -1, 1, 2};
    //knights position
    int currentRow, currentColumn;
    
    public KnightsClosedTourHeuristics(int size){
        this.size = size;
        
        runTours();
        
    }
    
    private void runTours(){
            //starting position
            for(int r=0;r<size;r++){
                for(int c=0;c<size;c++){
                    initializeBoard();
                    heuristic();
                    System.out.println("Start row: "+r+"; column: "+c);
                    setStartingPosition(r,c);
                    run();
                    displayBoard(board);
                }
            }
    }
    
    private void run(){
        for(int i=0;i<size*size;i++){
            heuristic();
            nextMove();
        }
        
    }
    
    private void initializeBoard(){
        board = new int[size][size];
        for(int r=0;r<size;r++){
            for(int c=0;c<size;c++){
                board[r][c]=0;
            }
        }
    }
       
    private void setStartingPosition(int r, int c){
        currentRow = r;
        currentColumn = c;
        board[r][c] = 1;
    }
    
    private void moveKnight(int prevMove, int moveNumber){
        
        currentRow = currentRow+vertical[moveNumber];
        currentColumn = currentColumn+horizontal[moveNumber];
        board[currentRow][currentColumn] = prevMove+1;
    }
    
    private void nextMove(){
        int accessibilityNumber = Integer.MAX_VALUE;
        int moveNumber = -1;
        int tie_counter = 0;
        for(int i=0;i<horizontal.length;i++){
            
            int tempRow = currentRow+vertical[i];
            int tempColumn = currentColumn+horizontal[i];
            if(validatePositionAccessibility(tempRow,tempColumn)){
               
                if(accessibilityNumber>accessibility[tempRow][tempColumn]){
                    accessibilityNumber = accessibility[tempRow][tempColumn];
                    moveNumber = i;
                    tie_counter = 0;
                } else if (accessibilityNumber==accessibility[tempRow][tempColumn]){
                    if(tie_counter==0){
                        tie_counter += 2;
                    }else{
                        tie_counter += 1;
                    }
                } 
            }
        }
        
        if(tie_counter>0){
            moveNumber = findNextMoveFrom(currentRow, currentColumn, accessibilityNumber, tie_counter); //discover best move, from 0-7
            
        }
        
        if(moveNumber>=0){
            moveKnight(board[currentRow][currentColumn], moveNumber);
        }
    }
    
    //look for lowest accessibility number reachable and return it
    private int findNextMoveFromChild(int accessRow, int  accessColumn, int originalRow,int originalColumn){
        int moveNumber = -1;
        Random rand = new Random();
        
        int accessibilityNumber = Integer.MAX_VALUE;
        for(int i=0;i<horizontal.length;i++){
            int tempRow = accessRow+vertical[i];
            int tempColumn = accessColumn+horizontal[i];
            if(validatePositionAccessibility(tempRow,tempColumn)
                    && originalRow!=tempRow && originalColumn!=tempColumn){
                if(accessibilityNumber>accessibility[tempRow][tempColumn]){
                    moveNumber = i; //save which move number to take 0-7
                    
                    accessibilityNumber = accessibility[tempRow][tempColumn]; //save new accessibility number
                }
                /*
                if move accessibility number equals to next moves 
                accessibility number,choose between them by random
                */
                else if(accessibilityNumber==accessibility[tempRow][tempColumn] 
                        && rand.nextBoolean()){
                    accessibilityNumber = accessibility[tempRow][tempColumn];
                }
                
            }
        }
        
        return accessibilityNumber;
    }
    
    //look for lowest accessibility number from tied squares
    private int findNextMoveFrom(int accessRow, int  accessColumn, int accessibilityNumber, int  counter){
        int moveNumber = -1;
        int count = 0; //used as serial number for squares array and as locator
        //use counter as reference for how many tied squares we need to examine
        int[] squares = new int[counter];
        for(int i=0;i<horizontal.length;i++){
            int tempRow = accessRow+vertical[i];
            int tempColumn = accessColumn+horizontal[i];
            
            if(validatePositionAccessibility(tempRow,tempColumn)){
                /*
                if accessibilityNumber equals current squares accessibility
                value, it means its one of the tied squeres
                */
                
                if(accessibilityNumber==accessibility[tempRow][tempColumn]){
                    //find lowest accessibility value and save the value
                    //in the squares array
                    squares[count] = findNextMoveFromChild(tempRow,tempColumn,accessRow,accessColumn);
                    
                    //after saving the lowest access value of first tied square
                    //increase count by 1
                    count++;
                }
            }
        }
        
        /*
        to finish the method we need to return a moveNumber of the square with
        the lowest accessibility value from child square
        -
        loop thru squares and save the serial number to variable 'serialNum'
        */
        int serialNum = -1;
        Random rand = new Random();
        for (int i=0;i<squares.length-1;i++){
            if(squares[i]<squares[i+1]){
                serialNum = i;
            }
            //or save last serial if its smaller
            else if(squares[i]>squares[i+1]){
                serialNum = i+1;
            }
            else if(squares[i]==squares[i+1]){
                serialNum = (rand.nextBoolean()?i:i+1);
            }
        }
        //use serialNum to get to te location of the choosen tied square
        //reset count to 0
        count = 0;
        for(int i=0;i<horizontal.length;i++){
            int tempRow = accessRow+vertical[i];
            int tempColumn = accessColumn+horizontal[i];
            if(validatePositionAccessibility(tempRow,tempColumn)){
                if(accessibility[tempRow][tempColumn]==accessibilityNumber){
                    
                    //save i value to moveNumber when count meets serialNum
                    if(count==serialNum){
                        moveNumber = i;
                    }
                    //increase count by 1
                    count++;
                }
            }
        }
        
        return moveNumber;
    }
    private boolean validatePositionAccessibility(int row, int column){
        if(row>=0 && row<size && column>=0 && column<size && board[row][column]==0){
            return true;
        }
        else{
            return false;
        }
    }
    
    private void heuristic(){
        accessibility = new int[size][size];
        for(int r=0;r<size;r++){
            for(int c=0;c<size;c++){
                for(int m=0;m<horizontal.length;m++){
                    int tempRow = r+vertical[m];
                    int tempColumn = c+horizontal[m];
                    if(tempRow>=0 && tempRow<size
                            && tempColumn>=0 && tempColumn<size
                            && board[tempRow][tempColumn]==0){
                        if(board[r][c]==0){
                            accessibility[r][c] += 1;
                        }
                        else{
                            accessibility[r][c] = 0;
                        }
                    }
                }
            }
        }
        
    }
        
    private void displayBoard(int[][] board){
        //TOP Row
        System.out.printf("%3s"," ");
        for(int c=0;c<board[0].length;c++){
            System.out.printf("%3s",c);
        }
        System.out.println();
        for(int c=0;c<=board[0].length;c++){
            System.out.printf("%3s","—");
        }
        System.out.println();
        //TOP Row End
        
        for(int r=0;r<board.length;r++){
            //Left SIDE Row
            System.out.printf("%3s",r+"|");
            //Row Content
            for(int c=0;c<board[r].length;c++){
                System.out.printf("%3s",board[r][c]);
            }
            System.out.println();
            //Row Content End
        }
        System.out.println();
    }
}
