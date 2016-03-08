public class TestingStuff{
    public static int runningCount=1;
    
    public static void main(String[] args){
        int dollas;
        int[] denoms = {1, 5, 10, 20};
        while(true){
        System.out.print("Enter the amount to be divided: $");
        dollas = TextIO.getlnInt();
        if (dollas == 0){break;}
        System.out.println("There are " + numberOfBills(denoms.length - 1,dollas,denoms,dollas) + " ways to split up this dollar amount.");}
    }
    
    public static long numberOfBills(int n, int amount, int[] bills, int amountF){
        long count = 0;
        int currLoop;
        int currAmount;
        currLoop = amount/bills[n];
        
        //if (runningCount==1 && n==3){
            //System.out.print(runningCount+") ");
        //}
        if (n==0 || amount == 0){
            //System.out.println(amount + " $1, ");
            //if (amount != amountF){
                //System.out.print(++runningCount + ") ");}
            return 1;
        }
        else{
        for (int j=currLoop;j>=0;j--){
            //if(j!=0){
                //System.out.print(j + " $" + bills[n] + ", ");}
        
                currAmount = amount - bills[n]*j;
                count+=numberOfBills(n-1,currAmount, bills,amountF);
                }}
        return count;
    }
}