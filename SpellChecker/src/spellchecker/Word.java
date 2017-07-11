/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spellchecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;
import spellchecker.LanguageModel.Probability;

/**
 *
 * @author markoc
 */
public class Word{
    private final String word, trimmed;
    private ArrayList<String> matches = new ArrayList<String>(5);
    public Word(){
        this.word = "";
        this.trimmed = "";
    }
    
    public Word(String word){
        this.word = word;
        this.trimmed = trimChars(word);
    }

    public String getWord(){
        return this.word;
    }
    
    public String getTrimmed(){
        return this.trimmed;
    }
    
    public boolean equals(Object o){
        String w = (String)o;
        return this.word.equals(w.toString());
    }
    /**
     *
     * @param word a word to be trimmed
     * 
     */
    private String trimChars(String word){
        return word.replaceAll("(?i)(.)\\1+","$1").replaceAll("(?i)(..)\\1+","$1");
    }
    
    /**
     *
     * @return all matches
     */
    public ArrayList<String> getWordCorrections(){
        return this.matches;
    }
    
    public void sortCorrections() throws IOException{
        Probability p = new Probability();
        
        HashMap<String, Double> wP = p.cP(getWordCorrections());
        DoubleComparator dC = new DoubleComparator(wP);
        TreeMap<String, Double> sorted = new TreeMap<>(dC);
        this.matches.clear();
        sorted.putAll(wP);
        for(String s : sorted.keySet()){
            this.matches.add(s);
        }
    }
    
    /**
     *
     * @param word that is added to collection of matches
     */
    public void addWordCorrection(String word){
        this.matches.add(word);
    }
    private static class DoubleComparator implements Comparator<String> {
        private HashMap<String, Double> basket;
        public DoubleComparator(HashMap<String,Double> basket) {
            this.basket = basket;
        }

        @Override
        public int compare(String o1, String o2) {
            if(this.basket.get(o1)>=this.basket.get(o2))
                return -1;
            else
                return 1;
        }
    }
}

    