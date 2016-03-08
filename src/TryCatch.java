/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andrew
 */
public class TryCatch {
    
    public static void main(String[] args){
        String uI1, uI2;
        int i1, i2;
        System.out.println("Enter two integers.");
        uI1 = TextIO.getlnWord();
        uI2 = TextIO.getlnWord();
        try{
            i1 = Integer.parseInt(uI1);
            i2 = Integer.parseInt(uI2);
            System.out.println("The sum is " + (i1 + i2) + ".");
        } catch(IllegalArgumentException e){
            System.out.println("One of your inputs was not an integer.");
        }
    }
    
}
