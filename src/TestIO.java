public class TestIO{
    public static void main(String[] args){
        String uI1, uI2, firstName, lastName;
        int uInt;
        int place;
        
        System.out.print("Please input your name (first and last): ");
        uI1 = TextIO.getln();
        place = uI1.indexOf(' ');
        firstName=uI1.substring(0,place);
        lastName=uI1.substring(place+1);
        System.out.print("\nPlease input your job: ");
        uI2 = TextIO.getln();
        System.out.print("\nPlease input your age: ");
        uInt = TextIO.getInt();
        System.out.printf("\n******************\nYour first name is %s,"
                + " your last name is %s, your job is %s, and your age is %d.\n"
                ,firstName.toUpperCase(), lastName.toUpperCase(), uI2, uInt);
        System.out.printf("Your initials are %s%s. Your first name has %d characters,"
                + " and your last name has %d characters",firstName.charAt(0),lastName.charAt(0),
                firstName.length(), lastName.length());
        
    }
}