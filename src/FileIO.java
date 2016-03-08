public class FileIO{
    public static void main(String[] args){
        TextIO.readFile("student.txt");
        int grade1,grade2,grade3;
        String sName;
        double average;
        sName = TextIO.getln();
        grade1 = TextIO.getlnInt();
        grade2 = TextIO.getlnInt();
        grade3 = TextIO.getlnInt();
        average = (grade1+grade2+grade3)/(3.0);
        System.out.printf("The student's name is %s. The student's average is %.1f",sName,average);
        
    }
}