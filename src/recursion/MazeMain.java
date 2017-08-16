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
public class MazeMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
// manualy created maps
//        String[][] map = 
//        {
//            {"#","#","#","#","#","#","#","#","#","#","#","#"},
//            {"#"," "," "," ","#"," "," "," "," "," "," ","#"},
//            {" "," ","#"," ","#"," ","#","#","#","#"," ","#"},
//            {"#","#","#"," ","#"," "," "," "," ","#"," ","#"},
//            {"#"," "," "," "," ","#","#","#"," ","#"," "," "},
//            {"#","#","#","#"," ","#"," ","#"," ","#"," ","#"},
//            {"#"," "," ","#"," ","#"," ","#"," ","#"," ","#"},
//            {"#","#"," ","#"," ","#"," ","#"," ","#"," ","#"},
//            {"#"," "," "," "," "," "," "," "," ","#"," ","#"},
//            {"#","#","#","#","#","#"," ","#","#","#"," ","#"},
//            {"#"," "," "," "," "," "," ","#"," "," "," ","#"},
//            {"#","#","#","#","#","#","#","#","#","#","#","#"}
//        };
 
//        String[][] map = 
//        {
//            {"#","#","#"," ","#"},
//            {" "," ","#"," ","#"},
//            {"#"," ","#"," ","#"},
//            {"#"," ","#"," ","#"},
//            {"#"," "," "," ","#"},
//            {"#","#","#"," ","#"},
//            {"#"," "," "," ","#"},
//            {"#"," ","#","#","#"},
//            {"#"," "," "," ","#"},
//            {"#","#","#","#","#"},
//        };

        MazeTraversalBacktracking maze = new MazeTraversalBacktracking();
//        maze.setMap(map, 1, 0, 0, 3); // used to import a manualy created map
        maze.mazeGenerator(12,12);
        maze.displayMap(true);
        if(maze.solve(0, 0, true)){
            System.out.printf("%2s%n","Maze solved!");
        }
        else{
            System.out.printf("%2s%n","Maze CAN NOT be solved. :/");
        }
        maze.displayMap(true);
    }
    
}
