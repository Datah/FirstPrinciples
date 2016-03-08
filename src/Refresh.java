/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andrew
 */
public class Refresh{
    enum Button {BACK, FORWARD, HOME, BOOKMARK};
    
    public static void main(String[] args){
        Button x;
        Button y;
        double z = 0d;
        
        do{
        z= Math.random();
        if (z >= 0.5){
            x = Button.BACK;
            y = Button.FORWARD;
        }
        else if (z >=0.4){
            x = Button.HOME;
            y = Button.BOOKMARK;
        }
        else {
            x = Button.HOME;
            y = Button.FORWARD;
        }
        System.out.println("x is " + x + ", y is " + y + ", z is " + z);
        } while (z <=0.9);
    }
}
