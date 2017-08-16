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
public class EightQueens {
    int[][] board = new int[8][8];
    public EightQueens(){
        if(Solver(1)){
            displayBoard(board);
        }
        else{
            System.out.printf("%s%n","No solutions possible");
        }
    }
    
    private boolean Solver(int queenNum){
        
        if(queenNum == 9){
            return true;
        }
            for(int y=0;y<8;y++){
                for(int x=0;x<8;x++){
                    if(canPlaceQueenOn(this.board, y, x)){
                        this.board[y][x] = queenNum;
//                         displayBoard(board); // uncomment to print steps
                        if(Solver(queenNum+1)){
                            return true;
                        }
                        board[y][x] = 0;
                    }
                    
                    
                }
            }
        return false;
    }
    
    private boolean canPlaceQueenOn(int[][] matrix, int rootY, int rootX){
        for(int i=0;i<8;i++){
            //is the column free
            if(matrix[i][rootX] != 0 ){
                return false;
            }
            //is the row free
            if(matrix[rootY][i] != 0 ){
                return false;
            }
            
            //is lower diagonal free
            if(rootX+rootY-i >= 0 && rootX+rootY-i < 8
                    && matrix[i][rootX+rootY-i] != 0){
                return false;
            }
            //is upper diagonal free
            if(rootX-rootY+i >= 0 && rootX-rootY+i < 8
                    && matrix[i][rootX-rootY+i] != 0){
                return false;
            }
        }
        return true;
    }
    
    private void displayBoard(int[][] board){
        
        System.out.printf("%-1s| %-1d %-1d %-1d %-1d %-1d %-1d %-1d %-1d%n"
                + "-----------------%n"," ", 0,1,2,3,4,5,6,7);
        for(int y=0;y<8;y++){
            System.out.printf("%d| ",y);
            for(int x=0;x<8;x++){
                System.out.printf("%s ",board[y][x]);
            }
            System.out.printf("%n");
        }
    }
}
