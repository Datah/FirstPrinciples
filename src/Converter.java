public class Converter{
    
    //Main method. Runs the show.
    public static void main(String[] args){
        int selection;                    //variable for menu selection
        while (true){                       //Program loop
            selection = menu();             //call menu function, set value equal to selection
            if (selection == 0){            //exit if selection = 0;
                break;
            }
            switch (selection){             //switch based on menu selection
                case 2: temperature();break;
                case 1: length();break;
                case 3: weight();break;
            }
            System.out.println();
        }
        
    }
    
    //menu method, prints out the main menu
    static int menu(){
        int select = 0;                 // selection var
        //print menu
        System.out.println("What type of units would you like to convert?");
        System.out.println("1. Length");
        System.out.println("2. Temperature");
        System.out.println("3. Weight");
        System.out.println("0. Exit program");
        while(true){                //selection loop
        try{                        //catch exceptions
        while(true){
            select = TextIO.getlnInt();
            if (select >3 || select < 0){               //check if selection is valid
                System.out.println("Invalid input. Type an integer between 1 and 3.");
            }
            else break;}
            break;
        }catch (IllegalArgumentException e){            //catch any exception
            System.out.println("Invalid input. Type an integer between 1 and 3.");
        }
        }
        return select;                          //return a valid menu selection
    }
    
    //method to convert temperatures
    static void temperature(){
        double userT, convT;                    //input T and calced T
        char userU, convU;                      //input unit and conversion unit
        System.out.println("Enter the temperature and a unit, separated by a space. Acceptable units are F, C, K.");
        while (true){                           //get input T + unit, check for exceptiond and a valid unit
            try{
                userT = TextIO.getDouble();
                while (true){
                    userU = TextIO.getlnChar();
                        if (userU == 'F' || userU=='C' || userU == 'K'){break;}
                    else{
                        System.out.println("Invalid unit. Try again. (F,C,K)");
                        }
                    }
                break;}
            catch(IllegalArgumentException e){          //catch invalid input T
                System.out.println("Invalid temperature. Try again.");
            }
        }
        System.out.println("What unit would you like to convert to? (F,C,K)");          //get conversion unit
        while (true){                       //check for valid conversion unit
            
                convU = TextIO.getlnChar();
                if (convU == 'F' || convU == 'K' || convU == 'C')
                    break;
                else
                    System.out.println("Invalid unit. Try again. (F,C,K)");
        }
        
        //conversion control chain
        if (userU == 'F'){
            switch (convU){         //fahrenheit to other units
                case 'K': convT = (userT - 32)/1.8 + 273.15;break;
                default: convT = (userT-32)/1.8;break;
            }
        }
        else if (userU == 'C'){             //celsius to other units
            switch (convU){
                case 'F': convT = (userT*1.8 + 32);break;
                default: convT = userT + 273.15;break;
            }
        }
        else{                           //kelvin to other units
            switch (convU){
                case 'C':convT = userT - 273.15;break;
                default:convT = (userT - 273.15)*1.8+32;break;
            }
        }
        if(userU == convU){             //if same unit, say so
            System.out.printf("No conversion necessary. Same unit. Temperature is %f %c.\n",userT, userU);
        }
        else{                           //else print conversion result
            System.out.printf("Converted temperature is %.1f %c.\n", convT, convU);
        }
        
    }
    
    
    //length conversion method
    static void length(){
        double userL, convL;                    //input T and calced T
        String userU, convU;                      //input unit and conversion unit
        System.out.println("Enter the length and a unit. Do not put a space between them (e.g. '25km'). Acceptable units are km, mi, ly.");
        while (true){                           //get input T + unit, check for exceptiond and a valid unit
            try{
                userL = TextIO.getDouble();
                while (true){
                    userU = TextIO.getln();
                        if (userU.equals("mi") || userU.equals("km") || userU.equals("ly")){break;}
                    else{
                        System.out.println("Invalid unit. Try again. (km,mi,ly)");
                        }
                    }
                break;}
            catch(IllegalArgumentException e){          //catch invalid input T
                System.out.println("Invalid length. Try again.");
            }
        }
        convL = userL;
        System.out.println("What unit would you like to convert to? (km,mi,ly)");          //get conversion unit
        while (true){                       //check for valid conversion unit
            
                convU = TextIO.getln();
                if (userU.equals("mi") || userU.equals("km") || userU.equals("ly"))
                    break;
                else
                    System.out.println("Invalid unit. Try again. (F,C,K)");
        }
        
        //conversion control chain
        if (userU.equals("mi")){
            switch (convU){         //fahrenheit to other units
                case "ly": convL *= 1.70111428e-13;break;
                default: convL *=1.60934;break;
            }
        }
        else if (userU.equals("ly")){             //celsius to other units
            switch (convU){
                case "mi": convL *= 5.87849981e12;break;
                default: convL *= 9.4605284e12;break;
            }
        }
        else{                           //kelvin to other units
            switch (convU){
                case "ly": convL *= 1.05702341e-13;break;
                default: convL *= 0.621371;break;
            }
        }
        if(userU.equals(convU)){             //if same unit, say so
            System.out.printf("No conversion necessary. Same unit. Temperature is %.7g %s.\n",userL, userU);
        }
        else{                           //else print conversion result
            System.out.printf("Converted length is %.7g %s.\n", convL, convU);
        }
    }
    
    static void weight(){
        double userW, convW;                    //input T and calced T
        String userU, convU;                      //input unit and conversion unit
        System.out.println("Enter the weight and a unit. Do not separate them by a space (e.g. 25lb). Acceptable units are kg, lb, oz.");
        while (true){                           //get input T + unit, check for exceptiond and a valid unit
            try{
                userW = TextIO.getDouble();
                while (true){
                    userU = TextIO.getln();
                        if (userU.equals("lb") || userU.equals("kg") || userU.equals("oz")){break;}
                    else{
                        System.out.println("Invalid unit. Try again. (lb,kg,oz)");
                        }
                    }
                break;}
            catch(IllegalArgumentException e){          //catch invalid input T
                System.out.println("Invalid length. Try again.");
            }
        }
        convW = userW;
        System.out.println("What unit would you like to convert to? (lb,kg,oz)");          //get conversion unit
        while (true){                       //check for valid conversion unit
            
                convU = TextIO.getln();
                if (userU.equals("lb") || userU.equals("kg") || userU.equals("oz"))
                    break;
                else
                    System.out.println("Invalid unit. Try again. (lb,kg,oz)");
        }
        
        //conversion control chain
        if (userU.equals("kg")){
            switch (convU){         //fahrenheit to other units
                case "lb": convW *= 2.20462;break;
                default: convW *= 35.274;break;
            }
        }
        else if (userU.equals("lb")){             //celsius to other units
            switch (convU){
                case "kg": convW /= 2.20462;break;
                default: convW *= 16;break;
            }
        }
        else{                           //kelvin to other units
            switch (convU){
                case "kg": convW /=35.274;break;
                default: convW /= 16;break;
            }
        }
        if(userU.equals(convU)){             //if same unit, say so
            System.out.printf("No conversion necessary. Same unit. Weight is %.7g %s.\n",userW, userU);
        }
        else{                           //else print conversion result
            System.out.printf("Converted weight is %.7g %s.\n", convW, convU);
        }
        
    }
}