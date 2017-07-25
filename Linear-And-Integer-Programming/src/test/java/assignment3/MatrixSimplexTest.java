package assignment3;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MatrixSimplexTest {

    @Test
    public void testLastColumnTableauHasNegativeTrue(){
        assertTrue(MatrixSimplex.lastColumnHasNegative(new double[][]{
                {2,3,2,1,0,0,1000},
                {1,1,2,0,1,0,800},
                {-7,-8,-10,0,0,1,0}
        }));
    }
    @Test
    public void testLastColumnTableauHasNegativeFalse(){
        assertFalse(MatrixSimplex.lastColumnHasNegative(new double[][]{
                {2,3,2,1,0,0,1000},
                {1,1,2,0,1,0,800},
                {7,8,10,0,0,1,0}
        }));
    }
    @Test
    public void testLastColumnTableauGetSmallestValue(){
        assertEquals(MatrixSimplex.getPivotColumnIndex(new double[][]{
                {2,3,2,1,0,0,1000},
                {1,1,2,0,1,0,800},
                {-7,-8,-10,0,0,1,0}
        }),2);
    }
    @Test
    public void testGetPivotRowIndex(){
        assertEquals(MatrixSimplex.getPivotRowIndex(new double[][]{
                {2,3,2,1,0,0,1000},
                {1,1,2,0,1,0,800},
                {-7,-8,-10,0,0,1,0}
        },2),1);
    }
    @Test
    public void testGetPivotRowIndexUsingColumnSmallestValue(){
        double[][] tableau = new double[][]{
                {2,3,2,1,0,0,1000},
                {1,1,2,0,1,0,800},
                {-7,-8,-10,0,0,1,0}
        };
        assertEquals(MatrixSimplex.getPivotRowIndex(tableau,
                MatrixSimplex.getPivotColumnIndex(tableau)),
                1);
    }
    @Test
    public void testRowOperationMakePivotOne(){
        double[][] tableau = new double[][]{
                {2,3,2,1,0,0,1000},
                {1,1,2,0,1,0,800},
                {-7,-8,-10,0,0,1,0}
        };
        assertEquals(MatrixSimplex.makePivotOne(tableau,2,1),
        new double[][]{
                {2,3,2,1,0,0,1000},
                {1/2.,1/2.,1,0,1/2.,0,400},
                {-7,-8,-10,0,0,1,0}
        });
    }
    @Test
    public void testRowOperationMakePivotRowZero(){
        double[][] tableau = new double[][]{
                {2,3,2,1,0,0,1000},
                {1/2.,1/2.,1,0,1/2.,0,400},
                {-7,-8,-10,0,0,1,0}
        };
        assertEquals(MatrixSimplex.makePivotRowZero(tableau,2,1),
                new double[][]{
                        {1,2,0,1,-1,0,200},
                        {1/2.,1/2.,1,0,1/2.,0,400},
                        {-2,-3,0,0,5,1,4000}
                });
    }
    @Test
    public void testSolutionToTableau(){
        double[][] tableau = new double[][]{
                {2,3,2,1,0,0,1000},
                {1,1,2,0,1,0,800},
                {-7,-8,-10,0,0,1,0}
        };
        assertEquals(MatrixSimplex.solveSimplexTableau(tableau),
                new double[][]{
                        {1,2,0,1,-1,0,200},
                        {0,-1/2.,1,-1/2.,1,0,300},
                        {0,1,0,2,3,1,4400}
                });
    }
    @Test public void testCreateTablaue(){
        assertEquals(new MatrixSimplex(new String[][]{
                {"x1", "x2", "x3", "s1", "s2", "P"},
                {"x1", "x3", "P"}
            },new int[][]{
                    {2,3,2,1,0,0},
                    {1,1,2,0,1,0}
            },new int[]{1000,800},
            new int[]{-7,-8,-10,0,0,1}).createTableau(),
            new double[][]{
                    {2,3,2,1,0,0,1000},
                    {1,1,2,0,1,0,800},
                    {-7,-8,-10,0,0,1,0}
        });
    }
    @Test public void testSolve(){
        new MatrixSimplex(new String[][]{
                    {"x1", "x2", "x3", "s1", "s2", "P"},
                    {"x1", "x3", "P"}
            },new int[][]{
                    {2,3,2,1,0,0},
                    {1,1,2,0,1,0}
            },new int[]{1000,800},
                    new int[]{-7,-8,-10,0,0,1}).doSimplex();
    }
}
