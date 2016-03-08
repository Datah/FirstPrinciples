/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andrew
 */
public class StaticSuper {
    
    static{System.out.println("super static block");
    }
    
    StaticSuper(){
        System.out.println("super constructor");
    }
    
}
