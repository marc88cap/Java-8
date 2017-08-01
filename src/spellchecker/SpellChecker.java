package spellchecker;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author markoc
 */
public class SpellChecker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        Dictionary dict = new Dictionary();
        ArrayList<String> wordList = dict.getArrayList();
        System.out.print("Input a word to spellcheck: ");
        String input = sc.nextLine();
        Search s = new Search(input, wordList);
        try{     
            if(s.check()){
                System.out.println("Word is spelled correctly.");
            }
            else{
                System.out.println("Word is not spelled correctly.");
                System.out.println("Starting search sequence....");
                
                if(s.word().getWordCorrections().size()==1 && s.word().getWordCorrections().get(0)!=null)
                    System.out.printf("Did you mean \'%s\'?%n",s.word().getWordCorrections().get(0));
                
                else if(s.word().getWordCorrections().size()>1){
                    System.out.printf("%d suggestions found: %n", s.word().getWordCorrections().size());
                    for(String w : s.word().getWordCorrections()){
                        System.out.printf("%s%n",w);
                    }
                }
                else{
                    System.out.printf("%d corrections were found. :'(%n",0);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
}
