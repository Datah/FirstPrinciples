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

public class SquareMatrix extends Matrix {
            
    
    SquareMatrix(){
        super();
    }
    
    SquareMatrix(int n){
        super(n,n);
    }
    
    SquareMatrix(double[][] mEntries){
        super();
        if(mEntries.length == mEntries[0].length){
            super.readMatrix(mEntries);}
        else
            throw new RuntimeException();
    }
    
    @Override
    public void initMatrix(int n, int m){
        initMatrix(n);
    }
    
    public void initMatrix(int n){
        super.initMatrix(n,n);
    }
    
    @Override
    public void readMatrix(double[][] mEntries){
        if(mEntries.length == mEntries[0].length){
            super.readMatrix(mEntries);
        }
        else throw new RuntimeException();
    }
    
    public static SquareMatrix random(int n){
        SquareMatrix randomSquare = new SquareMatrix(Matrix.random(n, n).getEntries());
        return randomSquare;
    }
    
    @Override
    public SquareMatrix coFactor(int r, int c){
        Matrix coFactorMatrix = super.coFactor(r, c);
        SquareMatrix squareCoFactor = new SquareMatrix(coFactorMatrix.getEntries());
        return squareCoFactor;
    }
    
    @Override
    public SquareMatrix returnCopy(){
        Matrix copyMatrix = super.returnCopy();
        SquareMatrix squareCopy = new SquareMatrix(copyMatrix.getEntries());
        return squareCopy;
    }
    
    public void copy(SquareMatrix M2){
        this.readMatrix(M2.getEntries());
    }
    
    @Override
    public SquareMatrix transposeNew(){
        Matrix transpose = super.transposeNew();
        return new SquareMatrix(transpose.getEntries());
    }
    
    @Override
    public void transpose(){
        super.transpose();
    }
    
    /**Find determinant by multiplying diagonals of LU decomp
    @return double*/
    public double determinant(){
        SquareMatrix[] LU = findLU();
        double det = 1;
        for (int i=0;i<rows;i++){
            det*=LU[0].getEntry(i,i)*LU[1].getEntry(i,i);
        }
        return det;
    }
        
        
        
    /*    if(this.initialized()){
            if (rows == 1){
                return getEntry(0,0);
            }
            else{
                SquareMatrix determinantMatrix = this.returnCopy();
                double cofEntry, determinant;
                determinant = 0;
                int sign = 1;
                for(int i=0;i<rows;i++){
                    cofEntry = determinantMatrix.getEntry(0,i);
                    determinant+=cofEntry*sign*(determinantMatrix.coFactor(0, i)).determinant();
                    sign*=-1;
                }
                return determinant;
            }
        }
        else{throw new RuntimeException();}
    }*/
    
    
    public SquareMatrix[] findLU(){
        SquareMatrix L = new SquareMatrix(rows);
        SquareMatrix U = new SquareMatrix(rows);
        SquareMatrix[] LU = new SquareMatrix[2];
        int i, j, k;
        double sum = 0;
        for(i=0;i<rows;i++){
            U.setEntry(i,i,1);
        }
        
        for (j=0;j<rows;j++){
            for (i=j;i<rows;i++){
                sum = 0;
                for (k=0;k<j;k++){
                    sum += L.getEntry(i,k)*U.getEntry(k,j);
                }
                L.setEntry(i,j,getEntry(i,j)-sum);
            }
            
            for (i=j;i<rows;i++){
                sum = 0;
                for (k=0;k<j;k++){
                    sum += L.getEntry(j,k)*U.getEntry(k,i);
                }
                if (L.getEntry(j,j)==0){
                    System.out.println("Determinant of L is close to 0. Can't complete LU decomp.");
                    throw new RuntimeException();
                }
                U.setEntry(j,i,(getEntry(j,i)-sum)/L.getEntry(j, j));
            }
        }
        LU[0]=L;
        LU[1]=U;
        return LU;
    }
    
    public SquareMatrix invertNew(){
        double det = this.determinant();
        if(this.initialized() && det !=0){
            double[][] inverseEntries = new double[rows][rows];
            SquareMatrix inverse;
            byte sign;
            for (int i=0;i<rows;i++){
                for (int j=0;j<rows;j++){
                    sign=(byte)Math.round(Math.pow(-1,i+j));
                    inverseEntries[i][j] = sign*(this.coFactor(i, j)).determinant();
                }
            }
            inverse = new SquareMatrix(inverseEntries);
            inverse.transpose();
            inverse.mult(1/det);
            return inverse;
        }
        else{throw new RuntimeException();}
    }
    
    public void invert(){
        readMatrix(this.invertNew().getEntries());
    }
    
    public boolean isSingular(){
        return (determinant()==0);
    }
    
    public boolean hasInverse(){
        return (determinant()!=0);
    }
    
    public void square(){
        this.rightMultiply(this);
    }
    
    public static void main(String[] args){
        /*SquareMatrix N = SquareMatrix.random(5);
        N.printMatrix();
        System.out.println(N.determinant());
        System.out.println(N.isSingular());
        SquareMatrix NI = N.invertNew();
        NI.printMatrix();
        N.rightMultiply(NI);
        N.printMatrix();*/
        double[][] x = {{1,2,2},{0,1,2},{3,2,2}};
        SquareMatrix P = SquareMatrix.random(5);
        P.printMatrix();
        System.out.println(P.determinant() + " " + P.isSingular());
        String[] rankRREF = P.RREF();
        System.out.println(rankRREF[0] + " " + rankRREF[1]);
        P.printMatrix();
        
        /*SquareMatrix S = new SquareMatrix(4);
        S.setMatrix();
        S.printMatrix();
        System.out.println(S.determinant() + " " + S.isSingular());
        //S.square();
        S.printMatrix();
        SquareMatrix SI = S.invertNew();
        SI.printMatrix();
        SI.rightMultiply(S);
        SI.printMatrix();*/
    }
    
}
