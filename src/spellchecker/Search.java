package spellchecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author markoc
 */
public class Search {
    Word word;
    int counter = 0;
    char[] c = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    HashMap<String, String> basket, b;

    ArrayList<String> wordList;

    
    public Search(String word, ArrayList<String> wordList){
        this.wordList = wordList;
        this.word = new Word(word);
        this.basket = new HashMap<>(54*this.word.getWord().length()+25);
    }
    
    public boolean check() throws IOException{
        
        if(this.wordList.contains(this.word)){
            return true;
        }
        run(this.word.getWord());
        b = new HashMap<>(basket);
        
        for(String s : b.keySet()){
            run(s);
        }
        
        extractResult(this.basket);
        return false;
    }
    
    private void run(String word){
        for(int i=0;i<=word.length();i++){
            
            if(i<word.length())
                addResult(removeChar(word,i));
            
            if(i<word.length()-1)
                addResult(yeOldeSwitcheroo(word,i));
            
            for(char c : this.c){
                addResult(insertChar(word, i, c));
                
                if(i<word.length())
                addResult(replaceChar(word, i, c));
            }
        }
    }
    
    private void addResult(String result){
                basket.put(result, result);
    }
    
    private void extractResult(HashMap<String, String> basket) throws IOException{
        for(String s : basket.keySet()){
            if(wordList.contains(s)){
                this.word.addWordCorrection(s);
            }
        }
        this.word.sortCorrections();
    }
    
    private String yeOldeSwitcheroo(String input,int pos){
//        System.err.println(input.substring(0, pos) 
//                + input.charAt(pos+1) 
//                + input.charAt(pos)
//                + input.substring(pos+2, input.length()));
        return input.substring(0, pos) 
                + input.charAt(pos+1) 
                + input.charAt(pos)
                + input.substring(pos+2, input.length());
    }
    
    private String insertChar(String input, int pos, char c){
//        System.err.println(input.substring(0,pos)
//                + c
//                + input.substring(pos, input.length()));
        return input.substring(0,pos)
                + c
                + input.substring(pos, input.length());
    }
    
    private String replaceChar(String input, int pos, char c){
//        System.err.println(input.substring(0, pos)
//                + c
//                + input.substring(pos+1));
        return input.substring(0, pos)
                + c
                + input.substring(pos+1);
    }
    private String removeChar(String input,int pos){
//        System.err.println(input.substring(0, pos)+input.substring(pos+1));
        return input.substring(0, pos)+input.substring(pos+1);
    }
    
    public Word word(){
        return this.word;
    }


}
