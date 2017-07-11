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
import java.util.Scanner;
public final class KnightsTour {

    //-------DECLARATION
    //board
    String[][] board;
    String[][] tempBoard;
    String placeHolder = "·";
    String placeHolderVisited = "+";
    String knight = "K";
    int size;
    //knight possible moves
    int[] horizontal    = {-1, -2, -2, -1,  1,  2, 2, 1};
    int[] vertical      = { 2,  1, -1, -2, -2, -1, 1, 2};
    //knights position
    int currentRow, currentColumn, prevRow, prevColumn;
    //-------DECLARATION end
    
    //Constructor
    public KnightsTour(int size){
        //size of board. eg 8 = 8x8
        this.size = size;
        setRandomPosition();
        prepareBoard(size);
        positionKnight();
        displayBoard();
    }
    //Constructor end
    private void prepareBoard(int size){
       board = new String[size][size];
       tempBoard = new String[size][size];
       for(int r=0;r<size;r++){
           for(int c=0;c<size;c++){
               board[r][c] = placeHolder;
           }
       }
    }
    
    private void displayBoard(){
        refreshTempBoard();
        findPossibleMoves();
        
        //TOP Row
        System.out.printf("%2s"," ");
        for(int c=0;c<tempBoard[0].length;c++){
            System.out.printf("%2s",c);
        }
        System.out.println();
        for(int c=0;c<=tempBoard[0].length;c++){
            System.out.printf("%2s","—");
        }
        System.out.println();
        //TOP Row End
        
        for(int r=0;r<tempBoard.length;r++){
            //Left SIDE Row
            System.out.printf("%2s",r+"|");
            //Row Content
            for(int c=0;c<tempBoard[r].length;c++){
                System.out.printf("%2s",tempBoard[r][c]);
            }
            System.out.println();
            //Row Content End
        }
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Insert move type: ");
        int moveType= sc.nextInt();
        
        moveKnight(moveType);
    }
    
    private void setRandomPosition(){
        Random rand = new Random();
        
        currentRow = rand.nextInt((size+1)-1);
        currentColumn = rand.nextInt((size+1)-1);
    }
    
    private void setNewPosition(int moveType){
        prevRow = currentRow;
        prevColumn = currentColumn;
        currentRow += vertical[moveType];
        currentColumn += horizontal[moveType];
        positionKnight();
    }
    
    private void positionKnight(){
        board[currentRow][currentColumn] = knight;
    }
    
    private void findPossibleMoves(){
        
        for(int moveType=0;moveType<size;moveType++){
            int tempRow = currentRow+vertical[moveType];
            int tempColumn = currentColumn+horizontal[moveType];
            if(tempRow>=0 && tempRow<size
                    && tempColumn>=0 && tempColumn<size
                    && board[tempRow][tempColumn]==placeHolder){
                tempBoard[tempRow][tempColumn] = String.valueOf(moveType);
            }
        }
        
        
    }
    
    private void refreshTempBoard(){
        for(int r=0;r<board.length;r++){
            for(int c=0;c<board[r].length;c++){
                tempBoard[r][c] = board[r][c];
            }
        }
    }
    
    
    public void moveKnight(int moveType){
        if(moveType<0 && moveType>size-1)
            throw new IllegalArgumentException("Move type must be between 0 and "+(size-1));
        if(validateMoveType(moveType)){
            board[currentRow][currentColumn] = placeHolderVisited;
            setNewPosition(moveType);
            displayBoard();
        }
        displayBoard();
    }
    
    private boolean validateMoveType(int moveType){
        int tempRow = currentRow+vertical[moveType];
        int tempColumn = currentColumn+horizontal[moveType];
        if(tempRow>=0 && tempRow<size
                && tempColumn>=0 && tempColumn<size
                && board[tempRow][tempColumn]!=placeHolderVisited){
            return true;
        }
        return false;
    }
   
}
