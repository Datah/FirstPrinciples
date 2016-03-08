import java.util.Scanner;

public class ThreeN1{
    
    //main method for 3N+1 problem
    public static void main(String[] args){

        int n,count; //declare n
        count=1;
        Scanner stdin = new Scanner(System.in); //declare Scanner instance to be used
        System.out.print("Input a positive integer: "); //ask for input
         n = stdin.nextInt();
            //read input integer
        while(n<=0){
            System.out.print("The input was not positive. Try again: ");
            n = stdin.nextInt();
        }
        System.out.printf("%d ",n); //output new line
        while (n>1){        //main while loop
            if (n%2 == 0){  //even case
                n/=2;
            }
            else{           //odd case
                n = 3*n+1;
            }
            System.out.print(n + " ");  //print sequence element
            count++;                    //increment counter
        }
        System.out.println("\nThe sequence contains " + count + " terms.");     //final program output
    }
}