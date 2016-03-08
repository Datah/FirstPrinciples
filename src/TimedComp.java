public class TimedComp{
    public static void main(String[] args){
        long startT;
        long endT;
        double tSpent;
        
        startT = System.currentTimeMillis();
        double width, height, hypotenuse;
        double topA, botA;
        width = 5;
        height = 17;
        topA = Math.atan(height/width);
        botA = Math.PI/2 - topA;
        hypotenuse = Math.sqrt(width*width + height*height);
        System.out.print("The top angle is ");
        System.out.println(topA);
        System.out.print("The bottom angle is ");
        System.out.println(botA);
        System.out.print("The hypotenuse is ");
        System.out.println(hypotenuse);
        System.out.print("The time elapsed was ");
        endT = System.currentTimeMillis();
        tSpent = (endT - startT)/1000.0d;
        System.out.println(tSpent);
       }
}