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
import java.io.Serializable;
import java.util.regex.Pattern;
import java.io.*;

public class Matrix implements RingElement, Serializable{
    private static final long serialVersionUID = 1;
    private static final Pattern INTP = Pattern.compile("\\d+");
    private static final Pattern DOUBLEP = Pattern.compile("\\d*(\\.\\d+)?");
    protected double[][] entries;     //main matrix array
    protected int rows, columns;      //matrix dimensions
    
    
    //initializes the matrix to a given size. checks that the size is valid and that the matrix has not been init'd already.
    
    Matrix(){
        rows=0;
        columns=0;
    }
    
    Matrix(double[][] ents){
        rows = ents.length;
        columns = ents[0].length;
        entries = new double[rows][columns];
        entries = ents;
    }
    
    Matrix(int r, int c){
        rows=r;
        columns=c;
        entries = new double[r][c];
    }
    
    
    public void initMatrix(int r, int c){
        if (r>0 && c>0){
            rows = r;
            columns = c;
            entries = new double[rows][columns];
        }
        else{
            System.out.println("Error: Invalid matrix dimensions.");
        }
    }
    
    //reads the matrix from user console input, providing instructions.
    public void setMatrix(){
        Scanner stditxt = new Scanner(System.in);
        String currEntry;
        double currEntryd;
        
        if(!this.initialized()){
            System.out.println("Error: Matrix not yet initialized. Cannot read entries.");
        }
        else{
            for (int i=0;i<rows;i++){
                System.out.println("Please input row " + (i+1) + " as " + columns + " double-precision values separated by spaces:");
                for (int j = 0; j<columns; j++){
                    while(true){
                        try{
                        currEntry = stditxt.next(DOUBLEP);
                        currEntryd = Double.parseDouble(currEntry);
                        entries[i][j] = currEntryd; 
                        break;}
                        catch(NumberFormatException e){
                            System.out.println("Invalid entry at index " + j + ". Input the last " + (columns - j) + " entries again.");
                        }
                    }
                }
            }
        }
        
    }
    
    public void readMatrix(double[][] rMatrix){
            this.initMatrix(rMatrix.length, rMatrix[0].length);
            System.arraycopy(rMatrix,0,entries,0,rMatrix.length);
              
    }
    
    public int rows(){
        return rows;
    }
    
    public int columns(){
        return columns;
    }
    
    public int[] getDimensions(){
        int[] dimensions = {rows, columns};
        return dimensions;
    }
    
    public double[][] getEntries(){
        return entries;
    }
    
    public double getEntry(int i, int j){
        if (this.initialized() && (i >= 0 && i < rows) && (j >=0 && j < columns)){
            return entries[i][j];
        }
        else{
            return Double.NaN;
        }
    }
    
    public boolean initialized(){
        return entries!=null;
    }
    
    public void printMatrix(){
        System.out.print(this.toString());
        
        /*if(this.initialized()){
            System.out.println();
            for (int i=0;i<rows;i++){
                System.out.print("[ ");
                for (int j=0;j<columns;j++){
                    System.out.printf("%4.4g ",entries[i][j]);
                }
                System.out.print(" ]");
                System.out.println();
            }
        }
        else{
            System.out.print("Matrix not yet initialized. Cannot print.");
        }*/
    }
    
    public void copy(Matrix toCopy){
        this.readMatrix(toCopy.getEntries());
    }
    
    public Matrix returnCopy(){
        Matrix copy = new Matrix();
        copy.readMatrix(entries);
        return copy;
    }
    
    @Override
    public String toString(){
        String mString = "";
        double roundedEntry;
        if(this.initialized()){
            mString += "\n";
            for (int i=0;i<rows;i++){
                mString += "[ ";
                for (int j=0; j<columns;j++){
                    roundedEntry = (Math.round(entries[i][j]*1000.0))/1000.0;
                    mString += String.format("%9.3f",roundedEntry);
                }
                mString+="      ]\n";
            }
        }
        else{
            mString = "Matrix not yet initialized.";
        }
        return mString;
    }
    
    public boolean setEntry(int r, int c, double newEntry){
        boolean success = false;
        if (this.initialized() && (r >= 0 && r < rows) && (c >=0 && c < columns)){
            entries[r][c] = newEntry;
            success = true;
        }
        return success;
    }
    
    public void swapRows(int r1, int r2){
        double[] iRow;
        if (this.initialized() && (r1 >= 0 && r1 < rows) && (r2 >=0 && r2 < rows)){
            iRow = entries[r1];
            entries[r1] = entries[r2];
            entries[r2] = iRow;
        }
        else{throw new RuntimeException();}
    }
    
    public void addScaledRow(int r1, double constantMult, int r2){
        if(this.initialized() && (r1 >= 0 && r1<rows) && (r2>=0 && r2<rows)){
            for (int j=0;j<columns;j++){
                entries[r1][j] += constantMult*entries[r2][j];
            }
        }
        else{
            throw new RuntimeException();
        }
    }
    
    
    public void multiplyRow(int r, double constantMult){
        if(this.initialized() && (r >= 0 && r<rows) && constantMult != 0){
            for(int j=0;j<columns;j++){
                entries[r][j]*=constantMult;
            }
        }
        else{throw new RuntimeException();}
    }
    
    public void swapCols(int c1, int c2){
        double swapCurr;
        if (this.initialized() && (c1 >= 0 && c1 < columns) && (c2 >=0 && c2 < columns)){
            for (int i=0;i<rows;i++){
                swapCurr = entries[i][c1];
                entries[i][c1] = entries[i][c2];
                entries[i][c2] = swapCurr;
            }
        }
        else{
            throw new RuntimeException();
        }
    }
    
    public void addScaledCol(int c1, double constantMult, int c2){
        if(this.initialized() && (c1 >= 0 && c1<columns) && (c2>=0 && c2<columns)){
            for (int i=0;i<rows;i++){
                entries[i][c1] += constantMult*entries[i][c2];
            }
        }
        else{
            throw new RuntimeException();
        }
    }
    
    public void multiplyCol(int c, double constantMult){
        if(this.initialized() && (c >= 0 && c<columns) && constantMult != 0){
            for(int i=0;i<rows;i++){
                entries[i][c]*=constantMult;
            }
        }
        else{throw new RuntimeException();}
    }
    
    public Matrix transposeNew(){
        if(this.initialized()){
            double[][] transposeEntries = new double[columns][rows];
            for (int i=0;i<columns;i++){
                for (int j=0;j<rows;j++){
                    transposeEntries[i][j] = entries[j][i];
                }
            }
            Matrix transpose = new Matrix(transposeEntries);
            return transpose;
        }
        else{throw new RuntimeException();}
    }
    
    public void transpose(){
        readMatrix(this.transposeNew().getEntries());
    }
    
    public static Matrix random(int r, int c){
        Matrix randomMatrix = new Matrix();
        randomMatrix.initMatrix(r, c);
        for (int i = 0;i<randomMatrix.rows;i++){
            for (int j=0;j<randomMatrix.columns;j++){
                randomMatrix.setEntry(i,j,Math.random());
            }
        }
        return randomMatrix;
    }
    
    public static Matrix fromFile(File matrixFile) throws Exception{
        double[][] newEntries;
        int rowsN, colsN;
        String currLine;
        String[] splitLine;
        FileReader fReader = new FileReader(matrixFile);
        BufferedReader buffer = new BufferedReader(fReader);
        currLine = buffer.readLine();
        while(currLine.contains("  ")){
            currLine = currLine.replace("  "," ");
        }
        splitLine = currLine.split(" ");
        rowsN = Integer.parseInt(splitLine[0]);
        colsN = Integer.parseInt(splitLine[1]);
        newEntries = new double[rowsN][colsN];
        for(int i=0;i<rowsN;i++){
            currLine = buffer.readLine();
            while(currLine.contains("  ")){
            currLine = currLine.replace("  "," ");
            }
            splitLine = currLine.split(" ");
            for(int j=0; j<colsN; j++){
                newEntries[i][j] = Double.parseDouble(splitLine[j]);
            }
        }
        buffer.close();
        if(rowsN == colsN){
            return new SquareMatrix(newEntries);
        }
        else{
            return new Matrix(newEntries);
        }
        
    }
    
    public Matrix addNew(Matrix M2){
        Matrix added = new Matrix(entries);
        added.add(M2);
        return added;
    }
    
    public Matrix subMatrix(int[]rowsL, int[] columnsL){
        Matrix subM = new Matrix(rowsL.length, columnsL.length);
        boolean validArrays = true;
        for (int row:rowsL){
            for (int col:columnsL){
                validArrays = ((row>=0 && row<rows) && (col>=0 && col<columns));
            }
        }
        if(validArrays && this.initialized()){
            for(int i=0;i<rowsL.length;i++){
                for (int j=0;j<columnsL.length; j++){
                    subM.setEntry(i,j,entries[rowsL[i]][columnsL[j]]);
                }
            }
            return subM;
        }
        else{throw new RuntimeException();}
    }
    
    public Matrix coFactor(int r, int c){
        if (this.initialized()){
            Matrix cof = new Matrix(rows-1, columns-1);
            int extra;
            for (int i=0;i<rows-1;i++){
                extra = (i<r)?0:1;
                for(int j=0;j<columns-1;j++){
                    if(j<c){
                       cof.setEntry(i,j,entries[i+extra][j]);
                    }
                    else{
                        cof.setEntry(i, j, entries[i+extra][j+1]);
                    }
                }
            }
            return cof;
        }
        else{throw new RuntimeException();} 
    }
    
    public void rightMultiply(Matrix M2){
        double currEntry;
        Matrix multResult = new Matrix(this.rows(), M2.columns());
        int x;
        if (this.initialized() && M2.rows() == this.columns()){
            for (int i=0; i<this.rows();i++){
                for (int j=0;j<M2.columns();j++){
                    currEntry = 0.0;
                    for (x=0;x<columns;x++){
                        currEntry+= entries[i][x]*M2.getEntry(x, j);
                    }
                    multResult.setEntry(i, j, currEntry);
                }
            }
        this.readMatrix(multResult.getEntries());
        }
        else{
            throw new RuntimeException();
        }
    }
    
    public boolean isSquare(){
        return (rows == columns && rows > 0);
        }
    
/** Row reduction methods*/
    
    /**Given list of previously found pivots and list of rows left, finds the next pivot
    *takes an int[2] array representing the last pivot position, and an ArrayList of Integers 
    *representing legal pivot rows left*/
    private int[] findPivot(int[] lastPivot, ArrayList<Integer> rowsLeft){
        int[] nextPivot = {-1,-1};
        pivotColLoop: for (int j=lastPivot[1]+1;j<columns;j++){
            for (Integer i:rowsLeft){
                if(entries[i][j]!=0){
                    nextPivot[0]=i;
                    nextPivot[1]=j;
                    break pivotColLoop;
                }
            }
        }
        return nextPivot;
    }
    
    /** find Reduced Row Echelon Form of the matrix
     *returns void, takes no arguments. Puts the current matrix in RREF form.
     @return String[2] in the form of {rank, row ops to reach RREF}*/
    public final String[] RREF(){
        if(this.initialized()){
            String rowOps = "";
            String[] rankOps = new String[2];
            int[] lastPivot = {-1, -1};                             //track last pivot position
            TreeMap<Integer,Integer> pivotList = new TreeMap<>();   //list of all pivots, for row reordering at the end (column = key, row = value)
            ArrayList<Integer> rowsList = new ArrayList<>();        //track nonpivot rows, to let findPivot() know which rows are legal for new pivots
            for (int i=0;i<rows;i++){                               //populate rowsList with all rows initially
            rowsList.add(i);
            }
            while(true){                                                     //row reduction loop. Does NOT yet reorder rows to put the matrix in echelon form.
                lastPivot = findPivot(lastPivot, rowsList);         //find next pivot
                if(lastPivot[0]==-1)                                //test if a legal new pivot was found, otherwise break the loop
                    {break;}
                else{                                               //if a legal pivot was found
                    pivotList.put(lastPivot[1],lastPivot[0]);       //add new pivot to pivot list - column as key, row as value
                    rowsList.remove(rowsList.indexOf(lastPivot[0]));               //remove the pivot row from the legal pivot rows list
                    rowOps+=String.format("R%d->(%.3f)R%d$",lastPivot[0],1/getEntry(lastPivot[0],lastPivot[1]),lastPivot[0]);
                    multiplyRow(lastPivot[0],1/getEntry(lastPivot[0],lastPivot[1]));    //scale pivot row to put a 1 in pivot position
                    for (int i=0;i<rows;i++){                       //add scaled versions of pivot row to every OTHER row
                        if (i!=lastPivot[0]){
                            rowOps+=String.format("R%d->R%d+(%.3f)R%d$", i,i,-getEntry(i,lastPivot[1]),lastPivot[0]);
                            addScaledRow(i,-getEntry(i,lastPivot[1]),lastPivot[0]);     //makes every OTHER row equal 0 in the pivot column
                        }
                    }
                }  
            }
            int rank = pivotList.size();                            //preserve number of pivots for row reordering algorithm
            for(int i=0;i<rank;i++){                                //loop to reorder rows into echelon form
                Integer pivotRow = pivotList.get(pivotList.firstKey());
                swapRows(pivotRow,i);    //swap rows appropriately
                rowOps+=String.format("R%d<->R%d$",pivotRow,i);
                pivotList.remove(pivotList.firstKey());
                for(Integer K:pivotList.keySet()){                  //reset values of swapped pivot rows appropriately
                    pivotList.replace(K, i, pivotRow);
                }
            }
            rankOps[0]=String.valueOf(rank);
            rankOps[1]=rowOps;
            return rankOps;
        }
        else{throw new RuntimeException();}
    }
    
    public final int rank(){
        if(this.initialized()){
            return Integer.parseInt(RREF()[0]);
        }
        else{throw new RuntimeException();}
    }

/** Ring element interface methods
 @return false for matrices*/
    @Override
    public boolean finiteOrder(){
        return false;
    }
    
    @Override
    public int order(){
        return 0;
    }
    
    @Override
    public void add(RingElement M2){
        if (M2 instanceof Matrix){
            Matrix M2Matrix = (Matrix)M2;
            if (M2Matrix.rows() == this.rows() && M2Matrix.columns() == this.columns){
                for (int i=0;i<rows; i++){
                    for (int j=0; j<columns; j++){
                        entries[i][j]+=M2Matrix.getEntry(i,j);
                    }
                }
            
             }
            else{throw new RuntimeException();}
        }
        else{
            throw new RuntimeException();
        }
    }
    
    @Override
    public void mult(int constantMult){
        if(this.initialized()){
            for (int i=0;i<rows;i++){
                for (int j=0;j<columns;j++){
                    entries[i][j]*=constantMult;
                }
            }
        }
        else{
            throw new RuntimeException();
        }
    }
    
    public void mult(double constantMult){
        if(this.initialized()){
            for (int i=0;i<rows;i++){
                for (int j=0;j<columns;j++){
                    entries[i][j]*=constantMult;
                }
            }
        }
        else{
            throw new RuntimeException();
        }
    }
    
    
    
}
