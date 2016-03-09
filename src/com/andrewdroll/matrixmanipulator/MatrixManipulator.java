package com.andrewdroll.matrixmanipulator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Andrew
 */
import java.util.*;
import java.io.*;
import java.util.regex.*;

public class MatrixManipulator {
    
    File helpFile = new File(".\\Matrixapi.txt");
    //MatrixNameMap matrices = new MatrixNameMap();
    private static final Pattern MATRIXP = Pattern.compile("\\[[ ]*(<[ ]*(\\d*(\\.\\d+)?[ ]*)+>[ ]*)\\]");
    private static final Pattern VECTORP = Pattern.compile("<[ ]*(\\d*(\\.\\d+)?[ ]*)+>");
    private static final Pattern ENTRYP = Pattern.compile("\\d*(\\.\\d+)?");
    
    MatrixCommandParser parser = new MatrixCommandParser();
    
    /**Main command line function MatrixCmdLine()
     * takes no arguments, returns void, prints commands lines and instructions while the program keeps running.
     * reads user input and feeds it to parseCmdLine()
     */
    public void cmdLine(){                                                //main command line method
        Scanner scanner = new Scanner(System.in);                               //load io objects, create current cmdline string
        String currLine;
        /*try{
            FileReader fileInput = new FileReader("Matrixapi.txt");
            BufferedReader buffer = new BufferedReader(fileInput);
        }catch(Exception e)
        {}*/
        System.out.println("Matrix manipulator Beta v0.2");                     //intro lines
        System.out.println("By Andrew Droll - STARTED Nov 2014, polished March 2016.");
        System.out.println("Input help for an API listing, or quit to exit.");  //help & quit instructions
        while(true){                                                            //cmd line loop
            System.out.print("$ ");
            currLine = scanner.nextLine();
            if (currLine.toLowerCase().equals("quit")){
                break;
            }
            parseCmdLine(currLine);
        }
    }
    
    /** Parsing command line commands
     * Takes a line from the matrix command line, parses it into an assignment or an instruction.
     * Begins processing of the command line.
     * @param currLine current command line input
     */
    private void parseCmdLine(String currLine){
        /*String[] components;
        String name;
        String instruction;
        byte type;
        boolean errorType;*/
        if(currLine.trim().toLowerCase().equals("help")){
            try{
                FileReader helpReader = new FileReader(helpFile);
                BufferedReader buffer = new BufferedReader(helpReader);
                String nextLine;
                while((nextLine = buffer.readLine())!=null){
                    System.out.println(nextLine);
                }
                buffer.close();
                return;
            }catch(Exception e){
                System.out.println("Error reading help file.");
                return;
            }finally{}
        }else{
            Matrix response = parser.interpretAndAssign(currLine);
            System.out.println(response);
        }
        /*if((errorType = (currLine.indexOf("=")>0)) == (currLine.indexOf(":")>0)){
            System.out.println(errorType?"Instruction not processed: Too many components. Try again.":
                    "Invalid instruction. Try again.");
            return;
        }
        else if(currLine.indexOf('=')>0){
            type = 0;
            components = currLine.split("=");
        }
        else{
            type = 1;
            components = currLine.split(":");
        }     
        
        if (components.length > 2){
            System.out.println("Instruction not processed: Too many components. Try again.");
            return;
        }
        components[0] = components[0].trim();
        components[1] = components[1].trim();
        if(type == 0){
            Assignment(components[0], components[1]);
        }
        if(type == 1){
            Instruction(components[0], components[1]);
        }*/
        
        
    }
   
    
    /*private void Assignment(String name, String value){
        Matrix assignment;
        String[] arguments;
        if(value.contains("File")){
            arguments = value.split(" ");
            arguments[1] = arguments[1].trim();
            File fileSource = new File(arguments[1]);
            try{
                assignment = Matrix.fromFile(fileSource);
            }catch(Exception e){System.out.println("Error reading from file. Try again.");e.printStackTrace();return;}
                matrices.getMap().put(name,assignment);
        }
        else if (value.charAt(0) == '['){
            Matcher MatMatch = MATRIXP.matcher(value);
            if (MatMatch.matches()){
                
            }
            else{
                System.out.println("Not a valid matrix definition. Try again.");
            }
        }
        else{
            if(matrices.getMap().containsKey(name)){

            }
        }
    }
    
    private void Instruction(String name, String inst){
        if(name.equals("Print")){
            if(matrices.getMap().containsKey(inst)){
                Matrix M = (Matrix) matrices.getMap().get(inst);
                System.out.println(M);
            }
        }
        
    }*/
    
    public static void main(String[] args){
        MatrixManipulator matrixApp = new MatrixManipulator();
        matrixApp.cmdLine();
    }
    
}
