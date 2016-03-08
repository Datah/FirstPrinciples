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

import java.io.*;

public class MatrixTest {
    
    public static void main(String[] args){
        /*Matrix M = new Matrix();
        int r, c;
        System.out.print("Enter the number of rows: ");
        r = TextIO.getlnInt();
        System.out.print("Enter the number of columns: ");
        c = TextIO.getlnInt();
        M.initMatrix(r,c);
        M.setMatrix();
        M.printMatrix();
        System.out.println(M.rows() + " " + M.columns() + " " + M.getDimensions()[0] + " " + M.getDimensions()[1] + " " + M.getEntry(r-1,c-1));
        double[][] mEntries = M.getEntries();
        Matrix N;
        N = new Matrix();
        N.readMatrix(mEntries);
        N.printMatrix();
        Matrix Z;
        Z = new Matrix(mEntries);
        Z.printMatrix();
        Matrix X = Matrix.random(4,8);
        X.printMatrix();
        Matrix Y = Matrix.random(4,8);
        Y.printMatrix();
        Y.add(X);
        Y.printMatrix();
        Y.swapRows(0,Y.rows()-1);
        Y.addScaledRow(1,0.5,0);
        Y.multiplyRow(2, 2);
        Y.printMatrix();
        Y.swapCols(0,2);
        Y.addScaledCol(1, 0.5, 2);
        Y.multiplyCol(0, 0.5);
        Y.printMatrix();
        Matrix J = Y.coFactor(2, 5);
        J.printMatrix();
        int[] rowsx = {0,2};
        int[] colsx = {3,4};
        Matrix S = Y.subMatrix(rowsx, colsx);
        S.printMatrix();
        S.mult(5);
        S.printMatrix();
        S.rightMultiply(M);
        S.printMatrix();*/
        double[][] x = {{0,0,0,0,1},{6.5,6.5,1.7,1.7,1.7},{5.2,5.2,0,0,1},{2,2,3,3,5},{3,3,4,4,6},{1,1,0,0,0}};
        try{
        SquareMatrix M = SquareMatrix.random(50);
        M.printMatrix();
        double det = M.determinant();
        System.out.println(det);
        M.invert();
        M.printMatrix();
        double detI = M.determinant();
        System.out.println(detI + " " + (detI*det));
        M.RREF();
        M.printMatrix();
        System.out.println(M.determinant());
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("File import failed.");
        }
        
        
        
    }
    
}
