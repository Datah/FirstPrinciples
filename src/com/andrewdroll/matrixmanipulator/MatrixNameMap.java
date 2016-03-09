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
import java.util.*;
import java.io.*;
        
public class MatrixNameMap {
    Map<String,Matrix> matrices = new HashMap<>();
    
    public Map<String, Matrix> getMap(){
        return matrices;
    }
}
