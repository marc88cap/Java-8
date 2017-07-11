/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spellchecker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author markoc
 */
public class Dictionary {
    final private String location = "/usr/share/dict/";
    private String dict;
    private ArrayList<String> wordLines = new ArrayList<>(0);
    
    public Dictionary() {
        this("american-english"); //default dictionary
    }
    
    public Dictionary(String dict){
        this.dict = dict;
        
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.location+this.dict));
            br = new BufferedReader(new FileReader(this.location+this.dict));
            
            while(br.ready()){
                String line = br.readLine();
                this.wordLines.add(line);
            }            
            
            System.out.printf("%d lines stored in a file %s at location %s%n", wordLines.size(), this.dict, this.location);
            System.out.printf("Capacity of %d was ensured for wordLines variable%n", wordLines.size());
            br.close();
        }
        catch (IOException e){
            System.err.println(e);
        }
    }
    
    public ArrayList<String> getArrayList(){
        return this.wordLines;
    }
}
