/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andrew
 */

import java.util.Arrays;

public class ArrayTests {
    public static void main(String[] args){
        double[] raceTimes;
        double range;
        double max, min;
        
        raceTimes = new double[100];
        for (int i=0; i<100;i++){
            raceTimes[i]=Math.random()/10;
        }
        max = min = raceTimes[0];
        
        for (int i = 0; i<100; i++){
            if (raceTimes[i]>max){
                max = raceTimes[i];
            }
            if (raceTimes[i]<min){
                min = raceTimes[i];
            }
        }
        
        range = max - min;
        System.out.println("The range from going through the array is " + range);
        
        Arrays.sort(raceTimes);
        range = raceTimes[99]-raceTimes[0];
        System.out.println("The range is " + range);
        
        
        
    }
}
