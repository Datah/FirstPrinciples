/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andrew
 */
public class WrapperTests {
    
    Integer i = 0;
    int j;
    
    public static void main(String[] args){
        WrapperTests t = new WrapperTests();
        t.go();
    }
    
    public void go(){
        j=i;
        System.out.println(j);
        System.out.println(i);
    }
    
}
