public class LoopsLearn{
    public static void main(String[] args){
        double principal, rate;
        int years,curr;
        curr=0;
        System.out.print("Input the principal of the investment: ");
        principal = TextIO.getlnDouble();
        System.out.print("Input the interest rate, as a decimal: ");
        rate = TextIO.getlnDouble();
        System.out.print("How many years would you like to know the account balance for? ");
        years = TextIO.getlnInt();
        while (curr < years){
            double interest = principal*rate;
            principal = principal+interest;
            curr++;
            System.out.printf("In year %d, the interest is %.2f and the principal is %.2f.\n",curr,interest,principal);
        }
                   
        }
        
    }
