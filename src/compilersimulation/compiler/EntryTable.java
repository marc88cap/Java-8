/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.compiler;

/**
 *
 * @author markoc
 */
public class EntryTable {
    public int symbol; // unicode representation
    public char type; // C = constant, L = line number, V = variable
    public int location; // location in a memory 

    
    public EntryTable(int symb, char typ, int loc){
	this.symbol = symb;
	if(checkType(typ))
	    this.type = typ;
	this.location = loc;
    }
    
    public int getSymbol(){
	return this.symbol;
    }
    
    public char getType(){
	return this.type;
    }
    
    public int getLocation(){
	return this.location;
    }
    
    private boolean checkType(char t){
	if(t == 'C' || t == 'V' || t == 'L')
	    return true;
	else
	    throw new IllegalArgumentException("Unknown type");
    }
}
