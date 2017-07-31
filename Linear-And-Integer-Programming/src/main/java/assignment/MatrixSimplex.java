package assignment;

import java.util.HashSet;
import java.util.Set;

public class MatrixSimplex {
    public static void main(String[] args) throws Exception {
//        new MatrixSimplex(new String[][]{
//                {"p", "c", "s1", "s2", "P"},
//                {"p", "c", "P"}
//        },new int[][]{
//                {4,5,1,0,0},
//                {1,3,0,1,0},
//                {8,12,0,0,1}
//        },new int[]{20*16,15*16,20*12},
//                new int[]{-3,-4,0,0,0,0}).doSimplex();

        System.out.println(getMatrixString(
                MatrixSimplex.solveSimplexTableau(
                    new double[][]{
                            {400,600,1,0,0,0,9200},
                            {200,100,0,1,0,0,2400},
                            {100,0,0,0,1,0,1500},
                            {0,150,0,0,0,1,2100},
                            {-2,-4,0,0,0,0,0}
                    }
            ),new String[][]{
                    {"p", "g", "s1", "s2","s3","s4", "P"},
                    {"poppy seed cake", "german chocolate cake","Profit", "flour","butter","poppy seeds","chocolate"}
        }));
    }

    private static void printMatrix(double[][] doubles) {
        for (int i = 0; i < doubles.length; i++){
            for (int j = 0; j < doubles[i].length; j++)
                System.out.print(doubles[i][j]+"\t");
            System.out.println();
        }

    }

    private static String getMatrixString(double[][] doubles) throws Exception {
        String[][] names = new String[2][];
        names[0] = new String[doubles[0].length];
        names[1] = new String[doubles.length];
        String c = "x";
        for (int i = 0; i < names.length; i++) {
            for (int j = 0; j < names[i].length; j++)
                if(j == names[i].length - 1){
                    if(i == 0)  names[i][j] = "bs";
                    else names[i][j] = "z";
                }else
                    names[i][j] = c+""+j;
            c = "y";
        }
        return getMatrixString(doubles,names);
    }
    public static String getMatrixString(double[][] doubles, String[][] names) throws Exception {
        if(isNotSameLength(doubles))
            throw new Exception("Matrix is not Same length");

        int[] length = new int[doubles[0].length+1];
        for (int i = 0; i < length.length; i++)
            length[i] = Integer.MIN_VALUE;
        for (int i = 0; i < doubles.length; i++)
            for (int j = 0; j < doubles[i].length; j++)
                length[j] = Math.max(length[j],(doubles[i][j]+"").length());
        for (int i = 0; i < names[0].length; i++)
            length[i] = Math.max(length[i],names[0][i].length());
        for (int i = 0; i < names[1].length; i++)
            length[length.length-1] = Math.max(length[length.length-1],names[1][i].length());

        String s = "";
        int lenghtSum = 4, k = doubles.length;
        for (int i = 0; i < length.length; i++)
            lenghtSum+=length[i]+2;
        for (int i = 0; i < doubles.length; i++) {
            if(i == 0) {
                s+=String.format("%-" + (length[length.length-1]+2)+"s"," ");
                for (int j = 0; j < names[0].length; j++)
                    if(j == names[0].length - 1)
                        s+="|  "+String.format("%-" + (length[j] + 2) + "s", names[0][j]);
                    else
                        s += String.format("%-" + (length[j] + 2) + "s", names[0][j]);
                s+="\n";
            }
            if(i == doubles.length - 1){
                for (int j = 0; j < lenghtSum; j++)
                    s+="-";
                s+="\n";
            }
            for (int j = 0; j < doubles[i].length; j++) {
                if(j == 0)
                    s+=String.format("%-"+(length[length.length-1]+2)+"s",names[1][i]);
                if(j == doubles[i].length - 1)
                    s+="|  ";
                s += String.format("%-" + (length[j] + 2) + "s", doubles[i][j]);
            }
            s+="\n";
        }
        return s;
    }

    public static boolean isNotSameLength(double[][] doubles) {
        int l = doubles[0].length;
        for (int i = 1; i < doubles.length; i++)
            if(doubles[i].length != l)
                return true;
        return false;
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
        try {
            tableau = solveSimplexTableau(tableau);
        } catch (Exception e) {
            System.out.println("No Solution");
        }
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
    public static double[][] solveSimplexTableau(double[][] tableau) throws Exception {
        checkMatrix(tableau);
        int iteration = 0;
        while (lastColumnHasNegative(tableau)){
            int pivotColumn = getPivotColumnIndex(tableau);
            if(isNoSolution(tableau,pivotColumn))
                throw new Exception("No Solution to tableau");
            int pivotRow = getPivotRowIndex(tableau, pivotColumn);
            tableau = makePivotOne(tableau,pivotColumn,pivotRow);
            tableau = makePivotRowZero(tableau,pivotColumn,pivotRow);

            if(iteration++ > iterationMax){
                if(deepEquals(tableau,lastMatrix) || deepEquals(tableau,secondToLastMatrix))
                    if(repeatMatrix++ > repeatMatrixMax)
                        throw new Exception("No Solution: Tableau got repeated too many times");
                if(which == 1) {
                    lastMatrix = deepCopyIntMatrix(tableau);
                    which = 0;
                }else{
                    secondToLastMatrix = deepCopyIntMatrix(tableau);
                    which = 1;
                }
            }
        }
        return tableau;
    }

    private static int repeatMatrix = 0, repeatMatrixMax = 100, iterationMax = 1000, which = 1;
    private static double[][] lastMatrix, secondToLastMatrix;

    public static double[][] deepCopyIntMatrix(double[][] input) {
        if (input == null)
            return null;
        double[][] result = new double[input.length][];
        for (int r = 0; r < input.length; r++) {
            result[r] = input[r].clone();
        }
        return result;
    }
    public static boolean deepEquals(double[][] a, double[][] b){
        if(a == null && b == null)
            return true;
        if( a == null || b == null)
            return false;
        if(a.length != b.length)
            return false;
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[i].length; j++){
                if(a[i].length != b[i].length)
                    return false;
                if(a[i][j] != b[i][j])
                    return false;
            }
        return true;
    }

    private static void checkMatrix(double[][] tableau) throws Exception {
        if(isNotSameLength(tableau))
            throw new Exception("Matrix not same length");
    }

    public static boolean isNoSolution(double[][] tableau, int pivotColumn) {
        for (int i = 0; i < tableau.length; i++)
            if(tableau[i][pivotColumn] > 0)
                return false;
        return true;
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
