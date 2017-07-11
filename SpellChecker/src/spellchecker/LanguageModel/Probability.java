/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spellchecker.LanguageModel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author markoc
 */
public class Probability {
    private HashMap<String, Integer> words = new HashMap<>(99999);
    private HashMap<String, Double> wP;
    
    public Probability() throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader("/home/markoc/NetBeansProjects/GitHub/Java-8/SpellChecker/src/spellchecker/LanguageModel/big.txt"));
        while(br.ready()){
            String[] line = br.readLine().split("\\W|_");
            for(String word : line){
                if(this.words.containsKey(word))
                    this.words.put(word,this.words.get(word)+1);
                else
                    this.words.put(word,1);
            }
        }
    }
    
    public Double getP(String word){
        int N = 0;
        for(int i : this.words.values()){
            N += i;
        }
        if(this.words.containsKey(word))
            return (double)this.words.get(word)/N;
        else
            return 0.0;
    }
    
    public HashMap<String, Double> cP(ArrayList<String> w){
        wP = new HashMap<>(w.size());
        for(String s : w){
            wP.put(s, getP(s));
        }
            
        return wP;
    }
    
    public int returnCount(String word){
        return this.words.get(word);
    }
}
