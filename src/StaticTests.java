/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andrew
 */
public class StaticTests extends StaticSuper{
    
    static int rand = (int)(Math.random()*6);;
    
    
    StaticTests(){
        System.out.println("constructor");
    }
    
    public static void main(String[] args){
        System.out.println("in main");
        StaticTests st = new StaticTests();
    }
    
}
