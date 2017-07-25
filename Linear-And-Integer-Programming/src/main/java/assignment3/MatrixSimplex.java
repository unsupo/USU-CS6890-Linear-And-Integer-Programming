package assignment3;

import java.util.HashSet;
import java.util.Set;

public class MatrixSimplex {
    public static void main(String[] args) {
        new MatrixSimplex(new String[][]{
                {"x1", "x2", "s1", "s2","s3","z"},
                {"x1", "x2","z"}
        },new int[][]{
                {-1,-1,1,0,0,0},
                {-3,1,0,1,0,0},
                {1,0,0,0,1,0},
                {0,1,0,0,0,1}
        },new int[]{-3,1,2,2},
                new int[]{-3,-1,0,0,0,0,0}).doSimplex();

//        printMatrix(
//                MatrixSimplex.solveSimplexTableau(new double[][]{
//                    {-1,-1,1,0,0,0-3},
//                    {-3,1,0,1,0,0,1},
//                    {1,0,0,0,1,0,2},
//                    {0,1,0,0,0,1,2},
//                    {-3,-1,0,0,0,0,0}
//            })
//        );
    }

    private static void printMatrix(double[][] doubles) {
        for (int i = 0; i < doubles.length; i++){
            for (int j = 0; j < doubles[i].length; j++)
                System.out.print(doubles[i][j]+"\t");
            System.out.println();
        }

    }

    public MatrixSimplex(String[][] coefficientMatrix, int[][] basisMatrix, int[] b, int[] cost) {
        this.coefficientMatrix = coefficientMatrix;
        this.basisMatrix = basisMatrix;
        this.b = b;
        this.cost = cost;
    }

    /**
     * coefficientMatrix is like this
     * [
     *  [x1,x2,x3,s1,s2,P],
     *  [x1,x3,P]
     * ]
     *
     * basisMatrix is like this
     * [
     *  [2,3,2,1,0,0],
     *  [1,1,2,0,1,0]
     * ]
     */
    private String[][] coefficientMatrix;
    private int[][] basisMatrix;
    /**
     * b is the solutions to the matrix like this
     * [0,0,0,1000,800]
     *
     * cost is like this:
     * [-7,-8,-10,0,0,0]
     */
    private int[] b, cost;


    /**
     * tableau has the following format:
     *          z   x1      x2      ... xn      RHS
     *    xb,1  0   v1,1    v1,2    ... v1,n    w1
     *    xb,2  0   v2,1    v2,2    ... v2,n    w2
     *    ...   ... ...     ...     ... ...     ...
     *    xb,n  0   vm,1    vm,2    ... vm,n    wm
     *       z  0   z1-c1   z2-c2   ... zn-xn   cbw
     *
     * coefficients
     *
     * basis
     * cost  bT
     */
    public void doSimplex(){
        double[][] tableau = createTableau();
        tableau = solveSimplexTableau(tableau);
        printResults(tableau,coefficientMatrix);
    }

    public double[][] createTableau() {
        int r = coefficientMatrix[0].length + 1, c = basisMatrix.length + 1;
        double[][] tableau = new double[c][r];
        for (int i = 0; i < basisMatrix.length; i++)
            for (int j = 0; j < basisMatrix[i].length; j++)
                tableau[i][j] = basisMatrix[i][j];
        for (int i = 0; i < cost.length; i++)
            tableau[c - 1][i] = cost[i];
        for (int i = 0; i < b.length; i++)
            tableau[i][r - 1] = b[i];
        return tableau;
    }

    /** coefficientMatrix is like this
     * [
     *  [x1,x2,x3,s1,s2,P],
     *  [x1,x3,P]
     * ]
     */
    private void printResults(double[][] tableau, String[][] coefficientMatrix) {
        int v = tableau[0].length - 1, k = coefficientMatrix[0].length - 1;
        String[] c1 = coefficientMatrix[1], nc1 = new String[coefficientMatrix[0].length];
        Set<String> s = new HashSet<String>();
        for(String i : coefficientMatrix[1])
            s.add(i);
        for (int i = 0; i < coefficientMatrix[0].length; i++)
            if(!s.contains(coefficientMatrix[0][i]))
                nc1[k--] = coefficientMatrix[0][i];
        for (int i = 0; i < c1.length; i++)
            nc1[i] = c1[i];

        for (int i = 0; i < nc1.length; i++)
            System.out.println(nc1[i]+" = "+(tableau.length - 1 < i ? 0 : tableau[i][v]));
    }

    /**
     * is z optimal
     * what is the EV
     * min ratio test departing variable (dv)
     * pivot on the dv
     *
     */

    /**
     * Row reduction of simplex tableau
     * @param tableau example:
     *                [[2,3,2,1,0,0|1000],
     *                 [1,1,2,0,1,0|800],
     *                 [-7,-8,-10,0,0,1|0]]
     *                last column is the z, last row is always solutions
     */
    public static double[][] solveSimplexTableau(double[][] tableau){
        while (lastColumnHasNegative(tableau)){
            int pivotColumn = getPivotColumnIndex(tableau);
            int pivotRow = getPivotRowIndex(tableau, pivotColumn);
            tableau = makePivotOne(tableau,pivotColumn,pivotRow);
            tableau = makePivotRowZero(tableau,pivotColumn,pivotRow);
        }
        return tableau;
    }


    public static boolean lastColumnHasNegative(double[][] tableau){
        for(double i : tableau[tableau.length - 1])
            if(i < 0) return true;
        return false;
    }
    public static int getPivotColumnIndex(double[][] tableau){
        double smallest = Integer.MAX_VALUE;
        int index = -1;
        double[] tableauLastRow = tableau[tableau.length - 1];
        for(int i = 0; i < tableauLastRow.length; i++ ){
            double v = tableauLastRow[i];
            if(v < smallest){
                smallest = v;
                index = i;
            }
        }
        return index;
    }
    public static int getPivotRowIndex(double[][] tableau, int pivotColumnIndex){
        double smallest = Integer.MAX_VALUE;
        int lastRowIndex = tableau[0].length - 1, index = -1;
        for(int i = 0; i < tableau.length - 1; i++){
            double v = tableau[i][lastRowIndex]/tableau[i][pivotColumnIndex];
            if(v < smallest){
                smallest = v;
                index = i;
            }
        }
        return index;
    }
    public static double[][] makePivotOne(double[][] tableau, int pivotColumnIndex, int pivotRowIndex){
        double v = tableau[pivotRowIndex][pivotColumnIndex];
        if(v == 1)
            return tableau;
        for(int i = 0; i<tableau[pivotRowIndex].length; i++)
            tableau[pivotRowIndex][i]/=v;
        return tableau;
    }
    public static double[][] makePivotRowZero(double[][] tableau, int pivotColumnIndex, int pivotRowIndex){
        double v = tableau[pivotRowIndex][pivotColumnIndex];
        if(v == 0)
            throw new IllegalArgumentException("Pivot value can't be zero in tableau for (row,column)=("+pivotColumnIndex+","+pivotRowIndex+")");
        for(int i = 0; i<tableau.length; i++){
            if(i == pivotRowIndex)
                continue;
            double w = (-1/v)*tableau[i][pivotColumnIndex];
            for(int j = 0; j<tableau[i].length; j++)
                tableau[i][j]+=w*tableau[pivotRowIndex][j];
        }
        return tableau;
    }
}
