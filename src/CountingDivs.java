public class CountingDivs{
    public static void main(String[] args){
        int n; //integer to test for divisors
        int count; //counters
        count=0;
        while(true){
        System.out.println("Input an integer. The program will tell you how many divisors it has. ");
        n = TextIO.getlnInt();
        if (n>0){
            break;}
        System.out.println("Not a positive number. Try again.");
        }
        for (int i=1;i<=n;i++){
            if (n%i == 0){
                count++;
            }
            if (i%1000000 == 0){
                System.out.println(".");
            }
        }
        System.out.printf("The integer entered, %d, has %d divisors.",n,count);
    }
}