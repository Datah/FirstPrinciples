package com.andrewdroll.matrixmanipulator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andrew
 */
public interface RingElement {
    void add(RingElement rE);
    
    void mult(int power);
    
    int order();
    
    boolean finiteOrder();
    
}

