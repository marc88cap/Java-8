/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilersimulation.errorcheck;
/**
 *
 * @author markoc
 */
public class InstructionNode<E> {
    E data;
    InstructionNode<E> prevInstruction;
    InstructionNode<E> nextInstruction;
    
    public InstructionNode(E d){
        this(d, null, null);
    }
    
    /**
     * Construct an instruction node
     * @param d = data
     * @param pI = previous instruction node
     * @param nI = next instruction node
     */
    public InstructionNode(E d, InstructionNode pI, InstructionNode nI){
        data = d;
        prevInstruction = pI;
        nextInstruction = nI;
    }
    
    
}
